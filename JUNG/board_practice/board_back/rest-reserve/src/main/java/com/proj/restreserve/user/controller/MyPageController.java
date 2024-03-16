package com.proj.restreserve.user.controller;

import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.user.service.UserService;
import com.proj.restreserve.visit.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MyPageController {

    private final UserService userService;

    @GetMapping("/mypage/use")
    public ResponseEntity<List<VisitDto>> visitRestaur(){

        List<VisitDto> visitDtos = userService.MyRegistInfo().stream()
                .filter(visitDto -> visitDto.getVisitcheck())
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/reserve")
    public ResponseEntity<List<VisitDto>> reserveRestaur(){

        List<VisitDto> visitDtos = userService.MyRegistInfo().stream()
                .filter(visitDto -> !visitDto.getVisitcheck())
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/review")
    public ResponseEntity<List<ReviewDto>> reviewRestaur(){

        List<ReviewDto> reviewDtos = userService.MyReviewInfo();

        return ResponseEntity.ok(reviewDtos);
    }

    @PostMapping("/mypage/info")
    public ResponseEntity<String> entermodifyInfo(@RequestBody UserDto userDto) {
        Optional<User> optionalUser = userService.getMyUserPassword(userDto.getPassword());
        if (optionalUser.isPresent()) {
            // 비밀번호가 일치하는 경우
            return ResponseEntity.ok("비밀번호 일치");
        } else {
            // 비밀번호가 일치하지 않는 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
    }

    @PostMapping("/mypage/info/modify")
    public ResponseEntity<UserDto> modifyInfo(@RequestBody UserDto userDto) {
            userService.modifyUserInfo(userDto);
            return ResponseEntity.ok(userDto);
    }

    @PostMapping("/mypage/info/modify/password")
    public ResponseEntity<?> modifyPassword(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String newPasswordConfirm) {
        try {
            userService.modifyUserPassword(currentPassword, newPassword, newPasswordConfirm);
            return ResponseEntity.ok("비밀번호가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}