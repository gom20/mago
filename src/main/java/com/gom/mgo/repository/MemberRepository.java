package com.gom.mgo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gom.mgo.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String username);
}