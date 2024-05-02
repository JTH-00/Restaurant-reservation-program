package com.proj.restreserve.payment.entity;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "payment")
public class Payment implements Persistable<String> {
    @Id
    @Column(name = "paymentid", nullable = false)
    private String paymentid;

    @Column(name = "totalprice", nullable = false)
    private String totalprice;

    @Column(name = "paymentcheck", nullable = false)
    private Boolean paymentcheck;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    @ManyToOne
    @JoinColumn(name="restaurantid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @Override
    public String getId() {
        return paymentid;
    }

    @Override
    public boolean isNew() {
        return true; //UUID를 직접 set하여 .save()사용시 merge를 실행하여 id값이 변경되는 문제와 select문이 나오는걸 막으려 사용
    }
}