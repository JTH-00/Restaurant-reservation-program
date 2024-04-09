package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.detailpage.dto.DetailPageDto;
import com.proj.restreserve.detailpage.service.DetailPageService;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.review.dto.SelectReviewDto;
import com.proj.restreserve.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        return ResponseEntity.ok(restaurantService.showMainPage());//레스토랑 상세 페이지
    }
    @GetMapping("/user/restaurant/{restaurantid}")
    public ResponseEntity<DetailPageDto> showRestaurant(@PathVariable String restaurantid){
        return ResponseEntity.ok(detailPageService.pageload(restaurantid,1,false));//레스토랑 상세 페이지, 별점 상관없이 최신순
    }

    @GetMapping("/user/restaurant/review/{restaurantid}")
    public ResponseEntity<Page<SelectReviewDto>> ReviewSortToRestaurant(
            @RequestParam(name="sort", required = false) String sort,
            @PathVariable String restaurantid){//테스트용
        return ResponseEntity.ok(reviewService.Myrestaurant(restaurantid,1,10,sort));
    }

    @GetMapping("/admin/myreview")
    public ResponseEntity<Page<SelectReviewDto>> myRestaurantReview(){//로그인한 유저의 id로 레스토랑 검색후 반환
        return ResponseEntity.ok(reviewService.getMyrestaurant(1,10,false));
    }
    @GetMapping("/admin/myreview")
    public ResponseEntity<Page<SelectReviewDto>> sortMyRestaurantReview(//이것도 그렇게하게 묶어놔야겠네
            @RequestParam(name="sort", required = false) String sort){//테스트용
        return ResponseEntity.ok(reviewService.sortMyrestaurant(1, 10, false, sort));
    }
    //답글 작성 여부로도 추가해야함
}