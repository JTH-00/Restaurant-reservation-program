package com.proj.restreserve.board.controller;

import com.proj.restreserve.board.dto.EventDto;
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

    @GetMapping("/user/board/event")
    public ResponseEntity <List<EventDto>> eventlist(){
        return ResponseEntity.ok(boardService.eventlist());
    }

    @GetMapping("/user/board/event/detail/{eventid}")
    public ResponseEntity <List<EventDto>> eventdetail(@PathVariable("eventid") String eventid){
        List<EventDto> eventDtos = boardService.eventdetail(eventid);
        return ResponseEntity.ok(eventDtos);
    }
}
