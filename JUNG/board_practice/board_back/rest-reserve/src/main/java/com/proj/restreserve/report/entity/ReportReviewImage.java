package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reportreviewimage")
public class ReportReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportreviewimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name="reportreviewid")
    @JsonBackReference
    private ReportReview reportReview;
}
