package com.proj.restreserve.visit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitDto {
    private String visitid;
    private LocalDateTime visittime;
    private Boolean visitcheck;
    private String visitcustomers;
    private String restaurantid;
    private String userid;
}
