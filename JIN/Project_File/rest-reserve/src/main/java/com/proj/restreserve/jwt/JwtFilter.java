package com.proj.restreserve.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization"; //토큰 정보를 담고 있는 HTTP 요청의 헤더 이름
    private final TokenProvider tokenProvider;

    // 실제 필터릴 로직
    // 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest; //ServletRequest를 HttpServletRequest로 형변환하여 HTTP 요청에 특화된 메서드를 사용
        String jwt = resolveToken(httpServletRequest); //HTTP 요청 헤더에서 토큰을 추출
        String requestURI = httpServletRequest.getRequestURI(); //요청 URI를 가져오기

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { //토큰이 있는지와 유효한지를 검사
            Authentication authentication = tokenProvider.getAuthentication(jwt); //토큰의 인증 정보를 가져오기
            SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContext 인증 정보 저장
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);  //인증 정보가 저장되었음을 로그에 남깁
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse); //요청과 응답을 다음 필터로 전달,이 필터가 마지막 필터라면, 요청은 해당 요청을 처리하는 서블릿이나 컨트롤러로 전달
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); //HTTP 요청 헤더에서 "Authorization" 키에 해당하는 값을 가져와 bearerToken에 저장,JWT 토큰은 "Authorization" 헤더에 "Bearer {토큰}" 형식으로 저장

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { //토큰이 있는지와 "Bearer "로 시작하는지를 검사
            return bearerToken.substring(7); //다음에 오는 실제 토큰을 반환
        }
        return null;
    }
}