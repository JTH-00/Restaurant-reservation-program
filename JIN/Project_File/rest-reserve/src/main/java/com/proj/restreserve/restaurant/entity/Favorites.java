package com.proj.restreserve.restaurant.entity;

import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

;

@Entity
@Data
@Table(name = "favorites")
public class Favorites {
    @Id
    @Column(name= "favoritesid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String favoritesid;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurantid")
    private Restaurant restaurant;
}
