package com.proj.restreserve.review;

import com.proj.restreserve.payment.Payment;
import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
}
