package com.proj.restreserve.user.controller;

import com.proj.restreserve.jwt.JwtFilter;
import com.proj.restreserve.jwt.TokenDto;
import com.proj.restreserve.jwt.TokenProvider;
import com.proj.restreserve.report.service.ReportService;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.ReviewReply;
import com.proj.restreserve.review.service.ReviewService;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.Role;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name= "Admin", description = "업주 로그인 및 리뷰답글 API")
public class AdminController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    @Operation(summary = "업주 회원가입", description = "UserDto로 자신의 정보를 작성한뒤 저장하며,<br>" +
            "사진파일들을 통해 사업자등록번호를 증빙할 서류를 확인하게합니다.<br>" +
            "그 뒤 회원가입을 완료합니다.")
    public ResponseEntity<String> signupadmin(@Valid @RequestPart("userDto") UserDto userDto, @RequestPart(value = "files") List<MultipartFile> files) {
        userService.signupadmin(userDto,files);
        return ResponseEntity.ok().body("회원가입 완료");
    }
    @PostMapping("/login")
    @Operation(summary = "업주 로그인", description = "아이디와 비밀번호를 사용해 로그인합니다.")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserDto userDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUseremail(), userDto.getPassword()); //인증하여 authenticationToken에 저장

            // Authenticate user
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //인증 정보를 authentication에 저장

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token
            String jwt = tokenProvider.createToken(authentication);

            // Add token to response header
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            User user = userRepository.findByUseremail(userDto.getUseremail());
            String username = user.getUsername();

            // Set redirect URI based on user role
            String redirectUri;
            Set<Role> userRoles = user.getRoles(); // 사용자의 역할 집합을 가져옴

            if (userRoles.contains(Role.ROLE_ADMIN)) {
                redirectUri = "/api/admin/registration";
            } else {
                redirectUri = "/api/admin/login"; // Default redirect if role is not recognized
            }

            // Return token in response body
            return new ResponseEntity<>(new TokenDto(jwt,username,redirectUri), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/logout")
    @Operation(summary = "업주 로그아웃", description = "로그아웃을 합니다.")
    public ResponseEntity<String> logout(HttpServletRequest servletRequest){
        userService.logout();
        return ResponseEntity.ok().body("로그아웃");
    }
    @PostMapping(value = "/write/reply")
    @Operation(summary = "업주 리뷰 답글", description = "리뷰 답글을 작성합니다.<br>" +
            "답글을 작성할 리뷰의 id를 파라미터로 받으며,<br>" +
            "ReviewDto를 통해 리뷰를 작성합니다.")
    public ResponseEntity<ReviewReply> writeReviewReply(
            @RequestParam(name="reviewid") String reviewid,
            @Valid @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.writeReply(reviewid,reviewDto));
    }
    @PutMapping (value = "/modify/reply")
    @Operation(summary = "업주 리뷰 답글", description = "리뷰 답글을 수정합니다.<br>" +
            "답글의 id를 파라미터로 받으며,<br>" +
            "ReviewDto를 통해 리뷰를 수정합니다.")
    public ResponseEntity<ReviewReply> modifyReviewReply(
            @RequestParam(name="replyid") String replyid,
            @Valid @RequestBody ReviewDto reviewDto){
        return ResponseEntity.ok(reviewService.modifyReply(replyid,reviewDto));
    }
    @DeleteMapping(value = "/reply")
    @Operation(summary = "업주 리뷰 답글", description = "리뷰 답글을 삭제합니다.<br>" +
            "답글의 id를 파라미터로 받습니다")
    public ResponseEntity<String> deleteReviewReply(@RequestParam(name="replyid") String replyid) {
        try {
            reviewService.deleteReply(replyid);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}