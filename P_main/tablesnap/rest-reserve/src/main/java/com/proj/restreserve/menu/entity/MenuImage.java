package com.proj.restreserve.menu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "menuimage")
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menuimageid", nullable = false)
    private String menuimageid;

    @Column(name = "menuimagelink", nullable = false)
    private String menuimagelink;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "menuid")
    private Menu menu;
}