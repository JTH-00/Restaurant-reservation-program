package com.proj.restreserve.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
//JWT(JSON Web Tokens)를 생성, 인증 및 검증
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class); //Logger 객체를 사용하여 로그를 기록
    private static final String AUTHORITIES_KEY = "auth"; //JWT의 권한을 나타내는 키
    private final String secret; //토큰 서명 시 사용되는 키
    private final long tokenValidityInMilliseconds; //토큰의 유효 시간을 밀리초 단위로 나타냄
    private Key key; //비밀 키

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // Base64로 인코딩된 시크릿 키를 디코딩
        Key key = Keys.hmacShaKeyFor(keyBytes); // HMAC SHA 키 생성

        // 키가 512비트 미만인 경우, 새로운 시크릿 키 생성
        if (keyBytes.length < 64) {
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            logger.warn("Provided secret key is less than 512 bits. Generating a new secure key.");
        } else {
            this.key = key;
        }
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream() //사용자의 권한 정보를 스트림으로 받아오기
                .map(GrantedAuthority::getAuthority) //map을 사용하여 각 권한 객체(GrantedAuthority)의 권한 이름을 가져오기
                .collect(Collectors.joining(",")); //권한 이름들을 쉼표로 구분된 하나의 문자열로 합치기

        long now = (new Date()).getTime(); //현재 시간을 밀리초 단위로 받아오기
        Date validity = new Date(now + this.tokenValidityInMilliseconds); //현재 시간에 토큰의 유효 시간을 더한 시간을 만료 시간

        return Jwts.builder() //JWT를 생성
                .setSubject(authentication.getName()) //토큰의 주체를 설정
                .claim(AUTHORITIES_KEY, authorities) //권한 정보를 토큰에 저장
                .signWith(key, SignatureAlgorithm.HS512) //HMAC SHA512 알고리즘과 비밀 키를 이용하여 토큰을 서명
                .setExpiration(validity) //토큰의 만료 시간을 설정
                .compact(); //토큰을 문자열 형태로 반환
    }


    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();  //JWT 토큰으로부터 클레임(JWT 토큰의 페이로드에 담긴 정보 == 토큰의 주체, 발급자, 수신자 등)을 추출

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()); // 클레임으로부터 권한 정보를 추출하여 GrantedAuthority 객체의 컬렉션으로 변환

        User principal = new User(claims.getSubject(), "", authorities); //User 객체를 생성, claims.getSubject()는 토큰의 주체(사용자 이메일)를 반환

        return new UsernamePasswordAuthenticationToken(principal, token, authorities); //Authentication 객체(Spring Security에서 사용자의 인증 정보) 반환
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //JWT 토큰을 파싱하고 검사,토큰의 서명이 유효,만료되지 않았으며, 지원되는 형식의 토큰 시 파싱 완료
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) { //토큰의 서명이 잘못된 경우
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) { //토큰이 만료된 경우
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) { //지원되지 않는 형식의 토큰
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) { //null 이거나 비어있는 토큰
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
