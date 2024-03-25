package com.proj.restreserve.visit.entity;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
@DynamicInsert
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

    @ColumnDefault("false")
    @Column(name= "visitcheck", nullable = false)
    private Boolean visitcheck;

    @Column(name="visitcustomers",nullable = false)
    private int visitcustomers;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="userid")
    private User user;
}
