package com.proj.restreserve.review.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Table(name = "reviewimage")
public class ReviewImage implements Persistable<String> {
    @Id
    private String reviewimageid;

    private String imagelink;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewid")
    @JsonBackReference
    private Review review;

    @Override
    public String getId() {
        return reviewimageid;
    }
    @Override
    public boolean isNew() {
        return true; //UUID를 직접 set하여 .save()사용시 merge를 실행하여 id값이 변경되는 문제와 select문이 나오는걸 막으려 사용
    }
}