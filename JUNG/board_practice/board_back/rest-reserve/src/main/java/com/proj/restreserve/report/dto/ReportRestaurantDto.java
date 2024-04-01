package com.proj.restreserve.report.dto;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReportRestaurantDto {
    private String reportrestaurantid;
    private String content;
    private LocalDate date;
    private User user;
    private String reportrestcheck;
    private List<String> reportrestaurantimages;
    private Restaurant restaurant;
}
