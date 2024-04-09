package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Table(name = "reportreviewimage")
public class ReportReviewImage implements Persistable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportreviewimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name="reportreviewid")
    @JsonBackReference
    private ReportReview reportReview;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
