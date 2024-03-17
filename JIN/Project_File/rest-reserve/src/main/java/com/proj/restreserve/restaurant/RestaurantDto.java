package com.proj.restreserve.restaurant;

import com.proj.restreserve.detailpage.DetailAndReviewPageUserDto;
import lombok.Data;

import java.sql.Time;

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
    private String vibe;
    private String address;
    private Boolean ban;
    private DetailAndReviewPageUserDto userid;
}
