package com.proj.restreserve.review.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import com.proj.restreserve.payment.dto.PaymentDto;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.visit.dto.VisitDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SelectReviewDto {
    private String reviewid;
    private int scope;
    private String content;
    private Date date;
    private DetailUserDto userid;
    private PaymentDto payment;
    private VisitDto visit;
    private List<PaymentMenuDto> paymentMenuDtos;
    private List<String> iamgeLinks;
}