package com.proj.restreserve.user.controller;

import com.proj.restreserve.detailpage.service.SelectReservation;
import com.proj.restreserve.restaurant.dto.FavoritesDto;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.dto.SelectReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.service.ReviewService;
import com.proj.restreserve.user.dto.PasswordDto;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.service.MyPageService;
import com.proj.restreserve.visit.dto.VisitDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@Tag(name= "MyPage", description = "사용자의 MyPage API")
public class MyPageController {

    private final MyPageService myPageService;

    private final ReviewService reviewService;
    private final SelectReservation selectReservation;

    @GetMapping("/mypage/use")
    @Operation(summary = "포장 예약 이용내역 조회", description = "자신의 포장예약 이용내역을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<VisitDto>> visitRestaur(@RequestParam(required = false, defaultValue = "1") int page){
        Page<VisitDto> visitDtos = (Page<VisitDto>) myPageService.MyRegistInfo(page,10)
                .filter(visitDto -> visitDto.getVisitcheck());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/like")
    @Operation(summary = "즐겨찾기한 매장 조회", description = "자신의 즐겨찾기한 매장을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<FavoritesDto>> favoriterests(@RequestParam(required = false, defaultValue = "1") int page){
        Page<FavoritesDto> favoritesDtos = myPageService.Myfavorites(page,10);
        return ResponseEntity.ok(favoritesDtos);
    }

    @GetMapping("/mypage/reserve")
    @Operation(summary = "방문 예약 이용내역 조회", description = "자신의 방문예약 이용내역을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<VisitDto>> useRestaur(@RequestParam(required = false, defaultValue = "1") int page){
        Page<VisitDto> visitDtos = (Page<VisitDto>) myPageService.MyRegistInfo(page,10)
                .filter(visitDto -> !visitDto.getVisitcheck());

        return ResponseEntity.ok(visitDtos);
    }

    @GetMapping("/mypage/review")
    @Operation(summary = "자신이 작성한 리뷰 조회", description = "자신의 리뷰 작성글을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<ReviewDto>> reviewRestaur(@RequestParam(required = false,defaultValue = "1") int page){

        Page<ReviewDto> reviewDtos = myPageService.MyReviewInfo(page,10);

        return ResponseEntity.ok(reviewDtos);
    }

    @PostMapping("/mypage/info")
    @Operation(summary = "개인정보 수정 비밀번호 확인", description = "개인정보 수정 전 비밀번호를 확인합니다.<br>" +
            "UserDto를 통해 비밀번호를 입력받습니다.")
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
    @Operation(summary = "개인정보 수정 ", description = "개인정보를 수정합니다.<br>" +
            "UserDto를 통해 개인정보를 입력받습니다.")
    public ResponseEntity<UserDto> modifyInfo(@RequestBody UserDto userDto) {
        myPageService.modifyUserInfo(userDto);
            return ResponseEntity.ok(userDto);
    }

    @PostMapping("/mypage/info/modify/password")
    @Operation(summary = "비밀번호 수정 ", description = "비밀번호를 수정합니다.<br>" +
            "PasswordDto 통해 비밀번호를 입력받습니다.")
    public ResponseEntity<?> modifyPassword(@Valid @RequestBody PasswordDto passwordDto) {
        try {
            myPageService.modifyUserPassword(passwordDto);
            return ResponseEntity.ok("비밀번호가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/mypage/write/review", consumes = {"multipart/form-data"})
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성합니다.<br>" +
            "이용내역에 따라 visitid또는 paymentid를 받습니다. 둘다 null이거나 둘다 있을수 없습니다.<br>" +
            "ReviewDto로 제목과 내용을 입력하고, 사진을 같이 올려 저장합니다.<br>"+
            "추가할 사진의 경우 없어도 무방합니다.")
     public ResponseEntity<SelectReviewDto> writeReviewRestaur(
            @Valid @RequestPart("reviewDto") ReviewDto reviewDto,
            @RequestParam(name="visitid", required = false) String visitid,
            @RequestParam(name="paymentid", required = false) String paymentid,
            @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(reviewService.writeReview(visitid,paymentid,reviewDto, files));
    }
    @PutMapping(value = "/mypage/modify/review", consumes = {"multipart/form-data"})
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.<br>" +
            "수정할 리뷰의 id를 파라미터로 받으며,<br>"+
            "ReviewDto로 제목과 내용을 수정하고,<br>"+
            "지울 파일의 경우 해당파일의 링크를 List로 저장하여 일련번호 추출 후 S3와 DB삭제 후 새로운 파일을 추가하여 수정합니다.<br>" +
            "추가할 사진의 경우 없어도 무방합니다.")
    public ResponseEntity<SelectReviewDto> modifyReviewRestaur(
            @RequestParam(name="reviewid") String reviewid,
            @Valid @RequestPart("reviewDto") ReviewDto reviewDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @RequestPart List<String> deleteImageLinks) {
        return ResponseEntity.ok(reviewService.modifyReview(reviewid,reviewDto, files,deleteImageLinks));
    }
    @PostMapping(value = "/mypage/delete/review")
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.<br>" +
            "파라미터로 삭제할 리뷰의 id값을 가져온 뒤 삭제합니다.<br>" +
            "삭제할 경우 해당 리뷰와 업로드된 사진들을 삭제합니다.")
    public ResponseEntity<?> deleteReviewRestaur(@RequestParam(name="reviewid") String reviewid) {
        try {
            reviewService.deleteReview(reviewid);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/mypage/reserve2")
    @Operation(summary = "현재 예약접수 조회", description = "자신의 접수된 예약(방문,포장)을 리스트로 조회합니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<Object>> showReservation(@RequestParam(required = false, defaultValue = "1") int page){
        return ResponseEntity.ok(selectReservation.showReservation(page,10));
    }
}
