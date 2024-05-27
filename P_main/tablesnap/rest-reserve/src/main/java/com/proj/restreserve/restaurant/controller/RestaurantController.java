package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.detailpage.dto.DetailPageDto;
import com.proj.restreserve.detailpage.service.DetailPageService;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.dto.SelectRestaurantModifyDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.review.dto.ReviewAndReplyDto;
import com.proj.restreserve.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name= "Restaurant", description = "사용자와 업주의 매장 관련 API")
public class RestaurantController {
    private final DetailPageService detailPageService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping("/user/restaurant/list")
    @Operation(summary = "가게 리스트 조회", description = "검색조건의 따른 가게 리스트를 조회합니다.<br>" +
            "검색조건은 카테고리와 분위기,주소로 결정되며 필수로 요구되지않지만 검색기능 사용 시 파라미터로 추가됩니다.")
    public ResponseEntity<List<RestaurantDto>> restaurantlist(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String vibe,
            @RequestParam(required = false) String address) {
        List<RestaurantDto> restaurantDtos = restaurantService.restaurantAll(category, vibe,address);
        return ResponseEntity.ok(restaurantDtos);
    }

    @PostMapping("/user/restaurant/list/favorite/{restaurantid}")
    @Operation(summary = "즐겨찾기", description = "선택한 매장을 즐겨찾기로 지정합니다.<br>" +
            "선택한 매장의 id를 파라미터로 받습니다.")
    public ResponseEntity<String> addFavoriteRestaurant(@PathVariable String restaurantid) {
        restaurantService.addFavoriteRestaurant(restaurantid);
        return ResponseEntity.ok("Favorite restaurant added successfully.");
    }
    @GetMapping(value = "/admin/modify/myrestaurant")
    @Operation(summary = "가게 수정 페이지 이동", description = "자신의 매장을 수정하기 위해 이전에 작성한 매장 정보를 불러옵니다.<br>")
    public ResponseEntity<SelectRestaurantModifyDto> modifyrestaurant(){
        return ResponseEntity.ok(restaurantService.selectRestaurantModifyDto());
    }
    @PutMapping(value = "/admin/modify/myrestaurant", consumes = {"multipart/form-data"})
    @Operation(summary = "가게 수정", description = "자신의 매장을 수정합니다.<br>" +
            "RestaurantDto를 통해 매장정보를 입력하며,<br>"+
            "deleteMenus를 통해 감출 메뉴를 list로 id를 저장한뒤 변경합니다.(이용내역 조회를 위해 삭제를 하지않습니다.<br>"+
            "menuDtos를 통해 추가할 메뉴와 menufiles로 사진을 저장합니다.<br>"+
            "지울 파일의 경우 해당파일의 링크를 List로 저장하여 일련번호 추출 후 S3와 DB삭제 후 새로운 파일을 추가하여 수정합니다.<br>" +
            "추가할 사진의 경우 없어도 무방합니다.")
    public ResponseEntity<RestaurantDto> modifyrestaurant(
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart(value = "deleteMenus" ,required = false) List<String> deleteMenus,
            @RequestPart(value = "menuDtos", required = false) List<MenuDto> menuDtos,
            @RequestPart(value = "menuFiles",required = false) List<MultipartFile> menufiles,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(restaurantService.modifyRestaurant(restaurantDto,deleteMenus,menuDtos,menufiles, files,deleteImageLinks));
    }
  
    @GetMapping("/main")
    @Operation(summary = "메인 페이지", description = "메인페이지로 이동합니다.<br>" +
            "해당 페이지는 랜덤한 가게를 추천하여 노출합니다.")
    public ResponseEntity<List<List<SelectRestaurantDto>>> showRestaurant(){
        return ResponseEntity.ok(restaurantService.showMainPage());//레스토랑 메인 페이지
    }
    @GetMapping("/user/restaurant/{restaurantid}")
    @Operation(summary = "가게 상세페이지", description = "자신이 선택한 매장을 클릭 시 해당 매장의 상세페이지로 이동합니다.<br>" +
            "해당 매장의 id를 파라미터로 받습니다.")
    public ResponseEntity<DetailPageDto> showRestaurant(@PathVariable String restaurantid){
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        return ResponseEntity.ok(detailPageService.pageload(restaurantid,1,2));//레스토랑 상세 페이지, 별점 상관없이 최신순
    }

    @GetMapping("/user/restaurant/review/{restaurantid}")
    @Operation(summary = "리뷰 정렬 페이지", description = "매장 상세페이지의 리뷰부분만 정렬하여 다시 호출합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.<br>" +
            "추가로 해당 매장의 id를 파라미터로 받으며, sort로 정렬기준을 파라미터로 받습니다.<br>" +
            "scope = 방문,포장 합쳐서 별점높은순,날짜기준 내림차순,<br>" +
            "visit = 방문만 날짜기준 내림차순,<br>" +
            "payment = 포장만 날짜기준 내림차순,<br>" +
            "Default = 방문,포장 합쳐서 날짜기준 내림차순")
    public ResponseEntity<Page<ReviewAndReplyDto>> ReviewSortToRestaurant(
            @RequestParam(name="sort", required = false) String sort,
            @RequestParam(required = false, defaultValue = "1") int page,
            @PathVariable String restaurantid){//상세페이지의 리뷰부분만 다시 정렬하는 용도
        return ResponseEntity.ok(reviewService.sortReviews(restaurantid,page,5,sort));
    }

    @GetMapping("/admin/myreview")
    @Operation(summary = "업주 리뷰 조회", description = "자신의 매장의 리뷰를 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<ReviewAndReplyDto>> myRestaurantReview(@RequestParam(required = false, defaultValue = "1") int page){//로그인한 유저의 id로 레스토랑 검색후 반환
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        return ResponseEntity.ok(reviewService.getMyrestaurant(page,10,2));
        //정렬 방문,포장 합쳐서 날짜순
    }

    @GetMapping("/admin/myreview/sort")
    @Operation(summary = "업주 리뷰 조회", description = "자신의 매장의 리뷰를 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.<br>"+
            "sort로 정렬기준을 파라미터로 받습니다.<br>" +
            "scope = 방문,포장 합쳐서 별점높은순,날짜기준 내림차순,<br>" +
            "visit = 방문만 날짜기준 내림차순,<br>" +
            "payment = 포장만 날짜기준 내림차순,<br>" +
            "Default = 방문,포장 합쳐서 날짜기준 내림차순")
    public ResponseEntity<Page<ReviewAndReplyDto>> sortMyRestaurantReview(
            @RequestParam(name="sort", required = false) String sort,
            @RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(reviewService.sortMyrestaurant(page, 10, sort));
        //sort = {"scope","visit","visitReply,"payment","paymentReply"}로 둔상태
        //scope = 방문,포장 합쳐서 별점높은순,날짜기준 내림차순, visit = 방문만 날짜기준 내림차순, payment = 포장만 날짜기준 내림차순,
        //visitReply=방문 답글 없는거만 날짜순, paymentReply=포장 답글 없는거만 날짜순, Default = 방문,포장 합쳐서 날짜기준 내림차순
    }

    @PostMapping("/admin/sales/{restaurantid}")
    @Operation(summary = "매장 활성화 여부 체크", description = "자신의 매장의 예약을 접수 받을 지 여부를 선택합니다.<br>" +
            "파라미터로 자신의 매장의 id를 받습니다.")
    public ResponseEntity<String> changeSalesStatus(@PathVariable String restaurantid){
        return ResponseEntity.ok(restaurantService.changeSales(restaurantid));
    }
}