package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reportrestimage")
public class ReportRestaurantImage {
    @Id
    private String reportrestimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name="reportrestaurantid")
    @JsonBackReference
    private ReportRestaurant reportRestaurant;
}
