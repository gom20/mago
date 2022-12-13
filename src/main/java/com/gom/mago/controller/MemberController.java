package com.gom.mago.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.member.CreateMember;
import com.gom.mago.dto.member.Login;
import com.gom.mago.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

	private final MemberService memberService;

    @PostMapping("")
    public APIResponse<CreateMember.Response> createMember(@Valid @RequestBody final CreateMember.Request request){
    	log.info("createMember");
        return APIResponse.of(memberService.createMember(request));
    }

	@PostMapping("/login")
	public APIResponse<Login.Response> login(@RequestBody final Login.Request request) {
		log.info("login");
        log.info("user id = {}", request.getId());
        return APIResponse.of(memberService.login(request));
	}

	@PostMapping("/logout")
	public String logout() {
		log.info("logout");
		return "logout";
	}
}
