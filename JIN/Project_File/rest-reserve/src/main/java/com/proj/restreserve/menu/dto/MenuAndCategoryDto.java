package com.proj.restreserve.menu.dto;

import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data

public class MenuAndCategoryDto {
    private List<SelectMenuDto> selectMenuDtoList;
    private Set<MenuCategoryDto> CategoryList;

    public MenuAndCategoryDto(Set<MenuCategoryDto> CategoryList, List<SelectMenuDto> selectMenuDtoList){
        this.CategoryList = CategoryList;
        this.selectMenuDtoList = selectMenuDtoList;
    }
}
