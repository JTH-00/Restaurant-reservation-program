package com.proj.restreserve.review.entity;

import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="review")
public class Review {
    @Id
    @Column(name= "reviewid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewid;

    @Column(name = "scope")
    private Integer scope;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewimages = new ArrayList<>(); // 연관된 이미지들
}
