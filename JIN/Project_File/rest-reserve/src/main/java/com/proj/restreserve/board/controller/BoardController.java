package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/admin/write/event")
    public ResponseEntity<?> writeevent(@ModelAttribute EventDto eventDto, @RequestParam("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writeevent(eventDto,files));
    }

    @PostMapping("/admin/write/notice")
    public ResponseEntity<?> writenotice(@ModelAttribute NoticeDto noticeDto, @RequestParam("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writenotice(noticeDto,files));
    }

    @GetMapping("/user/board/event")
    public ResponseEntity <List<EventDto>> eventlist(){
        return ResponseEntity.ok(boardService.eventlist());
    }

    @GetMapping("/user/board/event/detail/{eventid}")
    public ResponseEntity <EventDto> eventdetail(@PathVariable("eventid") String eventid){
        EventDto eventDto = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/user/board/notice")
    public ResponseEntity <List<NoticeDto>> noticelist(){
        return ResponseEntity.ok(boardService.noticelist());
    }

    @GetMapping("/user/board/notice/detail/{noticeid}")
    public ResponseEntity <NoticeDto> noticedetail(@PathVariable("noticeid") String noticeid){
        NoticeDto noticeDto = boardService.noticedetail(noticeid);
        return ResponseEntity.ok(noticeDto);
    }
}
