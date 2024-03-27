package com.proj.restreserve.menu.dto;

import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import lombok.Data;

@Data
public class SelectMenuDto {

    private String menuid;

    private String name;

    private String content;

    private String price;

    private MenuCategoryDto categoryid;

    private String imagelink;
}
