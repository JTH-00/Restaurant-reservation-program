package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "reportreview")
public class ReportReview {
    @Id
    @Column(name= "reportreviewid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportreviewid;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reportreviewcheck")
    private String reportreviewcheck;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "reportReview", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReportReviewImage> reportreviewimages = new ArrayList<>(); // 연관된 이미지들

    @ManyToOne
    @JoinColumn(name="reviewid")
    private Review review;
}
