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
import com.gom.mago.dto.post.CreatePostDTO;
import com.gom.mago.dto.post.PostDTO;
import com.gom.mago.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {

	private final PostService postService;

    @PostMapping("")
    public APIResponse<CreatePostDTO.Response> createPost(@Valid @RequestBody final CreatePostDTO.Request request){
    	log.info("createPost");
        return APIResponse.of(postService.createPost(request));
    }

    @GetMapping("")
    public APIResponse<List<PostDTO>> getPosts(@AuthenticationPrincipal User user) {
    	log.info("getPosts");
        return APIResponse.of(postService.getPosts(user.getUsername()));
    }
}
