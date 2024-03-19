package com.proj.restreserve.payment.entity;

import com.proj.restreserve.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="paymentmenu")
public class PaymentMenu {//payment와 menu의 중간 테이블로 구매한 메뉴 하나를 저장
    @Id
    @Column(name = "paymentmenuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentmenuid;

    @ManyToOne
    @JoinColumn(name = "paymentid")
    private Payment paymentid;

    @ManyToOne
    @JoinColumn(name = "menuid")
    private Menu menuid;

    @Column(name="count")
    private int count;
}
