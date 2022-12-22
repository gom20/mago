package com.gom.mago.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.dto.member.CreateMember;
import com.gom.mago.dto.member.Login;
import com.gom.mago.entity.Member;
import com.gom.mago.jwt.JwtTokenProvider;
import com.gom.mago.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService  {
 
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public CreateMember.Response createMember(CreateMember.Request request) {
    	Member member = modelMapper.map(request, Member.class);
    	member.setPassword(passwordEncoder.encode(request.getPassword()));
        return modelMapper.map(memberRepository.save(member), CreateMember.Response.class);
    } 
   
}