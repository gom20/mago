package com.gom.mago.jwt;

import static com.gom.mago.constant.Constants.PREFIX_LOGOUT_A_TOKEN;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gom.mago.utils.RedisUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
//    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisUtil redisUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
     // Redis에 해당 accessToken 로그아웃 여부 확인하여, 로그아웃이 안된 토큰일 경우에만 인증 절차 진행
        if(accessToken != null && !redisUtil.hasKey(PREFIX_LOGOUT_A_TOKEN + accessToken)) {          
        	// 유효한 토큰인지 확인
            if (jwtTokenProvider.validateToken(accessToken)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보 조회
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                // SecurityContext 에 Authentication 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    } 
}