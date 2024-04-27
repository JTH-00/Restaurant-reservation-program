package com.proj.restreserve.payment.service;

import com.proj.restreserve.alarm.dto.AlarmDto;
import com.proj.restreserve.alarm.service.AlarmService;
import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.cart.repository.CartMenuRepository;
import com.proj.restreserve.cart.repository.CartRepository;
import com.proj.restreserve.cart.service.CartService;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMenuRepository paymentMenuRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;
    private final AlarmService alarmService;

    public List<PaymentMenuDto> paymentMenusSet (String paymentid){
        List<PaymentMenu> paymentMenus = this.paymentMenuRepository.findByPayment_Paymentid(paymentid);

        List<PaymentMenuDto> paymentMenusDto = new ArrayList<>();//총 메뉴 저장

        for (PaymentMenu menu : paymentMenus) {
            PaymentMenuDto paymentMenuDto = modelMapper.map(menu, PaymentMenuDto.class);//DTO로 변환

            Menu image = this.menuRepository.getReferenceById(paymentMenuDto.getMenu().getMenuid());//메뉴 이미지 링크 null떠서 추가함
            if(image.getMenuimages()!=null){
                paymentMenuDto.getMenu().setImagelink(image.getMenuimages().getMenuimagelink());
            }//결제한 총 메뉴를 뽑는 부분이라 메뉴 이미지가 필요없다면 지워도 될 부분, 레스토랑 상세페이지의 경우 이미 메뉴를 영속성에 담아서 추가 쿼리가 없지만, 다른 경우에 생길 예정이라 지울 예정일수도 있음

            paymentMenusDto.add(paymentMenuDto);
        }
        return paymentMenusDto;
    }
    @Transactional
    public void processCartToPayment(String restaurantid,String cartid) {
        User user=getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartMenu> cartMenus = cartMenuRepository.findByCart(cart);

        Restaurant restaurant = restaurantRepository.findById(restaurantid)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (cartMenus.isEmpty()) {
            throw new RuntimeException("CartMenu is empty");
        }else if(restaurant.getBan() || restaurant.getStopsales()){
            throw new RuntimeException("해당 매장은 현재 영업 중이 아닙니다.");
        }

        double totalAmount = cartMenus.stream()
                .mapToDouble(cartMenu -> Double.parseDouble(cartMenu.getMenu().getPrice()) * cartMenu.getMenucount())
                .sum();

        // 결제 정보 생성 및 저장
        String paymentid= UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setPaymentid(paymentid);
        payment.setTotalprice(String.valueOf(totalAmount));
        payment.setPaymentcheck(false);
        payment.setDay(LocalDate.now());
        payment.setUser(user);
        payment.setRestaurant(restaurant);
        paymentRepository.save(payment);

        //알람을 해당 매장 업주에게 전송
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(user.getUsername()+"님의 새로운 포장 주문이 들어왔습니다.");
        alarmDto.setUrl("api/admin/restaurant/payment/refuse/"+paymentid);// 업주에게 알람 리스트에 사용가능한 거절 url제공
        alarmService.wirteAlarm(alarmDto,"포장 예약",restaurant.getUser());//레스토랑 업주에게 보내는 알람

        // 결제 메뉴 정보 생성 및 저장
        for (CartMenu cartMenu : cartMenus) {
            PaymentMenu paymentMenu = new PaymentMenu();
            paymentMenu.setPayment(payment);
            paymentMenu.setMenu(cartMenu.getMenu());
            paymentMenu.setCount(cartMenu.getMenucount());
            paymentMenuRepository.save(paymentMenu);
        }
        cartService.removeCart(cartid);

    }
    @Transactional
    public void refusePayment(String paymentid){//포장 예약 거절
        Payment payment = paymentRepository.getReferenceById(paymentid);
        //알람을 해당 매장 업주에게 전송
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(payment.getUser().getUsername()+"님의 포장 주문이 거절되었어요.");
        alarmService.wirteAlarm(alarmDto,"포장 예약",payment.getUser());//예약을 신청한 사용자에게 보내는 알람

        paymentRepository.deleteById(paymentid);
    }

    @Transactional
    public void checkPayment(String paymentid){//포장 수령 확인
        Payment payment = paymentRepository.getReferenceById(paymentid);
        payment.setPaymentcheck(true);

        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(payment.getUser().getUsername()+"님의 포장 주문이 확인되었어요.");
        alarmService.wirteAlarm(alarmDto,"포장 예약",payment.getUser());//예약을 신청한 사용자에게 보내는 알람
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail);
    }
}
