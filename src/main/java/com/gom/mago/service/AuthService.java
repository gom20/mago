package com.gom.mago.service;

import static com.gom.mago.constant.Constants.PREFIX_LOGOUT_A_TOKEN;
import static com.gom.mago.constant.Constants.PREFIX_R_TOKEN;
import static com.gom.mago.constant.Constants.PREFIX_V_EMAIL;
import static com.gom.mago.constant.Constants.PREFIX_V_TOKEN;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.dto.auth.LoginDTO;
import com.gom.mago.dto.auth.TokenDTO;
import com.gom.mago.dto.member.MemberDTO;
import com.gom.mago.entity.Member;
import com.gom.mago.error.APIException;
import com.gom.mago.jwt.JwtTokenProvider;
import com.gom.mago.repository.MemberRepository;
import com.gom.mago.utils.RedisUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisUtil redisUtil;
	
	/**
	 * 로그인 서비스
	 * @param request 로그인 정보: 이메일, 비밀번호
	 * @return 토큰 정보
	 */
	@Transactional
	public LoginDTO.Response login(LoginDTO.Request request) {
		Member member = authenticateMember(request.getEmail(), request.getPassword());

		TokenDTO tokenDTO = jwtTokenProvider.generateToken(member.getUsername());
		redisUtil.setDataExpire(PREFIX_R_TOKEN + member.getUsername(), tokenDTO.getRefreshToken(), jwtTokenProvider.getRemainedValidTime(tokenDTO.getRefreshToken()));

		return LoginDTO.Response.fromEntity(tokenDTO, MemberDTO.builder().email(member.getEmail()).name(member.getName()).build());
	}

	/**
	 * 로그아웃 서비스
	 * @param accessToken 엑세스 토큰
	 */
	public void logout(String accessToken) {
		if (!jwtTokenProvider.validateToken(accessToken)) return;
		
		String refreshTokenKey = PREFIX_R_TOKEN + jwtTokenProvider.getUserPk(accessToken);
		if(redisUtil.hasKey(refreshTokenKey)) {
			redisUtil.deleteData(refreshTokenKey);
		}
		redisUtil.setDataExpire(PREFIX_LOGOUT_A_TOKEN + accessToken, "LOGOUT", jwtTokenProvider.getRemainedValidTime(accessToken));
	}
	
	/**
	 * 토큰 재발급 서비스
	 * @param request 토큰 정보: 액세스 토큰, 리프레시 토큰
	 * @return
	 */
	public TokenDTO refreshToken(TokenDTO request) {
		String refreshToken = request.getRefreshToken(), accessToken = request.getAccessToken();

		if(redisUtil.hasKey(PREFIX_LOGOUT_A_TOKEN + accessToken)) {
			throw new APIException(ErrorCode.AUTH_ACCESS_TOKEN_NOT_VALID);
		}

		if (jwtTokenProvider.validateToken(accessToken)) {
			// Access Token 유효 && Refresh Token 만료: Refresh Token 발급
			String userPk = jwtTokenProvider.getUserPk(accessToken);

			if (!jwtTokenProvider.validateToken(refreshToken)) {
				refreshToken = jwtTokenProvider.generateRefreshToken(userPk);
				redisUtil.setDataExpire(PREFIX_R_TOKEN + userPk, refreshToken, jwtTokenProvider.getRemainedValidTime(refreshToken));
			}
		} else {
			// Access Token 만료 && Refresh Token 만료: 에러 발생, 재로그인 필요
			if (!jwtTokenProvider.validateToken(refreshToken)) {
				throw new APIException(ErrorCode.AUTH_ACCESS_AND_REFRESH_TOKEN_EXPIRE);
			}

			// Access Token 만료 && Refresh Token 유효: 저장된 Refresh 토큰 검증하여 Access Token 발급
			String userPk = jwtTokenProvider.getUserPk(refreshToken);
			
			String savedRefreshToken = redisUtil.getData(PREFIX_R_TOKEN + userPk);
			if (savedRefreshToken == null || !savedRefreshToken.equals(request.getRefreshToken())) {
				// null 케이스: 로그아웃, 탈퇴 회원 리프레쉬 토큰으로 엑세스 토큰 발급 방지
				// not match 케이스: 리프레쉬 토큰 저장된 값만 허용
				throw new APIException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_VALID);
			}
			accessToken = jwtTokenProvider.generateAccessToken(userPk);
		}

		// 유효한 Access Token, Refresh Token 발급
		return TokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}
	
	/**
	 * 본인 인증 이메일 전송 서비스
	 * @param email 이메일
	 */
	public void sendVerificationEmail(String email) {
		String uuid = UUID.randomUUID().toString();
		redisUtil.setDataExpire(PREFIX_V_TOKEN + uuid, email, 5 * 60 * 1000L);
		emailService.sendVerifyEmail(email, uuid.toString());
	}

	/**
	 * 본인 인증 서비스
	 * @param token 인증키
	 * @return
	 */
	public Boolean verifyEmail(String token) {
		if(!redisUtil.hasKey(PREFIX_V_TOKEN + token)) {
			return false;
		}
		
		String email = redisUtil.getData(PREFIX_V_TOKEN + token);
		redisUtil.deleteData(PREFIX_V_TOKEN + token);
		redisUtil.setDataExpire(PREFIX_V_EMAIL + email, "VERIFIED", 180 * 60 * 1000L);
		return true;
	}
	
	/**
	 * 이메일 본인 인증 완료 여부 조회 서비스
	 * @param email 이메일
	 * @return
	 */
	public Boolean isVerifiedEmail(String email) {
		return redisUtil.hasKey(PREFIX_V_EMAIL + email);
	}

	/**
	 * 회원 인증 서비스
	 * @param email 이메일
	 * @param password 비밀번호
	 * @return 회원 정보
	 */
	public Member authenticateMember(String email, String password) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new APIException(ErrorCode.AUTH_AUTHENTICATE_MEMBER_FAIL));
		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new APIException(ErrorCode.AUTH_AUTHENTICATE_MEMBER_FAIL);
		}
		return member;
	}
	
	/**
	 * 본인 인증 정보 삭제 서비스
	 * @param email 이메일
	 */
	public void deleteVerifiedEmail(String email) {
		if(redisUtil.hasKey(PREFIX_V_EMAIL + email)) {
			redisUtil.deleteData(PREFIX_V_EMAIL + email);
		}
	}

}
