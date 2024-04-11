package com.proj.restreserve.user.controller;

import com.proj.restreserve.restaurant.dto.FavoritesDto;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.service.ReviewService;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.service.MyPageService;
import com.proj.restreserve.visit.dto.VisitDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MyPageController {

    private final MyPageService myPageService;

    private final ReviewService reviewService;

    @GetMapping("/mypage/use")
    public ResponseEntity<List<VisitDto>> visitRestaur(){

        List<VisitDto> visitDtos = myPageService.MyRegistInfo().stream()
                .filter(visitDto -> visitDto.getVisitcheck())
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/like")
    public ResponseEntity<List<FavoritesDto>> favoriterests(){
        List<FavoritesDto> favoritesDtos = myPageService.Myfavorites();
        return ResponseEntity.ok(favoritesDtos);
    }

    @GetMapping("/mypage/reserve")
    public ResponseEntity<List<VisitDto>> reserveRestaur(){

        List<VisitDto> visitDtos = myPageService.MyRegistInfo().stream()
                .filter(visitDto -> !visitDto.getVisitcheck())
                .collect(Collectors.toList());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/review")
    public ResponseEntity<List<ReviewDto>> reviewRestaur(){

        List<ReviewDto> reviewDtos = myPageService.MyReviewInfo();

        return ResponseEntity.ok(reviewDtos);
    }

    @PostMapping("/mypage/info")
    public ResponseEntity<String> entermodifyInfo(@RequestBody UserDto userDto) {
        Optional<User> optionalUser = myPageService.enterUserInfo(userDto.getPassword());
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
        myPageService.modifyUserInfo(userDto);
            return ResponseEntity.ok(userDto);
    }

    @PostMapping("/mypage/info/modify/password")
    public ResponseEntity<?> modifyPassword(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String newPasswordConfirm) {
        try {
            myPageService.modifyUserPassword(currentPassword, newPassword, newPasswordConfirm);
            return ResponseEntity.ok("비밀번호가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/mypage/use/write/review", consumes = {"multipart/form-data"})
     public ResponseEntity<Review> writeReviewRestaur(
            @Valid @RequestPart("reviewDto") ReviewDto reviewDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(reviewService.writeReview(reviewDto, files));
    }
    @PutMapping(value = "/mypage/use/modify/review", consumes = {"multipart/form-data"})
    public ResponseEntity<ReviewDto> modifyReviewRestaur(
            @RequestParam(name="reviewid") String reviewid,
            @Valid @RequestPart("reviewDto") ReviewDto reviewDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(reviewService.modifyReview(reviewid,reviewDto, files,deleteImageLinks));
    }
    @PostMapping(value = "/mypage/use/delete/review")
    public ResponseEntity<?> deleteReviewRestaur(@RequestParam(name="reviewid") String reviewid) {
        try {
            reviewService.deleteReview(reviewid);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
