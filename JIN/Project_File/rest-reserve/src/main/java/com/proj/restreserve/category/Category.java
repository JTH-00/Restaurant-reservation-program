package com.proj.restreserve.category;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="menucategory")
public class Category {
    @Id
    @Column(name="menucategoryid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryid;

    @Column(nullable = false)
    private String name;
}
