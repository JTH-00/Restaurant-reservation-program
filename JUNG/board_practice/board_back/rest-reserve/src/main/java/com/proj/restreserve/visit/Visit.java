package com.proj.restreserve.visit;

import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "visit")
public class Visit {

    @Id
    @Column(name = "visitid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String visitid;

    @Column(name = "visittime", nullable = false)
    private LocalDateTime visittime;

    @Column(name= "visitcheck", nullable = false)
    private Boolean visitcheck;

    @Column(name="visitcustomers",nullable = false)
    private String visitcustomers;

    @ManyToOne
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
}
