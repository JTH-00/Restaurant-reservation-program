package com.proj.restreserve.user.controller;

import com.proj.restreserve.jwt.JwtFilter;
import com.proj.restreserve.jwt.TokenDto;
import com.proj.restreserve.jwt.TokenProvider;
import com.proj.restreserve.report.service.ReportService;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.user.service.UserService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/signup")
    public ResponseEntity<String> signupadmin(@Valid @RequestPart("userDto") UserDto userDto, @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        userService.signupadmin(userDto,files);
        return ResponseEntity.ok().body("회원가입 완료");
    }
    @PostMapping("/login")
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
            // Return token in response body
            return new ResponseEntity<>(new TokenDto(jwt,username), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest servletRequest){
        userService.logout();
        return ResponseEntity.ok().body("로그아웃");
    }
}