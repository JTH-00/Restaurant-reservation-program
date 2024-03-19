package com.proj.restreserve.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="restaurant")
public class Restaurant {
    @Id
    @Column(name = "restaurantid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String restaurantid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String closeddays;

    @Column(nullable = false)
    private Time opentime;

    @Column(nullable = false)
    private Time closetime;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String cookingtime;

    @Column(nullable = false)
    private String vibe;

    @Column(nullable = false)
    private String address;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean ban;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean stopsales;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="userid", nullable = false)
    private User userid;


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantImage> restaurantimages = new ArrayList<>(); // 연관된 이미지들
}
