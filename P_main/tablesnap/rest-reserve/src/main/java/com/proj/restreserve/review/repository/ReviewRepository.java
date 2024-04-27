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
    @Query("SELECT r FROM Review r WHERE r.reviewReply IS NULL AND r.payment.restaurant.restaurantid = :restaurantid")
    Page<Review> findByWithoutReplyAndPayment_Restaurantid(String restaurantid, Pageable pageable);
    @Query("SELECT r FROM Review r WHERE r.reviewReply IS NULL AND r.visit.restaurant.restaurantid = :restaurantid")
    Page<Review> findByWithoutReplyAndVisit_Restaurantid(String restaurantid, Pageable pageable);
    @EntityGraph(attributePaths = "user")
    //포장예약만을 기준으로 리뷰조회(정렬 별도로 해야함)
    Page<Review> findByPayment_Restaurant_Restaurantid(String restaurantid, Pageable pageable);

    void deleteByReviewid(String reviewid);

    @EntityGraph(attributePaths = "user")
    //방문예약만을 기준으로 리뷰조회(정렬 별도로 해야함)
    Page<Review> findByVisit_Restaurant_Restaurantid(String restaurantid, Pageable pageable);

    Page<Review> findByReviewid(String reviewid, Pageable pageable);
}
