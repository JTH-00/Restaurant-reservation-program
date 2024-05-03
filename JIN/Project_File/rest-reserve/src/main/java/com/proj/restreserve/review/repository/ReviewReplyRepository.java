package com.proj.restreserve.review.repository;

import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply,String> {
    ReviewReply findByReview(Review review);
}
