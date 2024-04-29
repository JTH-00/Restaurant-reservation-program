package com.proj.restreserve.report.repository;

import com.proj.restreserve.report.entity.ReportReview;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportReviewRepository extends JpaRepository <ReportReview, String> {
    List<ReportReview> findByUser(User user);
    List<ReportReview> findByReviewReviewid(String reviewid);

    Page<ReportReview> findAll(Pageable pageable);
}
