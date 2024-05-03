package com.proj.restreserve.menucategory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="menucategory")
public class MenuCategory {
    @Id
    @Column(name="menucategoryid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String menucategoryid;

    @Column(name = "name", nullable = false)
    private String name;
}
