package com.proj.restreserve.report.repository;

import com.proj.restreserve.report.entity.ReportRestaurant;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRestaurantRepository extends JpaRepository <ReportRestaurant, String> {
    List<ReportRestaurant> findByUser(User user);
}
