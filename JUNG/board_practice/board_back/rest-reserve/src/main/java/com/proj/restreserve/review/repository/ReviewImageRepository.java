package com.proj.restreserve.review.repository;


import com.proj.restreserve.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,String> {
    List<ReviewImage> findByReview_Reviewid(String reviewid);
}
