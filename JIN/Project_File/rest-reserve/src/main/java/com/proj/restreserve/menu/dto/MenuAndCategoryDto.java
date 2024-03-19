package com.proj.restreserve.menu.dto;

import com.proj.restreserve.category.dto.CategoryDto;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data

public class MenuAndCategoryDto {
    private List<MenuDto> menuDtoList;
    private Set<CategoryDto> CategoryList;

    public MenuAndCategoryDto(Set<CategoryDto> CategoryList, List<MenuDto> menuDtoList){
        this.CategoryList = CategoryList;
        this.menuDtoList = menuDtoList;
    }
}
