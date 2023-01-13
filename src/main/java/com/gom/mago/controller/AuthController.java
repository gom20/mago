package com.gom.mago.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.auth.ChangePasswordDTO;
import com.gom.mago.dto.auth.CreateMemberDTO;
import com.gom.mago.dto.auth.LoginDTO;
import com.gom.mago.dto.auth.SendPasswordDTO;
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

    @PostMapping("/signup")
    public APIResponse<CreateMemberDTO.Response> signup(@Valid @RequestBody final CreateMemberDTO.Request request){
    	log.info("signup");
        return APIResponse.of(authService.signup(request));
    }
    
	@PostMapping("/login")
	public APIResponse<LoginDTO.Response> login(@RequestBody final LoginDTO.Request request) {
		log.info("login");
        log.info("email = {}", request.getEmail());
        return APIResponse.of(authService.login(request));
	}

	@PostMapping("/refresh")
	public APIResponse<TokenDTO> refreshToken(@Valid @RequestBody final TokenDTO request) {
		log.info("refresh");
		return APIResponse.of(authService.refreshToken(request));
	}
	
	@PostMapping("/logout")
	public APIResponse logout(@RequestBody final TokenDTO request) {
		log.info("logout");
		authService.logout(request);
        return new APIResponse();
	}
	
	
	@PostMapping("/withdraw")
	public APIResponse withdraw(@AuthenticationPrincipal User user) {
		log.info("withdraw");
		authService.withdraw(user.getUsername());
        return new APIResponse();
	}
	
	@PostMapping("/sendPassword")
	public APIResponse<SendPasswordDTO.Response> sendPassword(@Valid @RequestBody final SendPasswordDTO.Request request) {
		log.info("sendPassword");
		return APIResponse.of(authService.resetAndSendPassword(request));
	}
	
	@PostMapping("/changePassword")
	public APIResponse<ChangePasswordDTO.Response> changePassword(@Valid @RequestBody final ChangePasswordDTO.Request request, @AuthenticationPrincipal User user) {
		log.info("updatePassword");
		return APIResponse.of(authService.updatePassword(request, user.getUsername()));
	}
}
