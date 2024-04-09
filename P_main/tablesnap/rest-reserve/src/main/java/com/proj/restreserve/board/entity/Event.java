package com.proj.restreserve.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DynamicUpdate
@Table(name="event")
public class Event {
    @Id
    @Column(name="eventid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "eventstart", nullable = false)
    private String eventstart;

    @Column(name = "eventend", nullable = false)
    private String eventend;

    @Column(name = "eventstatus", nullable = false)
    private Boolean eventstatus;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EventImage> eventimages = new ArrayList<>(); // 연관된 이미지들
}
