package com.proj.restreserve.review;


import com.proj.restreserve.restaurant.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,String> {
    List<ReviewImage> findByReview_Reviewid(String reviewid);
}
