package com.proj.restreserve.review.dto;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.visit.entity.Visit;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReviewDto {
    private String reviewid;
    private int scope;
    private String content;
    private LocalDate date;
    private String userid;
    private Payment payment;
    private Visit visit;
    private List<String> imageLinks;
    private List<String> deleteLinks;
}
