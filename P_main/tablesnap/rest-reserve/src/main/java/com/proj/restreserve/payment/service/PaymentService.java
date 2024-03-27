package com.proj.restreserve.payment.service;

import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;

    private final PaymentMenuRepository paymentMenuRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public List<PaymentMenuDto> paymentMenusSet (String paymentid){
        List<PaymentMenu> paymentMenus = this.paymentMenuRepository.findByPaymentid_Paymentid(paymentid);

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
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail);
    }
}
