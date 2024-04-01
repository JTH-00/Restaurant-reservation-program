package com.proj.restreserve.board.dto;

import com.proj.restreserve.user.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NoticeDto {
    private String noticeid;
    private String title;
    private String content;
    private LocalDate date;
    private User user;
    private List<String> noticeimageLinks;
}
