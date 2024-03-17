package com.proj.restreserve.payment;

import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.restaurant.RestaurantDto;
import com.proj.restreserve.user.User;
import com.proj.restreserve.user.UserDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PaymentDto {
    private String paymentid;

    private String totalprice;

    private Date day;

    private Boolean paymentcheck;

    private String userid;

    private String restaurantid;
}
