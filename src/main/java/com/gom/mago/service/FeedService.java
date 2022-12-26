package com.gom.mago.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.feed.CreateFeed;
import com.gom.mago.dto.feed.FeedDTO;
import com.gom.mago.entity.Feed;
import com.gom.mago.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final ModelMapper modelMapper;	

	@Transactional
	public CreateFeed.Response createFeed(CreateFeed.Request request) {
    	Feed feed = modelMapper.map(request, Feed.class);
        return modelMapper.map(feedRepository.save(feed), CreateFeed.Response.class);
	}
	
	@Transactional
	public FeedDTO getFeeds(String feedId) {
		return modelMapper.map(feedRepository.findById(feedId), FeedDTO.class);
	}
	
	@Transactional
	public List<FeedDTO> getFeeds() {
		List<Feed> feeds = feedRepository.findAll();
		List<FeedDTO> collect = feeds.stream()
	            .map(f-> new FeedDTO(f.getId(), f.getName(), f.getFeed()))
	            .collect(Collectors.toList());
		return collect;
	}


	
}