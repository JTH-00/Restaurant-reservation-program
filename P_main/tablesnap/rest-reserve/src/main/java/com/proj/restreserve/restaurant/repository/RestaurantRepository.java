package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository <Restaurant, String> {
    @EntityGraph(attributePaths = {"user"})//차단한 매장 조회 하는용
    Restaurant findByRestaurantidAndPermitcheckTrue(String restaurantid);
    @EntityGraph(attributePaths = {"user"})//fetch join으로 쿼리 하나로 묶기
    Restaurant findByRestaurantidAndBanFalseAndPermitcheckTrue(String restaurantid);
    @EntityGraph(attributePaths = {"user"})//fetch join으로 쿼리 하나로 묶기
    Restaurant findByCategory(String category);
    @Query("SELECT r FROM Restaurant r WHERE r.category = :category AND r.ban = false And r.permitcheck = true ORDER BY r.reviewcount DESC")
    List<Restaurant> findManyReviewByCategory(String category);//밴이 안된 레스토랑 중 카테고리로 검색 내림차순으로 리스트 출력

    @Query(value = "SELECT * FROM Restaurant WHERE ban = false And r.permitcheck = true ORDER BY RAND() LIMIT 8", nativeQuery = true)
    List<Restaurant> findRandom8Restaurants();//밴이 안된 레스토랑 무작위 8개

    Restaurant findByUser(User user);

    Page<Restaurant> findByPermitcheckIsNull(Pageable pageable);
}
