package com.proj.restreserve.restaurant.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
public class SelectRestaurantDto {
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
    private int reviewcount;
    private DetailUserDto user;
    private List<String> restaurantimageLinks;
    private LocalDate permitday;
    private Boolean permitcheck;
}
