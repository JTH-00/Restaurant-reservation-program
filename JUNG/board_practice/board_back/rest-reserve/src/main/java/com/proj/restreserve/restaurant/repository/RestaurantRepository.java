package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant, String> {
    Restaurant findByRestaurantid(String restaurantid);
}
