package com.proj.restreserve.payment;

import com.proj.restreserve.menu.Menu;
import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.review.Review;
import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name="userid")
    private User userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menuid")
    private Menu menuid;

    @OneToMany(mappedBy = "paymentid", fetch = FetchType.LAZY)//구매한 메뉴들을 PaymentMenu를 통해 컬렉션 저장
    private Set<PaymentMenu> paymentMenus = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reviewid", nullable = false)
    private Review review;
}
