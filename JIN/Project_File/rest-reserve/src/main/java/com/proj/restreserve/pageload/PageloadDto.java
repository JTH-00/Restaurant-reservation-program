package com.proj.restreserve.pageload;

import com.proj.restreserve.category.CategoryDto;
import com.proj.restreserve.menu.MenuDto;
import com.proj.restreserve.restaurant.RestaurantDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PageloadDto {
    private RestaurantDto restaurantDto;
    private Set<CategoryDto> categoryDto;
    private List<MenuDto> menuDtoList;

    public  PageloadDto(RestaurantDto restaurantDto, Set<CategoryDto> categoryDto, List<MenuDto> menuDtoList){
        this.restaurantDto = restaurantDto;
        this.categoryDto = categoryDto;
        this.menuDtoList = menuDtoList;
    }
}
