package com.proj.restreserve.restaurant.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class SelectRestaurantModifyDto {
    private String restaurantid;
    private String title;
    private String category;
    private String closeddays;
    private Time opentime;
    private Time closetime;
    private String content;
    private String phone;
    private Boolean stopsales;
    private String cookingtime;
    private String vibe;
    private String address;
    private DetailUserDto user;
    private List<String> restaurantimageLinks;
    private List<SelectMenuDto> menuDtos;
}
