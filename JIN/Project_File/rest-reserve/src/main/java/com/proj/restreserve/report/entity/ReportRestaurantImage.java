package com.proj.restreserve.report.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Table(name = "reportrestimage")
public class ReportRestaurantImage implements Persistable<String> {
    @Id
    private String reportrestimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name="reportrestaurantid")
    @JsonBackReference
    private ReportRestaurant reportRestaurant;

    @Override
    public String getId() {
        return reportrestimageid;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
