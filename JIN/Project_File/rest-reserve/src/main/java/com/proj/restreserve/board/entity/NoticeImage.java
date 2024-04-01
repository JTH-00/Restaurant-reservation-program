package com.proj.restreserve.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="noticeimage")
public class NoticeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String noticeimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "noticeid")
    @JsonBackReference
    private Notice notice;
}
