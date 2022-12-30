package com.gom.mago.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mago.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	 Optional<Post> findByUid(String id);
	 List<Post> findByEmail(String email);
}
