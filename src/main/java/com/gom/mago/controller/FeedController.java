package com.gom.mago.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gom.mago.dto.APIResponse;
import com.gom.mago.dto.feed.CreateFeed;
import com.gom.mago.dto.feed.FeedDTO;
import com.gom.mago.service.FeedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/feeds")
public class FeedController {

	private final FeedService feedService;

    @PostMapping("")
    public APIResponse<CreateFeed.Response> createFeed(@Valid @RequestBody final CreateFeed.Request request){
    	log.info("createFeed");
        return APIResponse.of(feedService.createFeed(request));
    }
    
    @GetMapping("/{feedId}")
    public APIResponse<FeedDTO> getFeed(@Valid @RequestParam final String feedId){
    	log.info("getFeed");
        return APIResponse.of(feedService.getFeeds(feedId));
    }

    @GetMapping("")
    public APIResponse<List<FeedDTO>> getFeedList() {
    	log.info("getFeedList");
        return APIResponse.of(feedService.getFeeds());
    }
}
