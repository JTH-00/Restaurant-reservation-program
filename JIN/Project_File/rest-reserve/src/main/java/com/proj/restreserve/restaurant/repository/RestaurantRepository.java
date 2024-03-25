package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,String> {
    @EntityGraph(attributePaths = {"userid"})//fetch join으로 쿼리 하나로 묶기
    Restaurant findByRestaurantid(String restaurantid);
    @EntityGraph(attributePaths = {"userid"})//fetch join으로 쿼리 하나로 묶기
    Restaurant findByCategory(String category);
    @Query("SELECT r FROM Restaurant r WHERE r.category = :category ORDER BY r.reviewcount DESC")
    List<Restaurant> findManyReviewByCategory(String category);//카테고리로 검색 내림차순으로 리스트 출력
    
    @Query(value = "SELECT * FROM Restaurant order by RAND() limit 8",nativeQuery = true)
    List<Restaurant> findRandom8Restaurants();
}
