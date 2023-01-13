package com.gom.mago.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.record.CreateRecordDTO;
import com.gom.mago.dto.record.DeleteRecordDTO;
import com.gom.mago.dto.record.RecordDTO;
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

    @GetMapping("all")
    public APIResponse<List<RecordDTO>> getAllRecords(@AuthenticationPrincipal User user) {
    	log.info("getAllRecords");
        return APIResponse.of(recordService.getAllRecords(user.getUsername()));
    }
    
    
    @GetMapping("")
    public APIResponse<Page<RecordDTO>> getRecords(@PageableDefault(size=10, sort="startDatetime", direction= Sort.Direction.DESC) Pageable pageable, 
    		@AuthenticationPrincipal User user) {
    	log.info("getRecords");
    	log.info("pageNumber: " +  pageable.getPageNumber());
        return APIResponse.of(recordService.getRecords(pageable, user.getUsername()));
    }
    
    
    
    @DeleteMapping("")
    public APIResponse<DeleteRecordDTO> deleteRecords(@Valid @RequestBody final DeleteRecordDTO request, @AuthenticationPrincipal User user) {
    	log.info("deleteRecords");
    	return APIResponse.of(recordService.deleteAllById(request, user.getUsername()));
    }
}
