package com.proj.restreserve.payment.entity;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="payment")
public class Payment {//결제 테이블,이용 내역 조회에도 사용
    @Id
    @Column(name="paymentid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentid;

    @Column(nullable = false)
    private String totalprice;

    @Column(nullable = false)
    private Date day;

    @Column(nullable = false)
    private Boolean paymentcheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userid",  nullable = false)
    private User userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurantid", nullable = false)
    private Restaurant restaurantid;
}
