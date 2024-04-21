package com.proj.restreserve.detailpage.dto;

import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.review.dto.ReviewAndReplyDto;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.dto.SelectReviewDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Data
public class DetailPageDto { //가게 상세페이지용(가게 정보, 카테고리 종류, 메뉴 전체, 리뷰조회 페이징
    private SelectRestaurantDto restaurantDto;
    private Set<MenuCategoryDto> menuCategoryDto;
    private List<SelectMenuDto> menuDtoList;
    private Page<ReviewAndReplyDto> reviewDto;

    public DetailPageDto(SelectRestaurantDto restaurantDto, Set<MenuCategoryDto> menuCategoryDto, List<SelectMenuDto> menuDtoList, Page<ReviewAndReplyDto> reviewDto){
        this.restaurantDto = restaurantDto;
        this.menuCategoryDto = menuCategoryDto;
        this.menuDtoList = menuDtoList;
        this.reviewDto = reviewDto;
    }
}
