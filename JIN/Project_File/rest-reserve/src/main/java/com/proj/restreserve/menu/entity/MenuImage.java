package com.proj.restreserve.menu.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "menuimage")
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String menuimageid;

    @Column(nullable = false)
    private String menuimagelink;
}