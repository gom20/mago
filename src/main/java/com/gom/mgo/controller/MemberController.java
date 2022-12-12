package com.gom.mgo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mgo.dto.APIResponse;
import com.gom.mgo.dto.CreateMember;
import com.gom.mgo.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

	private final MemberService memberService;
	
    @PostMapping("")
    public APIResponse createMember(@Valid @RequestBody final CreateMember.Request request){
        return APIResponse.of(memberService.createMember(request));
    }

//    @GetMapping("/{id}")
//    public APIResponse getContentDetail(@PathVariable final Long id){
//        return APIResponse.of(userService.getUserDetail(id));
//    }
//
//    @PutMapping("/{id}")
//    public APIResponse updateContent(@PathVariable final Long id, @Valid @RequestBody UpdateUser.Request request){
//        return APIResponse.of(userService.updateUser(id, request));
//    }
//
//    @DeleteMapping("/{id}")
//    public APIResponse deleteContent(@PathVariable final Long id){
//        return APIResponse.of(userService.deleteUser(id));
//    }
//	
}
