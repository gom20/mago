package com.gom.mago.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.gom.mago.dto.auth.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String secretKey;
	private Key key;
	
	// 테스트 시간
	private long accessTokenValidTime = 1 * 60 * 60 * 1000L; 
	private long refreshTokenValidTime = 672 * 60 * 60 * 1000L; 

	private final UserDetailsService userDetailsService;
	
	// 객체 초기화, secretKey를 Base64로 인코딩한다.
	@PostConstruct
	protected void init() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	// 토큰 생성
	public TokenDTO generateToken(String userPk) {
		Claims claims = Jwts.claims().setSubject(userPk);
		Date now = new Date();
		
		String accessToken = Jwts.builder().setClaims(claims)
		.setIssuedAt(now) 
		.setExpiration(new Date(now.getTime() + accessTokenValidTime)) 
		.signWith(key, SignatureAlgorithm.HS256) 
		.compact();
		
		String refreshToken = Jwts.builder().setClaims(claims)
		.setIssuedAt(now) 
		.setExpiration(new Date(now.getTime() + refreshTokenValidTime)) 
		.signWith(key, SignatureAlgorithm.HS256) 
		.compact();
		
		return new TokenDTO(accessToken, refreshToken);
	}
	
	// 토큰 생성
	public String generateToken(String userPk, long validTime) {
		Claims claims = Jwts.claims().setSubject(userPk);
		Date now = new Date();
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(now) 
				.setExpiration(new Date(now.getTime() + validTime)) 
				.signWith(key, SignatureAlgorithm.HS256) 
				.compact();
	}

	// 토큰 생성
	public String generateAccessToken(String userPk) {
		return generateToken(userPk, accessTokenValidTime);
	}

	// 토큰 생성
	public String generateRefreshToken(String userPk) {
		return generateToken(userPk, refreshTokenValidTime);
	}
	
	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	
	// 만료일자 조회 
	public Date getExpiration(String jwtToken) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody().getExpiration();
	}
	
	// 남은 유효시간 조회
	public Long getRemainedValidTime(String token) {
	     // accessToken 남은 유효시간
	     Date expiration = getExpiration(token);
	     // 현재 시간
	     Long now = new Date().getTime();
	     return (expiration.getTime() - now);
	}
	
	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
	
}