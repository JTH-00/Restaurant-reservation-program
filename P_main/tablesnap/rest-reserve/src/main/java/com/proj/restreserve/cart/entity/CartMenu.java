package com.proj.restreserve.cart.entity;

import com.proj.restreserve.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cartmenu")
public class CartMenu {
    @Id
    @Column(name = "cartmenuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartmenuid;

    @ManyToOne
    @JoinColumn(name = "cartid")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menuid")
    private Menu menu;

    @Column(name = "menucount", nullable = false)
    private Integer menucount;
}
