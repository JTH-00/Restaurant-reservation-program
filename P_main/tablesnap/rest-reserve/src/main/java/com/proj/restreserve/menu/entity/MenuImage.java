package com.proj.restreserve.menu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
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
}