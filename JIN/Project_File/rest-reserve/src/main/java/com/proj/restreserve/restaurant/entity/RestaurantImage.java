package com.proj.restreserve.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurantimage")
public class RestaurantImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String restaurantimageid;

    @Column(nullable = false)
    private String imagelink;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "restaurantid", nullable = false)
    private Restaurant restaurant;
}

