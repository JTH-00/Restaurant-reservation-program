package com.proj.restreserve.config;

import com.proj.restreserve.jwt.TokenProvider;
import com.proj.restreserve.jwt.point.JwtAccessDeniedHandler;
import com.proj.restreserve.jwt.point.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)  //csrf 차단 == token 사용

                .exceptionHandling((exceptionHandling) -> //컨트롤러의 예외처리
                        exceptionHandling
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .authorizeHttpRequests(request -> request // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                        .requestMatchers("/api/user/signup").permitAll() //사용자 전체 혀용
                        .requestMatchers("/api/user/login").permitAll() //사용자 전체 혀용
                        .requestMatchers("/api/user/{useremail}").hasAuthority("ROLE_ADMIN") //업주만 허용
                        .requestMatchers("api/user/rest/{restaurantid}").permitAll()//레스토랑
                        .anyRequest().authenticated() // 그 외 인증 없이 접근X
                )
                .with(new JwtSecurityConfig(tokenProvider), customizer -> {}); //filterChain 등록


        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //스프링 시큐리티 인증 UserSecurityService와 PasswordEncoder가 자동으로 설정
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}