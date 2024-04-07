package com.proj.restreserve.user.service;

import com.proj.restreserve.businessnumber.dto.BusinessNumberDto;
import com.proj.restreserve.businessnumber.entity.BusinessNumber;
import com.proj.restreserve.businessnumber.repository.BusinessNumberRepository;
import com.proj.restreserve.detailpage.service.FileUpload;
import com.proj.restreserve.jwt.SecurityUtil;
import com.proj.restreserve.user.dto.UserDto;
import com.proj.restreserve.user.entity.Role;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusinessNumberRepository businessNumberRepository;
    private final FileUpload fileUpload;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Transactional
    public User signup(UserDto userDto) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUseremail(userDto.getUseremail()));
        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUseremail(userDto.getUseremail());
        user.setPhone(userDto.getPhone());
        user.setRole(Role.ROLE_USER);
        user.setBan(false);

        return userRepository.save(user);
    }

    @Transactional
    public User signupadmin(UserDto userDto, List<MultipartFile> files) {
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUseremail(userDto.getUseremail()));
        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // Create or Update BusinessNumber
        BusinessNumber businessNumber =new BusinessNumber();
        businessNumber.setBusinessid(userDto.getBusinessid());

        // 이미지 파일 저장
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename(); // UUID와 원본 파일 이름 사용

                    String imageUrl = fileUpload.uploadImageToS3(file, "business", fileName); // S3에 파일 업로드 후 URL 획득

                    // BusinessNumber에 이미지 링크 저장
                    businessNumber.setImagelink(imageUrl);
                }
            }
        }

        // BusinessNumber 저장
        businessNumber = businessNumberRepository.save(businessNumber);


        // Create User
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUseremail(userDto.getUseremail());
        user.setPhone(userDto.getPhone());
        user.setRole(Role.ROLE_USER);
        user.setBan(false);
        user.setBusinessNumber(businessNumber);

        return userRepository.save(user);


    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        Optional<String> currentUseremail = SecurityUtil.getCurrentUseremail();
        if (currentUseremail.isPresent()) {
            return Optional.ofNullable(userRepository.findByUseremail(currentUseremail.get()));
        } else {
            return Optional.empty();
        }
    }
    @Transactional
    public void logout(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            ((UsernamePasswordAuthenticationToken) authentication).setDetails(null); // 토큰의 만료 시간을 현재 시간으로 설정하여 토큰을 무효화
        }else {
            logger.info("토큰이 존재하지 않음. 로그인 되어 있지 않음.");
        }
    }


}