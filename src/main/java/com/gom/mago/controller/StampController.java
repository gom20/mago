package com.gom.mago.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.stamp.StampDTO;
import com.gom.mago.dto.stamp.UpdateStampDTO;
import com.gom.mago.service.StampService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/stamps")
public class StampController {

	private final StampService stampService;

	/**
	 * 스탬프 조회 API
	 * @param user 로그인 유저
	 * @return
	 */
	@GetMapping
	public APIResponse<List<StampDTO>> getStamps(@AuthenticationPrincipal User user) {
		log.info("getStamps");
		return APIResponse.of(stampService.getStamps(user.getUsername()));
	}
	
	/**
	 * 스탬프 업데이트 API
	 * @param request 스탬프 업데이트 정보: 스탬프 아이디, 업데이트 플래그
	 * @param user 로그인 유
	 * @return
	 */
    @PutMapping
    public APIResponse<UpdateStampDTO.Response> updateStamps(@Valid @RequestBody final UpdateStampDTO.Request request, @AuthenticationPrincipal User user){
    	log.info("updateStamps");
    	request.setEmail(user.getUsername());
        return APIResponse.of(stampService.updateStamps(request));
    }
}
