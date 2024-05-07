package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant, String> {
    Restaurant findByRestaurantid(String restaurantid);

    List<Restaurant> findByCategoryAndVibe(String category, String vibe);

    List<Restaurant> findByCategory(String category);

    List<Restaurant> findByVibe(String vibe);
}
