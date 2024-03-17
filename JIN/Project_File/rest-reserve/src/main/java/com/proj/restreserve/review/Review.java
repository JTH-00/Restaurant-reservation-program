package com.proj.restreserve.review;

import com.proj.restreserve.payment.Payment;
import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="review")
public class Review {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewid;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int scope;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="userid", nullable = false)
    private User userid;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewimages = new ArrayList<>(); // 연관된 이미지들

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="paymentid", nullable = false)
    private Payment payment;
}
