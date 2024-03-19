package com.proj.restreserve.menu.dto;

import com.proj.restreserve.category.dto.CategoryDto;
import lombok.Data;

@Data
public class MenuDto {

    private String menuid;

    private String name;

    private String content;

    private String price;

    private CategoryDto categoryid;

    private String imagelink;
}
