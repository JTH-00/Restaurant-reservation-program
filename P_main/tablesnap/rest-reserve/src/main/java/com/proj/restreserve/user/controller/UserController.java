package com.proj.restreserve.user.controller;

import com.proj.restreserve.jwt.JwtFilter;
import com.proj.restreserve.jwt.TokenDto;
import com.proj.restreserve.jwt.TokenProvider;
import com.proj.restreserve.report.dto.ReportRestaurantDto;
import com.proj.restreserve.report.dto.ReportReviewDto;
import com.proj.restreserve.report.entity.ReportRestaurant;
import com.proj.restreserve.report.entity.ReportReview;
import com.proj.restreserve.report.service.ReportService;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.Review;
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
@RequestMapping("/api/user")
@Tag(name= "User", description = "사용자 로그인 및 신고 API")
public class UserController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ReportService reportService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    @Operation(summary = "사용자 회원가입", description = "UserDto로 자신의 정보를 작성한뒤 저장하며,<br>" +
            "그 뒤 회원가입을 완료합니다.")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok().body("회원가입 완료");
    }

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "아이디와 비밀번호를 사용해 로그인합니다.")
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

            if (userRoles.contains(Role.ROLE_SUPERADMIN)) {
                redirectUri = "/api/superadmin/permitpage";
            } else if (userRoles.contains(Role.ROLE_USER)) {
                redirectUri = "/api/main";
            } else {
                redirectUri = "/api/user/login"; // Default redirect if role is not recognized
            }

            // Return token in response body
            return new ResponseEntity<>(new TokenDto(jwt,username,redirectUri), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user") //user 정보 출력
    @Operation(summary = "테스트용 토큰 출력", description = "삭제할 예정인 도메인입니다. 토큰 확인용으로 사용합니다.")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }


    @PostMapping("/logout")
    @Operation(summary = "사용자 로그아웃", description = "로그아웃을 합니다.")
    public ResponseEntity<String> logout(HttpServletRequest servletRequest){
        userService.logout();
        return ResponseEntity.ok().body("로그아웃");
    }

    @PostMapping(value = "/report/restaurant/{restaurantid}", consumes = {"multipart/form-data"})
    @Operation(summary = "매장 신고", description = "매장을 신고합니다.<br>" +
            "신고할 매장의 매장id를 파라미터로 받으며,<br>" +
            "ReportRestaurantDto를 통해 신고내용를 작성하고, 사진을 같이 올려 저장합니다.")
    public ResponseEntity<ReportRestaurant> reportrestaurant(
            @Valid @RequestPart("reportRestaurantDto")ReportRestaurantDto reportRestaurantDto,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @PathVariable("restaurantid") String restaurantid) {
        return ResponseEntity.ok(reportService.reportRestaurant(reportRestaurantDto, files,restaurantid));
    }
    @PostMapping(value = "/report/review/{reviewid}", consumes = {"multipart/form-data"})
    @Operation(summary = "리뷰 신고", description = "리뷰를 신고합니다.<br>" +
            "신고할 리뷰의 리뷰id를 파라미터로 받으며,<br>" +
            "ReportReviewDto를 통해 신고내용를 작성하고, 사진을 같이 올려 저장합니다.")
    public ResponseEntity<ReportReview> reportreview(
            @Valid @RequestPart("reportReviewDto") ReportReviewDto reportReviewDto,
            @PathVariable("reviewid") String reviewid,
            @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(reportService.reportReview(reportReviewDto, files,reviewid));
    }
}