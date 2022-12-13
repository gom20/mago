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
    private final JwtTokenProvider jwtTokenProvider;

   
    @Transactional
    public CreateMember.Response createMember(CreateMember.Request request) {
    	Member member = modelMapper.map(request, Member.class);
    	member.setPassword(passwordEncoder.encode(request.getPassword()));
        return modelMapper.map(memberRepository.save(member), CreateMember.Response.class);
    } 
   
    @Transactional
    public Login.Response login(Login.Request request) {
    	// 1. 가입 여부 확인
    	Member member = memberRepository.findById(request.getId())
    		  .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
      
    	// 2. 비밀번호 확인
    	if(passwordEncoder.matches(request.getPassword(), member.getPassword())) {
    		return Login.Response.fromEntity(jwtTokenProvider.createToken(member.getUsername()));
    	} else {
    		throw new IllegalArgumentException("잘못된 패스워드 입니다.");
    	}
    	
    }
}