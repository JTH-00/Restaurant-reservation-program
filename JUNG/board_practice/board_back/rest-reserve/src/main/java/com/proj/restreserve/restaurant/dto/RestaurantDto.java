package com.proj.restreserve.restaurant.dto;

import lombok.Data;

import java.sql.Time;
import java.util.List;

@Data
public class RestaurantDto {
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
    private Boolean ban;
    private String vibe;
    private String address;
    private int reviewcount;
    private String userid;
    private List<String> restaurantimageLinks;
}

