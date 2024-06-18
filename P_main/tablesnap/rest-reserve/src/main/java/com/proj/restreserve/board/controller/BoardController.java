package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
import com.proj.restreserve.board.dto.NoticeDto;
import com.proj.restreserve.board.entity.Event;
import com.proj.restreserve.board.entity.Notice;
import com.proj.restreserve.board.service.BoardService;
import com.proj.restreserve.review.dto.ReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name= "Board", description = "관리자의 공지사항, 이벤트 게시글 관리 및 사용자 조회 통합 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/superadmin/write/event",  consumes = {"multipart/form-data"})
    @Operation(summary = "이벤트 게시글 작성", description = "이벤트 게시글을 등록합니다.<br>" +
            "EventDto로 제목과 내용, 이벤트 시작일자와 종료일자를 입력하고, 사진을 같이 올려 저장합니다.<br>" +
            "최소 한장 이상의 사진을 요구합니다.")
    public ResponseEntity<Event> writeevent(
            @Valid @RequestPart EventDto eventDto,
            @RequestPart("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writeevent(eventDto,files));
    }

    @PostMapping(value = "/superadmin/write/notice",  consumes = {"multipart/form-data"})
    @Operation(summary = "공지사항 게시글 작성", description = "공지사항 게시글을 등록합니다.<br>" +
            "NoticeDto로 제목과 내용을 입력하고, 사진을 같이 올려 저장합니다.<br>" +
            "최소 한장 이상의 사진을 요구합니다.")
    public ResponseEntity<Notice> writenotice(
            @Valid @RequestPart NoticeDto noticeDto,
            @RequestPart("files")List<MultipartFile> files){
        return ResponseEntity.ok(boardService.writenotice(noticeDto,files));
    }
    @PutMapping(value = "/superadmin/modify/event", consumes = {"multipart/form-data"})
    @Operation(summary = "이벤트 게시글 수정", description = "이벤트 게시글의 작성을 수정합니다.<br>" +
            "파라미터로 수정할 이벤트의 id값을 가져온 뒤<br>"+
            "EventDto로 제목과 내용, 이벤트 시작일자와 종료일자를 수정하며,<br>" +
            "지울 파일의 경우 해당파일의 링크를 List로 저장하여 일련번호 추출 후 S3와 DB삭제 후 새로운 파일을 추가하여 수정합니다.<br>" +
            "추가할 사진의 경우 없어도 무방합니다.")
    public ResponseEntity<EventDto> modifyEvent(
            @RequestParam(name="eventid") String eventid,
            @Valid @RequestPart("eventDto") EventDto eventDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(boardService.modifyEvent(eventid,eventDto , files,deleteImageLinks));
    }
    @PutMapping(value = "/superadmin/modify/notice", consumes = {"multipart/form-data"})
    @Operation(summary = "공지사항 게시글 수정", description = "공지사항 게시글의 작성을 수정합니다.<br>" +
            "파라미터로 수정할 공지사항의 id값을 가져온 뒤<br>"+
            "EventDto로 제목과 내용을 수정하며,<br>" +
            "지울 파일의 경우 해당파일의 링크를 List로 저장하여 일련번호 추출 후 S3와 DB삭제 후 새로운 파일을 추가하여 수정합니다.<br>" +
            "추가할 사진의 경우 없어도 무방합니다.")
    public ResponseEntity<NoticeDto> modifyNotice(
            @RequestParam(name="noticeid") String noticeid,
            @Valid @RequestPart("noticeDto") NoticeDto noticeDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(boardService.modifyNotice(noticeid,noticeDto , files,deleteImageLinks));
    }
    @DeleteMapping(value = "/superadmin/event")
    @Operation(summary = "이벤트 게시글 삭제", description = "이벤트 게시글을 삭제합니다.<br>" +
            "파라미터로 삭제할 이벤트의 id값을 가져온 뒤 삭제합니다.<br>" +
            "삭제할 경우 해당 이벤트 게시글과 업로드된 사진들을 삭제합니다.")
    public ResponseEntity<?> deleteEvent(@RequestParam(name="eventid") String eventid) {
        try {
            boardService.deleteEvent(eventid);
            return ResponseEntity.ok("이벤트가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping(value = "/superadmin/notice")
    @Operation(summary = "공지사항 게시글 삭제", description = "공지사항 게시글을 삭제합니다.<br>" +
            "파라미터로 삭제할 공지사항의 id값을 가져온 뒤 삭제합니다.<br>" +
            "삭제할 경우 해당 공지사항 게시글과 업로드된 사진들을 삭제합니다.")
    public ResponseEntity<?> deleteNotice(@RequestParam(name="noticeid") String noticeid) {
        try {
            boardService.deleteNotice(noticeid);
            return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/board/event")
    @Operation(summary = "진행중인 이벤트 리스트 조회", description = "최신순을 기준으로 진행중인 이벤트의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity <Page<EventDto>> eventlist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.eventlist(page,10));
    }

    @GetMapping("/user/board/event/detail/{eventid}")
    @Operation(summary = "진행중인 이벤트 상세페이지 조회", description = "진행중인 이벤트의 상세 페이지 입니다.<br>" +
            "현재 조회중인 이벤트의 id값을 파라미터로 받습니다.")
    public ResponseEntity <EventDto> eventdetail(@PathVariable("eventid") String eventid){
        EventDto eventDto = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/user/board/notice")
    @Operation(summary = "공지사항 리스트 조회", description = "최신순을 기준으로 공지사항의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity <Page<NoticeDto>> noticelist(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(boardService.noticelist(page,10));
    }

    @GetMapping("/user/board/notice/detail/{noticeid}")
    @Operation(summary = "공지사항 상세페이지 조회", description = "공지사항의 상세 페이지 입니다.<br>" +
            "현재 조회중인 공지사항의 id값을 파라미터로 받습니다.")
    public ResponseEntity <NoticeDto> noticedetail(@PathVariable("noticeid") String noticeid){
        NoticeDto noticeDto = boardService.noticedetail(noticeid);
        return ResponseEntity.ok(noticeDto);
    }
}
