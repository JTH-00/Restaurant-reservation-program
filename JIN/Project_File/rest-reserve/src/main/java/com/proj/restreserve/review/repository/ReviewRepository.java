package com.proj.restreserve.review.repository;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    List<Review> findByUser(User user);
    @EntityGraph(attributePaths = "user")
    //포장예약만을 기준으로 리뷰조회(정렬 별도로 해야함)
    Page<Review> findByPayment_Restaurant_Restaurantid(String restaurantid, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    //방문예약만을 기준으로 리뷰조회(정렬 별도로 해야함)
    Page<Review> findByVisit_Restaurant_Restaurantid(String restaurantid, Pageable pageable);

    @Query(value = "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
            "INNER JOIN Payment p ON p.paymentid = r.paymentid " +
            "WHERE p.restaurantid = :restaurantid " +
            "UNION " +
            "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
            "INNER JOIN Visit v ON v.visitid = r.visitid " +
            "WHERE v.restaurantid = :restaurantid " +
            "ORDER BY date DESC",
            countQuery = "SELECT COUNT(*) FROM (SELECT r.reviewid FROM Review r " +
                    "INNER JOIN Payment p ON p.paymentid = r.paymentid " +
                    "WHERE p.restaurantid = :restaurantid " +
                    "UNION " +
                    "SELECT r.reviewid FROM Review r " +
                    "INNER JOIN Visit v ON v.visitid = r.visitid " +
                    "WHERE v.restaurantid = :restaurantid) AS countQuery",
            nativeQuery = true)
    //네이티브 쿼리를 사용하여 Union 사용하여 Payment와 Visit 둘중 하나라도 양쪽테이블이 일치하는 경우를 합쳐서 Select
    //여기서 내림차순으로 정렬
    Page<Review> findReviewsByRestaurant(String restaurantid, Pageable pageable);
    @Query(value =
            "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
                    "INNER JOIN Payment p ON p.paymentid = r.paymentid " +
                    "WHERE p.restaurantid = :restaurantid " +
                    "UNION " +
                    "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
                    "INNER JOIN Visit v ON v.visitid = r.visitid " +
                    "WHERE v.restaurantid = :restaurantid " +
                    "ORDER BY date DESC, scope DESC",
            countQuery = "SELECT COUNT(*) FROM (SELECT r.reviewid FROM Review r " +
                    "INNER JOIN Payment p ON p.paymentid = r.paymentid " +
                    "WHERE p.restaurantid = :restaurantid " +
                    "UNION " +
                    "SELECT r.reviewid FROM Review r " +
                    "INNER JOIN Visit v ON v.visitid = r.visitid " +
                    "WHERE v.restaurantid = :restaurantid) AS countQuery",
            nativeQuery = true)
        //네이티브 쿼리를 사용하여 Union 사용하여 Payment와 Visit 둘중 하나라도 양쪽테이블이 일치하는 경우를 합쳐서 Select
        //여기서 내림차순으로 정렬
    Page<Review> findReviewsByRestaurantToDescScope(String restaurantid, Pageable pageable);
}
