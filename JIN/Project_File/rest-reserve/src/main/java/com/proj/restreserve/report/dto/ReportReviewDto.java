package com.proj.restreserve.report.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.report.entity.ReportReviewImage;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReportReviewDto {
    private String reportreviewid;
    private String content;
    private LocalDate date;
    private String reportreviewcheck;
    private User user;
    private List<String> reportreviewimages;
    private Review review;
}
