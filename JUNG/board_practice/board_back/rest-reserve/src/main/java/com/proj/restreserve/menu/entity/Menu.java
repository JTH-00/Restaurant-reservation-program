package com.proj.restreserve.menu.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.menucategory.entity.MenuCategory;
import com.proj.restreserve.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="menu")
public class Menu {
    @Id
    @Column(name = "menuid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String menuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private String price;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MenuImage> menuimages = new ArrayList<>(); // 연관된 이미지들

    @ManyToOne
    @JoinColumn(name="menucategoryid", nullable = false)
    private MenuCategory menuCategory;

    @ManyToOne
    @JoinColumn(name="restaurantid", nullable = false)
    private Restaurant restaurant;

}
