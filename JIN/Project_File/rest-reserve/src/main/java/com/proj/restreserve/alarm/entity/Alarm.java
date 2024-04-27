package com.proj.restreserve.alarm.entity;

import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name="alarm")
public class Alarm {
    @Id
    @Column(name="alarmid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String alarmid;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "url")
    private String url;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
}
