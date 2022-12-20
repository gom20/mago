package com.gom.mago.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mago.entity.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>{
	 Optional<Feed> findById(String id);
}
