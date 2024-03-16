package com.proj.restreserve.review;

import com.proj.restreserve.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReviewDto {
    private String reviewid;
    private Integer scope;
    private String content;
    private LocalDate date;
    private String userid;
    private List<String> imageLinks;
}
