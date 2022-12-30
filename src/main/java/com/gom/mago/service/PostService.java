package com.gom.mago.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.post.CreatePostDTO;
import com.gom.mago.dto.post.PostDTO;
import com.gom.mago.entity.Post;
import com.gom.mago.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final ModelMapper modelMapper;	

	@Transactional
	public CreatePostDTO.Response createFeed(CreatePostDTO.Request request) {
    	Post post = modelMapper.map(request, Post.class);
        return modelMapper.map(postRepository.save(post), CreatePostDTO.Response.class);
	}
	
	@Transactional
	public List<PostDTO> getPosts(String email) {
		List<Post> posts = postRepository.findByEmail(email);
		List<PostDTO> collect = posts.stream()
	            .map(p -> new PostDTO(p.getUid(), p.getEmail(), p.getContent()))
	            .collect(Collectors.toList());
		return collect;
	}


	
}