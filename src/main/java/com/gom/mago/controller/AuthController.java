package com.gom.mago.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.auth.SendVerificationEmailDTO;
import com.gom.mago.dto.auth.LoginDTO;
import com.gom.mago.dto.auth.LogoutDTO;
import com.gom.mago.dto.auth.TokenDTO;
import com.gom.mago.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

	private final AuthService authService;
	
	/**
	 * 로그인 API
	 * @param request 로그인 정보: 이메일, 비밀번호
	 * @return 사용자 정보, 엑세스 토큰, 리프레쉬 토큰
	 */
	@PostMapping("/login")
	public APIResponse<LoginDTO.Response> login(@Valid @RequestBody final LoginDTO.Request request) {
		log.info("login");
        log.info("email = {}", request.getEmail());
        return APIResponse.of(authService.login(request));
	}

	/**
	 * 로그아웃 API
	 * @param request 로그아웃 토큰 정보: 액세스 토큰
	 * @return
	 */
    @PostMapping("/logout")
	public APIResponse<Object> logout(@Valid @RequestBody final LogoutDTO request) {
		log.info("logout");
		authService.logout(request.getAccessToken());
        return APIResponse.of(null);
	}
    
    /**
     * 리프레쉬 토큰 API
     * @param request 리프레쉬 토큰 정보: 엑세스 토큰, 리프레쉬 토큰
     * @return
     */
	@PostMapping("/refresh")
	public APIResponse<TokenDTO> refreshToken(@Valid @RequestBody final TokenDTO request) {
		log.info("refresh");
		return APIResponse.of(authService.refreshToken(request));
	}
	
	/**
	 * 본인 인증 이메일 전송 API
	 * @param request 이메일
	 * @return
	 */
	@PostMapping("/sendVerificationEmail")
	public APIResponse<String> sendVerificationEmail(@Valid @RequestBody final SendVerificationEmailDTO request) {
		log.info("sendVerificationEmail");
		authService.sendVerificationEmail(request.getEmail());
		return APIResponse.of(request.getEmail());
	}
	
	/**
	 * 본인 인증 API
	 * @param token 인증 토큰
	 * @return
	 */
	@GetMapping("/verifyEmail")
	public APIResponse<Boolean> verifyEmail(@Valid @RequestParam String token) {
		log.info("verifyEmail");
		return APIResponse.of(authService.verifyEmail(token));
	}
	
	/**
	 * 본인 인증 완료 여부 API
	 * @param email 이메일
	 * @return
	 */
	@GetMapping("/isVerifiedEmail")
	public APIResponse<Boolean> isVerifiedEmail(@Valid @RequestParam String email) {
		log.info("isVerifiedEmail");
		return APIResponse.of(authService.isVerifiedEmail(email));
	}

}
