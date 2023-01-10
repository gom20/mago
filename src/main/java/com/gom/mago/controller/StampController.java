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

	private final StampService mountainService;

	@GetMapping("")
	public APIResponse<List<StampDTO>> getMountainStamps(@AuthenticationPrincipal User user) {
		log.info("getMountainStamps");
		return APIResponse.of(mountainService.getMountainStamps(user.getUsername()));
	}
	
    @PutMapping("")
    public APIResponse<UpdateStampDTO.Response> updateMountainStamp(@Valid @RequestBody final UpdateStampDTO.Request request, @AuthenticationPrincipal User user){
    	log.info("updateMountainStamp");
    	request.setEmail(user.getUsername());
        return APIResponse.of(mountainService.updateMountainStamp(request));
    }
}
