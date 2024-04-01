package com.proj.restreserve.restaurant.repository;


import com.proj.restreserve.restaurant.entity.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage,String> {
    List<RestaurantImage> findByRestaurant_Restaurantid(String restaurantid);
}
