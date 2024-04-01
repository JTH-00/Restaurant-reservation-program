package com.proj.restreserve.report.repository;


import com.proj.restreserve.report.entity.ReportRestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRestaurantImageRepository extends JpaRepository<ReportRestaurantImage,String> {
    List<ReportRestaurantImage> findByReportRestaurant_Reportrestaurantid(String reportrestaurantid);
}
