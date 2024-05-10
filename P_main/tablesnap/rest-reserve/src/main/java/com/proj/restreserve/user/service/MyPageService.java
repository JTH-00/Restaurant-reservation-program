package com.proj.restreserve.user.service;

import com.proj.restreserve.jwt.SecurityUtil;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.payment.dto.GroupedPaymentDto;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.restaurant.dto.FavoritesDto;
import com.proj.restreserve.restaurant.entity.Favorites;
import com.proj.restreserve.restaurant.repository.FavoritesRepository;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.entity.ReviewImage;
import com.proj.restreserve.review.repository.ReviewRepository;
import com.proj.restreserve.user.dto.SelectUserDto;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService{
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final PaymentMenuRepository paymentMenuRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;
    private final FavoritesRepository favoritesRepository;
    private final ModelMapper modelMapper;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    @Transactional
    public Page<VisitDto> MyRegistInfo(int page, int pagesize){

        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page-1, pagesize);

        Page<Visit> visits = visitRepository.findByUser(user,pageable);

        List<VisitDto> visitDtoList = visits.getContent().stream().map(visit -> {
            VisitDto visitDto = new VisitDto();
            visitDto.setVisitid(visit.getVisitid());
            visitDto.setVisittime(visit.getVisittime());
            visitDto.setVisitcheck(visit.getVisitcheck());
            visitDto.setVisitcustomers(visit.getVisitcustomers());
            if (visit.getRestaurant() != null) {
                visitDto.setRestaurant(visit.getRestaurant());
            }
            if (visit.getUser() != null) {
                visitDto.setUserid(visit.getUser().getUserid());
            }
            return visitDto;
        })
                .filter(visitDto -> visitDto.getVisitcheck())
                .collect(Collectors.toList());

        return new PageImpl<>(visitDtoList, pageable, visits.getTotalElements());
    }
    public SelectMenuDto menuToSelectMenuDto(Menu menu) {
        // Menu 객체의 필드를 사용하여 SelectMenuDto 객체 생성 및 필드 설정
        SelectMenuDto selectMenuDto = new SelectMenuDto();
        selectMenuDto.setMenuid(menu.getMenuid()); // 예시 필드 설정
        selectMenuDto.setName(menu.getName()); // 예시 필드 설정
        return selectMenuDto;
    }
    @Transactional
    public Page<GroupedPaymentDto> MyPaymentInfo(int page, int pagesize) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page-1, pagesize);

        Page<PaymentMenu> paymentMenus = paymentMenuRepository.findByPayment_User(user, pageable);

        List<PaymentMenuDto> paymentMenuDtoList = paymentMenus.getContent().stream().map(paymentMenu -> {
            PaymentMenuDto paymentMenuDto = new PaymentMenuDto();
            paymentMenuDto.setPayment(paymentMenu.getPayment().getPaymentid());
            paymentMenuDto.setPaymentmenuid(paymentMenu.getPaymentmenuid());
            paymentMenuDto.setMenu(menuToSelectMenuDto(paymentMenu.getMenu()));
            paymentMenuDto.setCount(paymentMenu.getCount());
            paymentMenuDto.setTotalprice(paymentMenu.getPayment().getTotalprice());
            paymentMenuDto.setDay(paymentMenu.getPayment().getDay());
            paymentMenuDto.setRestaurant(paymentMenu.getPayment().getRestaurant());
            paymentMenuDto.setPaymentcheck(paymentMenu.getPayment().getPaymentcheck());

            return paymentMenuDto;
        }).collect(Collectors.toList());

        // paymentid 기준으로 그룹화
        Map<String, List<PaymentMenuDto>> groupedByPaymentId = paymentMenuDtoList.stream()
                .collect(Collectors.groupingBy(PaymentMenuDto::getPayment));

        List<GroupedPaymentDto> groupedPaymentDtos = new ArrayList<>();
        // 각 그룹별로 GroupedPaymentDto 생성
        groupedByPaymentId.forEach((paymentId, paymentMenuDtos) -> {
            GroupedPaymentDto groupedPaymentDto = new GroupedPaymentDto();
            groupedPaymentDto.setPaymentId(paymentId);
            groupedPaymentDto.setPaymentMenus(paymentMenuDtos);
            // 첫 번째 항목에서 공통 정보 추출
            PaymentMenuDto firstDto = paymentMenuDtos.get(0);
            groupedPaymentDto.setTotalprice(firstDto.getTotalprice());
            groupedPaymentDto.setDay(firstDto.getDay());
            groupedPaymentDto.setRestaurant(firstDto.getRestaurant().getRestaurantid());
            groupedPaymentDto.setPaymentcheck(firstDto.getPaymentcheck());
            // ...
            groupedPaymentDtos.add(groupedPaymentDto);
        });

        // GroupedPaymentDto 리스트를 페이지로 변환
        // 여기서는 단순 예시로, 실제 페이지 처리는 복잡할 수 있습니다.
        return new PageImpl<>(groupedPaymentDtos, pageable, paymentMenus.getTotalElements());
    }
    public Page<FavoritesDto> Myfavorites(int page, int pagesize) {

        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page-1, pagesize);
       /* List<Favorites> favoritesList = favoritesRepository.findByUser(user);*/
        Page<Favorites> favorites = favoritesRepository.findByUser(user, pageable);
        /*return favoritesList.stream().map(favorite -> {
            FavoritesDto favoritesDto = new FavoritesDto();
            favoritesDto.setFavoritesid(favorite.getFavoritesid());
            favoritesDto.setUser(favorite.getUser());
            favoritesDto.setRestaurant(favorite.getRestaurant());
            return favoritesDto;
        }).collect(Collectors.toList());*/
        return favorites.map(favorite -> {
            FavoritesDto favoritesDto = new FavoritesDto();
            favoritesDto.setFavoritesid(favorite.getFavoritesid());
            favoritesDto.setUser(favorite.getUser());
            favoritesDto.setRestaurant(favorite.getRestaurant());
            return favoritesDto;
        });
    }

    @Transactional
    public Page<ReviewDto> MyReviewInfo(int page, int pagesize){

        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page-1,pagesize);
        /*List<Review> reviews = reviewRepository.findByUser(user);*/
        Page<Review> reviewPage = reviewRepository.findByUser(user, pageable);
