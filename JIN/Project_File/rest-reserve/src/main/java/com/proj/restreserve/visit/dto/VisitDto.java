package com.proj.restreserve.visit.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitDto {
    private String visitid;
    private LocalDateTime visittime;
    private Boolean visitcheck;
    private int visitcustomers;
    private String restaurantid;
    private String userid;
}
