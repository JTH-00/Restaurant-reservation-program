package com.proj.restreserve.board.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@Table(name = "notice")
public class Notice {
    @Id
    @Column(name = "noticeid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String noticeid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NoticeImage> noticeimages = new ArrayList<>(); // 연관된 이미지들
}
