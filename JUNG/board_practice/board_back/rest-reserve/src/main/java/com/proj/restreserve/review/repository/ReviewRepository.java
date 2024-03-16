package com.proj.restreserve.review.repository;

import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    List<Review> findByUser(User user);
}
