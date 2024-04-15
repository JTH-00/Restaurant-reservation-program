package com.proj.restreserve.menu.dto;

import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import com.proj.restreserve.menucategory.entity.MenuCategory;
import com.proj.restreserve.restaurant.entity.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    private String menuid;
    private String name;
    private String content;
    private String price;
    private String menuCategoryId;
    private List<String> imageLinks;
    private Restaurant restaurant;
}
