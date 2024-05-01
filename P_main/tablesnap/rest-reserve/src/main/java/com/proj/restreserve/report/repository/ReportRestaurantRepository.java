package com.proj.restreserve.report.repository;

import com.proj.restreserve.report.entity.ReportRestaurant;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRestaurantRepository extends JpaRepository <ReportRestaurant, String> {
    List<ReportRestaurant> findByUser(User user);

    Optional<ReportRestaurant> findByRestaurantRestaurantid(String restaurantId);

    Page<ReportRestaurant> findByReportrestcheck(String reportrestcheck,Pageable pageable);
}
