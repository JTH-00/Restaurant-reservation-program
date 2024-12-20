package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminBoardController {

    private final BoardService boardService;

    @GetMapping("/board/event")
    public ResponseEntity <Page<EventDto>> eventlist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.eventlist(page,10));
    }

    @GetMapping("/board/event/detail/{eventid}")
    public ResponseEntity <EventDto> eventdetail(@PathVariable("eventid") String eventid){
        EventDto eventDto = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/board/notice")
    public ResponseEntity <Page<NoticeDto>> noticelist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.noticelist(page,10));
    }

    @GetMapping("/board/notice/detail/{noticeid}")
    public ResponseEntity <NoticeDto> noticedetail(@PathVariable("noticeid") String noticeid){
        NoticeDto noticeDto = boardService.noticedetail(noticeid);
        return ResponseEntity.ok(noticeDto);
    }
}
