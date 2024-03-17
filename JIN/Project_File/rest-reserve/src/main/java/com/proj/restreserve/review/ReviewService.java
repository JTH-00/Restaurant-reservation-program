package com.proj.restreserve.review;

import com.proj.restreserve.detailpage.PaymentMenusDto;
import com.proj.restreserve.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    public Page<ReviewDto> getReview(String restaurantid,int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("Date")); //날짜기준 내림차순 정렬
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by(sorts));//기본페이지를 1로 두었기에 -1, 5개의 리뷰

        Page<Review> reviewPage= this.reviewRepository.findByPayment_Restaurantid_Restaurantid(restaurantid,pageable);//Payment의 레스토랑 객체의 레스토랑 아이디로 검색
        Page<ReviewDto> reviewDtos = reviewPage.map(review -> {
            ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)
            List<PaymentMenusDto> paymentMenus = paymentService.paymentMenusSet(reviewDto.getPayment().getPaymentid());//리뷰의 결제아이디를 가져와 해당 결제의 주문 메뉴 조회
            reviewDto.setPaymentMenusDtos(paymentMenus);//조회한 주문 메뉴를 주입
            return reviewDto;
        });

        return reviewDtos;
    }
}
