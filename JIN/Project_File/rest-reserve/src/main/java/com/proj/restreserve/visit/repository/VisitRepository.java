package com.proj.restreserve.visit.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository <Visit,String> {
    List<Visit> findByUser(User user);
    Boolean existsByUserAndVisittime(User user, LocalDateTime visittime);
    //방문 예약 신청 리스트
    List<Visit> findByVisitcheckFalseAndRestaurant(Restaurant restaurant);
    //방문 예약 신청 수락 리스트
    List<Visit> findByVisitcheckTrueAndRestaurantAndVisittimeBefore(Restaurant restaurant, LocalDateTime time);


}
