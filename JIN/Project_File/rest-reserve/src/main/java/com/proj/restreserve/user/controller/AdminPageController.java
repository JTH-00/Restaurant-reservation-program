package com.proj.restreserve.user.controller;

import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.ReviewReply;
import com.proj.restreserve.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminPageController {
    private final ReviewService reviewService;
    @PostMapping(value = "/write/reply")
    public ResponseEntity<ReviewReply> writeReviewReply(
            @RequestParam(name="reviewid") String reviewid,
            @Valid @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.writeReply(reviewid,reviewDto));
    }
    @PutMapping (value = "/modify/reply")
    public ResponseEntity<ReviewReply> modifyReviewReply(
            @RequestParam(name="replyid") String replyid,
            @Valid @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.modifyReply(replyid,reviewDto));
    }
    @PostMapping(value = "/delete/reply")
    public ResponseEntity<String> deleteReviewReply(@RequestParam(name="replyid") String replyid) {
        try {
            reviewService.deleteReply(replyid);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
