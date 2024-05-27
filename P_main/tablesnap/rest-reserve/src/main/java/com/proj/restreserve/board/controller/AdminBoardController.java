package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name= "AdminBoard", description = "업주의 공지사항, 이벤트 게시글 조회 API")
public class AdminBoardController {

    private final BoardService boardService;

    @GetMapping("/board/event")
    @Operation(summary = "진행중인 이벤트 리스트 조회", description = "최신순을 기준으로 진행중인 이벤트의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity <Page<EventDto>> eventlist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.eventlist(page,10));
    }

    @GetMapping("/board/event/detail/{eventid}")
    @Operation(summary = "진행중인 이벤트 상세페이지 조회", description = "진행중인 이벤트의 상세 페이지 입니다.<br>" +
            "현재 조회중인 이벤트의 id값을 파라미터로 받습니다.")
    public ResponseEntity <EventDto> eventdetail(@PathVariable("eventid") String eventid){
        EventDto eventDto = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/board/notice")
    @Operation(summary = "공지사항 리스트 조회", description = "최신순을 기준으로 공지사항의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity <Page<NoticeDto>> noticelist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.noticelist(page,10));
    }

    @GetMapping("/board/notice/detail/{noticeid}")
    @Operation(summary = "공지사항 상세페이지 조회", description = "공지사항의 상세 페이지 입니다.<br>" +
            "현재 조회중인 공지사항의 id값을 파라미터로 받습니다.")
    public ResponseEntity <NoticeDto> noticedetail(@PathVariable("noticeid") String noticeid){
        NoticeDto noticeDto = boardService.noticedetail(noticeid);
        return ResponseEntity.ok(noticeDto);
    }
}
