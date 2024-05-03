package com.proj.restreserve.report.repository;


import com.proj.restreserve.report.entity.ReportRestaurantImage;
import com.proj.restreserve.report.entity.ReportReview;
import com.proj.restreserve.report.entity.ReportReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportReviewImageRepository extends JpaRepository<ReportReviewImage,String> {
    List<ReportReviewImage> findByReportReview_Reportreviewid(String reportreviewid);
}
