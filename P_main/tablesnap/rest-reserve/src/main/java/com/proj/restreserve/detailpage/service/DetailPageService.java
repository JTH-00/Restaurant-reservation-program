package com.proj.restreserve.detailpage.service;

import com.proj.restreserve.detailpage.dto.DetailPageDto;
import com.proj.restreserve.menu.dto.MenuAndCategoryDto;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.menu.service.MenuService;
import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.dto.SelectReviewDto;
import com.proj.restreserve.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DetailPageService { //가게 상세페이지용 서비스
    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    @Transactional(readOnly = true)
    public DetailPageDto pageload(String restaurantid, int page, boolean scopecheck){ //DTO를 모아서 한번에 출력하기 위해 Map사용
        MenuAndCategoryDto menuAndCategoryDto = menuService.findMenu(restaurantid);//카테고리 종류와 총 메뉴를 가져오기

        SelectRestaurantDto date1= restaurantService.findRestaurant(restaurantid).get();//레스토랑 정보
        Set<MenuCategoryDto> data2 = menuAndCategoryDto.getCategoryList(); //menuAndCategoryDto에서 가져온 카테고리의 정보
        List<SelectMenuDto> date3= menuAndCategoryDto.getSelectMenuDtoList(); //menuAndCategoryDto에서 가져온 메뉴들의 정보
        Page<SelectReviewDto> data4= reviewService.getReviewAll(restaurantid,page,5,scopecheck);//페이징 처리한 리뷰 조회

        DetailPageDto detailPageDto = new DetailPageDto(date1,data2,date3,data4);

        return detailPageDto;
    }
}
