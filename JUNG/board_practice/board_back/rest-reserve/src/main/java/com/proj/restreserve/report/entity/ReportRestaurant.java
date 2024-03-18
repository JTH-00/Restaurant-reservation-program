package com.proj.restreserve.report.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reportrestaurant")
public class ReportRestaurant {
    @Id
    @Column(name= "reportrestaurantid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportrestaurantid;

    @Column(name = "content")
    private String content;


    @Column(name = "Date")
    private Integer Date;
}
