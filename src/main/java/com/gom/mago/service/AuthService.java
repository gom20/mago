package com.gom.mago.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.dto.auth.CreateMember;
import com.gom.mago.dto.auth.Login;
import com.gom.mago.entity.Member;
import com.gom.mago.error.APIException;
import com.gom.mago.jwt.JwtTokenProvider;
import com.gom.mago.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @Transactional
    public CreateMember.Response signup(CreateMember.Request request) {
    	Member member = modelMapper.map(request, Member.class);
    	member.setPassword(passwordEncoder.encode(request.getPassword()));
        return modelMapper.map(authRepository.save(member), CreateMember.Response.class);
    } 
    
    @Transactional
    public Login.Response login(Login.Request request) {
    	// 1. 가입 여부 확인
    	Member member = authRepository.findByEmail(request.getEmail())
    		  .orElseThrow(() -> new APIException(ErrorCode.LOGIN_FAIL_ERROR));
    	
    	// 2. 비밀번호 확인
    	if(passwordEncoder.matches(request.getPassword(), member.getPassword())) {
    		return Login.Response.fromEntity(jwtTokenProvider.createToken(member.getUsername()));
    	} else {
    		throw new APIException(ErrorCode.LOGIN_FAIL_ERROR);
    	}
    	
    }
}
