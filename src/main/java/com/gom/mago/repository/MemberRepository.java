package com.gom.mago.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mago.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);
    
    
}