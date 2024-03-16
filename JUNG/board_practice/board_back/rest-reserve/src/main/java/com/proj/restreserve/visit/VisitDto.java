package com.proj.restreserve.visit;

import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.user.User;
import jakarta.persistence.*;
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
