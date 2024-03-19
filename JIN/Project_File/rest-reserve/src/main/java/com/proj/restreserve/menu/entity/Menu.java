package com.proj.restreserve.menu.entity;

import com.proj.restreserve.category.entity.Category;
import com.proj.restreserve.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="menu")
public class Menu {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String menuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menuimageid", nullable = true)
    private MenuImage menuimageid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menucategoryid", nullable = false)
    private Category categoryid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurantid", nullable = false)
    private Restaurant restaurantid;
}
