package com.proj.restreserve.menu;

import com.proj.restreserve.category.CategoryDto;
import lombok.Data;

@Data
public class MenuDto {

    private String menuid;

    private String name;

    private String content;

    private String price;

    private CategoryDto categoryid;
}
