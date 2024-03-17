package com.proj.restreserve.restaurant;

import com.proj.restreserve.detailpage.DetailPageDto;
import com.proj.restreserve.detailpage.DetailPageService;
import com.proj.restreserve.detailpage.PaymentMenusDto;
import com.proj.restreserve.payment.PaymentService;
import com.proj.restreserve.review.ReviewDto;
import com.proj.restreserve.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RestraurantController {
    private final DetailPageService detailPageService;
    private final ReviewService reviewService;
    private final PaymentService paymentService;
    @GetMapping("/rest/{restaurantid}")
    public ResponseEntity<DetailPageDto> showRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(detailPageService.pageload(restaurantid,1));//레스토랑 상세 페이지
    }//임시로 1페이지만 해둠 다른 페이지도 처리되는거 확인했었고 리퀘스트파람으로 페이지값 하면될듯 디폴트값이랑
}
