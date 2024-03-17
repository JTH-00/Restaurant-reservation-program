package com.proj.restreserve.detailpage;

import com.proj.restreserve.category.CategoryDto;
import com.proj.restreserve.menu.MenuDto;
import com.proj.restreserve.restaurant.RestaurantDto;
import com.proj.restreserve.review.ReviewDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Data
public class DetailPageDto { //가게 상세페이지용(가게 정보, 카테고리 종류, 메뉴 전체, 리뷰조회 페이징
    private RestaurantDto restaurantDto;
    private Set<CategoryDto> categoryDto;
    private List<MenuDto> menuDtoList;
    private Page<ReviewDto> reviewDto;

    public DetailPageDto(RestaurantDto restaurantDto, Set<CategoryDto> categoryDto, List<MenuDto> menuDtoList, Page<ReviewDto> reviewDto){
        this.restaurantDto = restaurantDto;
        this.categoryDto = categoryDto;
        this.menuDtoList = menuDtoList;
        this.reviewDto = reviewDto;
    }
}
