package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.review.entity.ReviewImage;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "reportrestaurant")
public class ReportRestaurant {
    @Id
    @Column(name= "reportrestaurantid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportrestaurantid;

    @Column(name = "content")
    private String content;


    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reportrestcheck")
    private String reportrestcheck;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "reportRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReportRestaurantImage> reportrestaurantimages = new ArrayList<>(); // 연관된 이미지들

    @ManyToOne
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;
}
