package com.gom.mgo.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mgo.dto.APIResponse;
import com.gom.mgo.dto.CreateMember;
import com.gom.mgo.service.MemberService;

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

	@GetMapping("/login")
	public String login() {
		log.info("login");
		return "login";
	}

	@PostMapping("/logout")
	public String logout() {
		log.info("logout");
		return "logout";
	}
}
