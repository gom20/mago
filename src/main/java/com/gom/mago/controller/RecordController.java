package com.gom.mago.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.post.CreateRecordDTO;
import com.gom.mago.dto.post.RecordDTO;
import com.gom.mago.service.RecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/records")
public class RecordController {
	
	private final RecordService recordService;

    @PostMapping("")
    public APIResponse<CreateRecordDTO.Response> createRecord(@Valid @RequestBody final CreateRecordDTO.Request request){
    	log.info("createRecord");
        return APIResponse.of(recordService.createRecord(request));
    }

    @GetMapping("")
    public APIResponse<List<RecordDTO>> getRecords(@AuthenticationPrincipal User user) {
    	log.info("getRecords");
        return APIResponse.of(recordService.getPosts(user.getUsername()));
    }
}
