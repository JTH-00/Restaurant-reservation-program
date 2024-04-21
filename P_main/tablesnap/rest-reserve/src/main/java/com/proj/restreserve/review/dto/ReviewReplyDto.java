package com.proj.restreserve.review.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewReplyDto {
    private String reviewreplyid;
    private DetailUserDto user;
    private LocalDate date;
    private String content;
    private SelectReviewDto review;
}
