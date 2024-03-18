package com.proj.restreserve.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="restaurant")
public class Restaurant {

    @Id
    @Column(name="restaurantid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String restaurantid;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "closeddays",nullable = false)
    private String closeddays;

    @Column(name = "opentime",nullable = false)
    private Time opentime;

    @Column(name = "closetime",nullable = false)
    private Time closetime;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "stopsales",nullable = false)
    private Boolean stopsales;

    @Column(name = "cookingtime",nullable = false)
    private String cookingtime;

    @Column(name = "ban",nullable = false)
    private Boolean ban;

    @Column(name = "vibe",nullable = false)
    private String vibe;

    @Column(name = "address",nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RestaurantImage> restaurantimages = new ArrayList<>(); // 연관된 이미지들


}
