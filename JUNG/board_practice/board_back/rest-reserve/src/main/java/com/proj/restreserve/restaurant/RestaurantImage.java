package com.proj.restreserve.restaurant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurantimage")
public class RestaurantImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String restaurantimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "restaurantid")
    private Restaurant restaurant;
}

