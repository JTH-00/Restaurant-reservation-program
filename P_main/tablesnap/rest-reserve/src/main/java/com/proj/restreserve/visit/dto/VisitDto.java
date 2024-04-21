package com.proj.restreserve.visit.dto;

import com.proj.restreserve.restaurant.entity.Restaurant;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitDto {
    private String visitid;
    private LocalDateTime visittime;
    private Boolean visitcheck;
    private int visitcustomers;
    private Restaurant restaurant;
    private String userid;
}
