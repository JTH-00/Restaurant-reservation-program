package com.proj.restreserve.review.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@Table(name="review")
public class Review {
    @Id
    @Column(name= "reviewid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewid;

    @Column(name = "scope")
    private int scope;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReviewImage> reviewimages = new ArrayList<>(); // 연관된 이미지들

    @ManyToOne
    @JoinColumn(name = "visitid")
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "paymentid")
    private Payment payment;
}
