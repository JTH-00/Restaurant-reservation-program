package com.proj.restreserve.user.controller;

import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupadmin(@Valid @RequestPart("userDto") UserDto userDto, @RequestPart(value = "files",required = false) List<MultipartFile> files) {
        userService.signupadmin(userDto,files);
        return ResponseEntity.ok().body("회원가입 완료");
    }
}
