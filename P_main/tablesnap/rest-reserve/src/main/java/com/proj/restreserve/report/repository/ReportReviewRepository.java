package com.proj.restreserve.report.repository;

import com.proj.restreserve.report.entity.ReportReview;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportReviewRepository extends JpaRepository <ReportReview, String> {
    List<ReportReview> findByUser(User user);

    List<ReportReview> findByReviewReviewid(String reviewid);
}
