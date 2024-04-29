package com.proj.restreserve.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@DynamicUpdate
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

    @ColumnDefault("0")
    @Column(nullable = false)
    private int reviewcount;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RestaurantImage> restaurantimages = new ArrayList<>(); // 연관된 이미지들

    @Column(name= "permitday")
    private LocalDate permitday; //승인 요청일자

    @JsonInclude(JsonInclude.Include.ALWAYS)//null도 json출력되게 설정
    @Column(name = "permitcheck")
    private Boolean permitcheck; //true면 승인 false면 거절, null이면 확인중, 거절후  재승인을 했을때도 null로 초기화
}
