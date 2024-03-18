package com.proj.restreserve.payment.entity;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Payment {
    @Id
    @Column(name = "paymentid", nullable = false)
    private String paymentid;

    @Column(name = "totalprice", nullable = false)
    private String totalprice;

    @Column(name = "paymentcheck", nullable = false)
    private Boolean paymentcheck;

    @Column(name = "day", nullable = false)
    private LocalDate day;


    @ManyToOne
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
}