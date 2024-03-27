package com.proj.restreserve.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Review review;
}