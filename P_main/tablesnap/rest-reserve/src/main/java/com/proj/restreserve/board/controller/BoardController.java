package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.entity.Event;
import com.proj.restreserve.board.entity.Notice;
import com.proj.restreserve.board.service.BoardService;
import com.proj.restreserve.review.dto.ReviewDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/superadmin/write/event",  consumes = {"multipart/form-data"})
    public ResponseEntity<Event> writeevent(
            @Valid @RequestPart EventDto eventDto,
            @RequestPart("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writeevent(eventDto,files));
    }

    @PostMapping(value = "/superadmin/write/notice",  consumes = {"multipart/form-data"})
    public ResponseEntity<Notice> writenotice(
            @Valid @RequestPart NoticeDto noticeDto,
            @RequestPart("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writenotice(noticeDto,files));
    }
    @PutMapping(value = "/superadmin/modify/event", consumes = {"multipart/form-data"})
    public ResponseEntity<EventDto> modifyEvent(
            @RequestParam(name="eventid") String eventid,
            @Valid @RequestPart("eventDto") EventDto eventDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(boardService.modifyEvent(eventid,eventDto , files,deleteImageLinks));
    }
    @PutMapping(value = "/superadmin/modify/notice", consumes = {"multipart/form-data"})
    public ResponseEntity<NoticeDto> modifyNotice(
            @RequestParam(name="noticeid") String noticeid,
            @Valid @RequestPart("noticeDto") NoticeDto noticeDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(boardService.modifyNotice(noticeid,noticeDto , files,deleteImageLinks));
    }
    @PostMapping(value = "/superadmin/delete/event")
    public ResponseEntity<?> deleteEvent(@RequestParam(name="eventid") String eventid) {
        try {
            boardService.deleteEvent(eventid);
            return ResponseEntity.ok("이벤트가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "/superadmin/delete/notice")
    public ResponseEntity<?> deleteNotice(@RequestParam(name="noticeid") String noticeid) {
        try {
            boardService.deleteNotice(noticeid);
            return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/board/event")
    public ResponseEntity <Page<EventDto>> eventlist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.eventlist(page,10));
    }

    @GetMapping("/user/board/event/detail/{eventid}")
    public ResponseEntity <EventDto> eventdetail(@PathVariable("eventid") String eventid){
        EventDto eventDto = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/user/board/notice")
    public ResponseEntity <Page<NoticeDto>> noticelist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.noticelist(page,10));
    }

    @GetMapping("/user/board/notice/detail/{noticeid}")
    public ResponseEntity <NoticeDto> noticedetail(@PathVariable("noticeid") String noticeid){
        NoticeDto noticeDto = boardService.noticedetail(noticeid);
        return ResponseEntity.ok(noticeDto);
    }
}
