package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.detailpage.dto.DetailPageDto;
import com.proj.restreserve.detailpage.service.DetailPageService;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.review.dto.ReviewAndReplyDto;
import com.proj.restreserve.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantController {
    private final DetailPageService detailPageService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping("/user/restaurant/list")
    public ResponseEntity <List<RestaurantDto>> restaurantlist(){
        List<RestaurantDto> restaurantDtos = restaurantService.restaurantAll();
        return ResponseEntity.ok(restaurantDtos);
    }

    @PostMapping("/user/restaurant/list/favorite/{restaurantid}")
    public ResponseEntity<String> addFavoriteRestaurant(@PathVariable String restaurantid) {
        restaurantService.addFavoriteRestaurant(restaurantid);
        return ResponseEntity.ok("Favorite restaurant added successfully.");
    }

    @PostMapping(value = "/admin/registration", consumes = {"multipart/form-data"})
    public ResponseEntity<Restaurant> registrestaurant(
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(restaurantService.regist(restaurantDto, files));
    }
    @PutMapping(value = "/admin/modify/myrestaurant", consumes = {"multipart/form-data"})
    public ResponseEntity<RestaurantDto> modifyrestaurant(
            @RequestParam(name="restaurantid") String restaurantid,
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(restaurantService.modifyRestaurant(restaurantid,restaurantDto, files,deleteImageLinks));
    }
    @GetMapping("/main")
    public ResponseEntity<List<List<SelectRestaurantDto>>> showRestaurant(){
        return ResponseEntity.ok(restaurantService.showMainPage());//레스토랑 메인 페이지
    }
    @GetMapping("/user/restaurant/{restaurantid}")
    public ResponseEntity<DetailPageDto> showRestaurant(@PathVariable String restaurantid){
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        return ResponseEntity.ok(detailPageService.pageload(restaurantid,1,2));//레스토랑 상세 페이지, 별점 상관없이 최신순
    }

    @GetMapping("/user/restaurant/review/{restaurantid}")
    public ResponseEntity<Page<ReviewAndReplyDto>> ReviewSortToRestaurant(
            @RequestParam(name="sort", required = false) String sort,
            @PathVariable String restaurantid){//상세페이지의 리뷰부분만 다시 정렬하는 용도
        return ResponseEntity.ok(reviewService.sortReviews(restaurantid,1,5,sort));
    }

    @GetMapping("/admin/myreview")
    public ResponseEntity<Page<ReviewAndReplyDto>> myRestaurantReview(){//로그인한 유저의 id로 레스토랑 검색후 반환
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        return ResponseEntity.ok(reviewService.getMyrestaurant(1,10,2));
        //정렬 방문,포장 합쳐서 날짜순
    }
    @GetMapping("/admin/myreview/sort")
    public ResponseEntity<Page<ReviewAndReplyDto>> sortMyRestaurantReview(
             @RequestParam(name="sort", required = false) String sort){
        return ResponseEntity.ok(reviewService.sortMyrestaurant(1, 10, sort));
        //sort = {"scope","visit","visitReply,"payment","paymentReply"}로 둔상태 
        //scope = 방문,포장 합쳐서 별점높은순,날짜기준 내림차순, visit = 방문만 날짜기준 내림차순, payment = 포장만 날짜기준 내림차순,
        //visitReply=방문 답글 없는거만 날짜순, paymentReply=포장 답글 없는거만 날짜순, Default = 방문,포장 합쳐서 날짜기준 내림차순
    }
    @PostMapping("/admin/sales/{restaurantid}")
    public ResponseEntity<String> changeSalesStatus(@PathVariable String restaurantid){
        return ResponseEntity.ok(restaurantService.changeSales(restaurantid));
    }
}