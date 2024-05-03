package com.proj.restreserve.businessnumber.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "businessnumber")
public class BusinessNumber {

    @Id
    @Column(name = "businessid", nullable = false)
    private String businessid;

    @Column(name = "imagelink", nullable = false)
    private String imagelink;
}
