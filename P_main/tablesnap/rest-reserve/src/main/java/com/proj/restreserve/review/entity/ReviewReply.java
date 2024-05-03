package com.proj.restreserve.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="reviewreply")
public class ReviewReply {
    @Id
    @Column(name="reviewreplyid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewreplyid;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @Column(name="date")
    private LocalDate date;

    @Column(name="content")
    private String content;

    @OneToOne
    @JoinColumn(name="reviewid")
    @JsonBackReference
    private Review review;
}
