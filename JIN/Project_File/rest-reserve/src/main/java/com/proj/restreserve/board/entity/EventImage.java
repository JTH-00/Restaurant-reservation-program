package com.proj.restreserve.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proj.restreserve.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name="eventimage")
public class EventImage implements Persistable<String> {
    @Id
    private String eventimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "eventid")
    @JsonBackReference
    private Event event;

    @Override
    public String getId() {
        return eventimageid;
    }
    @Override
    public boolean isNew() {
        return true; //UUID를 직접 set하여 .save()사용시 merge를 실행하여 id값이 변경되는 문제와 select문이 나오는걸 막으려 사용
    }
}
