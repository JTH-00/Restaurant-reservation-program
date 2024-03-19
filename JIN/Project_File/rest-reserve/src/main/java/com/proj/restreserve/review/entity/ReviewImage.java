package com.proj.restreserve.review.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviewimage")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String reviewimageid;

    @Column(nullable = false)
    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "reviewid")
    private Review review;
}