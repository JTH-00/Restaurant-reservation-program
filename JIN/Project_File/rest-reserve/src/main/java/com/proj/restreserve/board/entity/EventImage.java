package com.proj.restreserve.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proj.restreserve.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="eventimage")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "eventid")
    @JsonBackReference
    private Event event;
}
