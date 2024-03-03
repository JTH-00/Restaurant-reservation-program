package com.proj.restreserve.user;

import com.proj.restreserve.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
        user.setBan(true);

        return userRepository.save(user);
    }


    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String useremail) {
        return Optional.ofNullable(userRepository.findByUseremail(useremail));
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
}