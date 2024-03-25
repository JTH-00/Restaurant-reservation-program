package com.proj.restreserve.review.repository;

import com.proj.restreserve.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    @EntityGraph(attributePaths = "userid")
    Page<Review> findByPayment_Restaurantid_Restaurantid(String restaurantid,Pageable pageable);


}
