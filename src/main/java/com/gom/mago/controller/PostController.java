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
        return APIResponse.of(postService.createFeed(request));
    }
    
    @GetMapping("/{id}")
    public APIResponse<PostDTO> getPost(@Valid @RequestParam final String id){
    	log.info("getPost");
        return APIResponse.of(postService.getPost(id));
    }

    @GetMapping("")
    public APIResponse<List<PostDTO>> getPosts() {
    	log.info("getPosts");
        return APIResponse.of(postService.getPosts());
    }
}
