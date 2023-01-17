package com.gom.mago.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.member.CreateMemberDTO;
import com.gom.mago.dto.member.DeleteMemberDTO;
import com.gom.mago.dto.member.SendTempPasswordDTO;
import com.gom.mago.dto.member.UpdatePasswordDTO;
import com.gom.mago.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

	private final MemberService memberService;

	/**
	 * 회원 가입 API
	 * @param request 회원 가입 정보: 이메일, 이름, 비밀번호, 비밀번호 확인
	 * @return 가입 완료 이메일 
	 */
    @PostMapping
    public APIResponse<CreateMemberDTO.Response> createMember(@Valid @RequestBody final CreateMemberDTO.Request request){
    	log.info("createMember");
        return APIResponse.of(memberService.createMember(request));
    }
	
    /**
     * 회원 탈퇴 API
     * @param request 탈퇴 요청 정보: 이메일, 비밀번호
     * @param user 로그인 유저
     * @return 탈퇴 완료 이메일
     */
	@DeleteMapping
	public APIResponse<DeleteMemberDTO.Response> deleteMember(
			@Valid @RequestBody final DeleteMemberDTO.Request request, 
			@AuthenticationPrincipal User user, 
			@RequestHeader(value="Authorization") String accessToken) {
		
		log.info("deleteMember");
		return APIResponse.of(memberService.deleteMember(request, user.getUsername(), accessToken));
	}

	/**
	 * 회원 비밀번호 변경 API
	 * @param request 비밀번호 변경 정보: 이메일, 기존 비밀 번호, 신규 비밀번호, 신규 비밀번호 확인
	 * @param user 로그인 유저
	 * @return
	 */
	@PutMapping("/password")
	public APIResponse<UpdatePasswordDTO.Response> updatePassword(@Valid @RequestBody final UpdatePasswordDTO.Request request, @AuthenticationPrincipal User user) {
		log.info("updatePassword");
		return APIResponse.of(memberService.updatePassword(request, user.getUsername()));
	}
	
	/**
	 * 임시 비밀번호 전송 API
	 * @param request 임시 비밀번호 요청 정보: 이메일, 이름
	 * @return
	 */
	@PostMapping("/sendTempPassword")
	public APIResponse<SendTempPasswordDTO.Response> sendTempPassword(@Valid @RequestBody final SendTempPasswordDTO.Request request) {
		log.info("sendTempPassword");
		return APIResponse.of(memberService.sendTempPassword(request));
	}
	
}
