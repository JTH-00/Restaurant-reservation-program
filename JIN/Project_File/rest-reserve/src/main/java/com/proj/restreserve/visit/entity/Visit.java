package com.proj.restreserve.visit.entity;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
@DynamicInsert
@Entity
@Data
@Table(name = "visit")
public class Visit implements Persistable<String> {

    @Id
    @Column(name = "visitid")
    private String visitid;

    @Column(name = "visittime", nullable = false)
    private LocalDateTime visittime;

    @ColumnDefault("false")
    @Column(name= "visitcheck", nullable = false)
    private Boolean visitcheck;

    @Column(name="visitcustomers",nullable = false)
    private int visitcustomers;

    @ManyToOne
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @Override
    public String getId() {
        return visitid;
    }

    @Override
    public boolean isNew() {
        return true; //UUID를 직접 set하여 .save()사용시 merge를 실행하여 id값이 변경되는 문제와 select문이 나오는걸 막으려 사용
    }
}
