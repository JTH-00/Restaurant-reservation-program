package com.proj.restreserve.menu;

import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.restaurant.RestaurantDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class MenuDto {

    private String menuid;

    private String name;

    private String content;

    private String price;

    private CategoryDto categoryid;
}