/*        return reviews.stream().map(review -> {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewid(review.getReviewid());
            reviewDto.setScope(review.getScope());
            reviewDto.setContent(review.getContent());
            reviewDto.setDate(review.getDate());
            reviewDto.setUserid(review.getUser().getUserid());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewDto.setImageLinks(imageLinks);

            return reviewDto;
        }).collect(Collectors.toList());*/
        return reviewPage.map(review -> {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewid(review.getReviewid());
            reviewDto.setScope(review.getScope());
            reviewDto.setContent(review.getContent());
            reviewDto.setDate(review.getDate());
            reviewDto.setUserid(review.getUser().getUserid());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewDto.setImageLinks(imageLinks);

            return reviewDto;
        });
    }

    @Transactional
    public Optional<User> enterUserInfo(String password) {
        Optional<String> currentUseremail = SecurityUtil.getCurrentUseremail();
        if (currentUseremail.isPresent()) {
            Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUseremail(currentUseremail.get()));
            // 사용자가 입력한 비밀번호와 저장된 비밀번호를 비교하여 일치하는 경우에만 사용자 정보 반환
            if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
                return optionalUser;
            }
        }
        return Optional.empty();
    }

    public void modifyUserInfo(UserDto userDto) {
        Optional<String> currentUseremail = SecurityUtil.getCurrentUseremail();
        if (currentUseremail.isPresent()) {
            User user = userRepository.findByUseremail(currentUseremail.get());
            if (user != null) {
                // 사용자 정보 업데이트 로직
                user.setUsername(userDto.getUsername());
                user.setPhone(userDto.getPhone());

                userRepository.save(user);
            }
        }
    }


    public void modifyUserPassword(String currentPassword, String newPassword, String newPasswordConfirm) {

        User user = getCurrentUser();

        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        // 현재 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        }

        // 새 비밀번호를 해시화하여 설정
        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordHash);
        // 사용자 정보 저장
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public Page<SelectUserDto> showBannedUser(int page, int pagesize){
        Pageable pageable = PageRequest.of(page-1,pagesize);
        Page<User> users = userRepository.findByBanTrue(pageable);

        Page<SelectUserDto> userDtos = users.map(user -> {
            SelectUserDto selectUserDto = modelMapper.map(user,SelectUserDto.class);
            return selectUserDto;
        });
        return userDtos;
    }
}
