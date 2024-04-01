package com.proj.restreserve.jwt;

import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String useremail) {

        return userRepository.findOneWithRolesByUseremail(useremail)
                .map(user -> createUser(useremail, user))
                .orElseThrow(() -> new UsernameNotFoundException(useremail + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userEmail, User user) {
        if (user.getBan()) {
            throw new RuntimeException(userEmail + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getKey()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUseremail(),
                user.getPassword(),
                authorities
        );
    }
}