package com.proj.restreserve.visit.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository <Visit,String> {
    Page<Visit> findByUser(User user,Pageable pageable);
    Boolean existsByUserAndVisittime(User user, LocalDateTime visittime);
    //방문 예약 신청 리스트
    Page<Visit> findByVisitcheckFalseAndRestaurant(Restaurant restaurant, Pageable pageable);
    //방문 예약 신청 수락 리스트
    List<Visit> findByVisitcheckTrueAndRestaurantAndVisittimeBefore(Restaurant restaurant, LocalDateTime time);
    List<Visit> findByUserAndVisitcheckFalse(User user);
}
