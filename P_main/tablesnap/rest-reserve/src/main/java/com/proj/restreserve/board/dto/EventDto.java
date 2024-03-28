package com.proj.restreserve.board.dto;

import com.proj.restreserve.user.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EventDto {
    private String eventid;
    private String title;
    private String content;
    private LocalDate date;
    private String eventstart;
    private String eventend;
    private Boolean eventstatus;
    private User user;
    private List<String> eventimageLinks;
}
