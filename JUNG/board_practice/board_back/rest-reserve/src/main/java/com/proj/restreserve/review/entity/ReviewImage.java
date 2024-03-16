package com.proj.restreserve.review.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviewimage")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "reviewid")
    private Review review;
}