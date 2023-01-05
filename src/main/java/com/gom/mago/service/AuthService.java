package com.gom.mago.service;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.dto.auth.CreateMemberDTO;
import com.gom.mago.dto.auth.LoginDTO;
import com.gom.mago.dto.auth.MemberDTO;
import com.gom.mago.dto.auth.SendPasswordDTO;
import com.gom.mago.dto.auth.TokenDTO;
import com.gom.mago.entity.Member;
import com.gom.mago.error.APIException;
import com.gom.mago.jwt.JwtTokenProvider;
import com.gom.mago.repository.AuthRepository;
import com.gom.mago.utils.MessageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final ModelMapper modelMapper;
	private final JavaMailSender mailSender;
	private final RedisTemplate<String, Object> redisTemplate;

	@Transactional
	public CreateMemberDTO.Response signup(CreateMemberDTO.Request request) {
		Member member = modelMapper.map(request, Member.class);
		member.setPassword(passwordEncoder.encode(request.getPassword()));

		// 가입 여부 확인
		if (authRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new APIException(ErrorCode.DUPLICATE_USER_ERROR);
		}

		return CreateMemberDTO.Response.fromEntity(authRepository.save(member));
	}

	@Transactional
	public LoginDTO.Response login(LoginDTO.Request request) {
		// 가입 여부 확인
		Member member = authRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new APIException(ErrorCode.LOGIN_FAIL_ERROR));

		// 비밀번호 확인 후 토큰 발급
		if (passwordEncoder.matches(request.getPassword(), member.getPassword())) {

			TokenDTO tokenDTO = jwtTokenProvider.generateToken(member.getUsername());
			redisTemplate.opsForValue().set("RT:" + member.getUsername(), tokenDTO.getRefreshToken(),
					jwtTokenProvider.getRemainedValidTime(tokenDTO.getRefreshToken()), TimeUnit.MILLISECONDS);

			return LoginDTO.Response.fromEntity(tokenDTO,
					MemberDTO.builder().email(member.getEmail()).name(member.getName()).build());
		} else {
			throw new APIException(ErrorCode.LOGIN_FAIL_ERROR);
		}

	}

	@Value("${spring.mail.username}")
	private String sender;

	@Transactional
	public SendPasswordDTO.Response resetAndSendPassword(SendPasswordDTO.Request request) {
		// 가입 여부 확인
		Member member = authRepository.findByEmailAndName(request.getEmail(), request.getName())
				.orElseThrow(() -> new APIException(ErrorCode.RESET_PW_FAIL_ERROR));

		// 임시 비밀번호 설정
		String tempPassword = RandomStringUtils.randomAlphanumeric(8);
		member.setPassword(passwordEncoder.encode(tempPassword));
		authRepository.save(member);

		// 임시 비밀번호 이메일 발송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(request.getEmail());
		message.setSubject(MessageUtil.getMessage("auth.passwordEmailTitle"));
		message.setText(MessageUtil.getMessage("auth.passwordEmailMessage", new Object[] { tempPassword }));
		message.setFrom(sender);
		message.setReplyTo(sender);
		mailSender.send(message);

		return SendPasswordDTO.Response.fromEntity(request.getEmail(), request.getName());
	}
	
	public void logout(TokenDTO request) {
		// 엑세스 토큰이 만료되었다면 에러 발생
		if (!jwtTokenProvider.validateToken(request.getAccessToken())) {
			throw new APIException(ErrorCode.ACCESS_CODE_NOT_VALID);
		}

		String userPk = jwtTokenProvider.getUserPk(request.getAccessToken());
		// 리프레쉬 토큰 삭제
		if (redisTemplate.opsForValue().get("RT:" + userPk) != null) {
			redisTemplate.delete("RT:" + userPk);
		}

		// 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장
		redisTemplate.opsForValue().set(request.getAccessToken(), "logout",
				jwtTokenProvider.getRemainedValidTime(request.getAccessToken()), TimeUnit.MILLISECONDS);
	}

	public TokenDTO refreshToken(TokenDTO request) {
		String refreshToken = request.getRefreshToken(), accessToken = request.getAccessToken();

		if (jwtTokenProvider.validateToken(accessToken)) {
			// Access Token 유효 && Refresh Token 만료: Refresh Token 발급
			String userPk = jwtTokenProvider.getUserPk(accessToken);

			if (!jwtTokenProvider.validateToken(refreshToken)) {
				refreshToken = jwtTokenProvider.generateRefreshToken(userPk);
				redisTemplate.opsForValue().set("RT:" + userPk, refreshToken,
						jwtTokenProvider.getRemainedValidTime(refreshToken), TimeUnit.MILLISECONDS);
			}
		} else {
			// Access Token 만료 && Refresh Token 만료: 에러 발생, 재로그인 필요
			if (!jwtTokenProvider.validateToken(refreshToken)) {
				throw new APIException(ErrorCode.REFRESH_TOKEN_EXPIRE);
			}

			// Access Token 만료 && Refresh Token 유효: Refresh 토큰 검증하여 Access Token 발급
			String userPk = jwtTokenProvider.getUserPk(refreshToken);

			String savedRefreshToken = (String) redisTemplate.opsForValue().get("RT:" + userPk);
			if (!savedRefreshToken.equals(request.getRefreshToken())) {
				throw new APIException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
			}
			accessToken = jwtTokenProvider.generateAccessToken(userPk);
		}

		// 유효한 Access Token, Refresh Token 발급
		return TokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

}
