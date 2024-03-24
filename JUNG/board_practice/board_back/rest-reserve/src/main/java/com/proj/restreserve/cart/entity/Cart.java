package com.proj.restreserve.cart.entity;

import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="cart")
public class Cart {

    @Id
    @Column(name = "cartid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cartid;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}
