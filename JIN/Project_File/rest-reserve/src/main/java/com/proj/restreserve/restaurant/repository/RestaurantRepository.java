package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {
    @EntityGraph(attributePaths = {"userid"})//fetch join으로 쿼리 하나로 묶기
    Restaurant findByRestaurantid(String restaurantid);
}
