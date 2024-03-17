package com.proj.restreserve.restaurant;

import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;

@Entity
@Data
@Table(name="restaurant")
public class Restaurant {
    @Id
    @Column(name = "restaurantid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String restaurantid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String closeddays;

    @Column(nullable = false)
    private Time opentime;

    @Column(nullable = false)
    private Time closetime;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String cookingtime;

    @Column(nullable = false)
    private String vibe;

    @Column(nullable = false)
    private String address;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean ban;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean stopsales;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="userid", nullable = false)
    private User userid;
}
