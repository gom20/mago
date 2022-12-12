package com.gom.mgo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gom.mgo.dto.CreateMember;
import com.gom.mgo.entity.Member;
import com.gom.mgo.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
 
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    
 
//    @Transactional
//    public TokenInfo login(String memberId, String password) {
//        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
//        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
// 
//        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
//        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
// 
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
// 
//        return tokenInfo;
//    }
//    
//    
    
    @Transactional
    public CreateMember.Response createMember(CreateMember.Request request) {
        return modelMapper.map(
                memberRepository.save(modelMapper.map(request, Member.class)),
                CreateMember.Response.class);
    }
}