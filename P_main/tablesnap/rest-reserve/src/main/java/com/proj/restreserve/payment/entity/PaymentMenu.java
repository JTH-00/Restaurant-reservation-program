package com.proj.restreserve.payment.entity;

import com.proj.restreserve.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "paymentmenu")
public class PaymentMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "paymentmenuid")
    private String paymentmenuid;

    @ManyToOne
    @JoinColumn(name="paymentid")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name="menuid")
    private Menu menu;

    @Column(name = "count")
    private Integer count;
}
