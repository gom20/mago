package com.gom.mago.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.dto.auth.CreateMember;
import com.gom.mago.dto.auth.Login;
import com.gom.mago.dto.auth.SendPassword;
import com.gom.mago.entity.Member;
import com.gom.mago.error.APIException;
import com.gom.mago.jwt.JwtTokenProvider;
import com.gom.mago.repository.AuthRepository;
import com.gom.mago.utils.MessageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;
    private final JavaMailSender mailSender;

    @Transactional
    public CreateMember.Response signup(CreateMember.Request request) {
    	Member member = modelMapper.map(request, Member.class);
    	member.setPassword(passwordEncoder.encode(request.getPassword()));
    	
    	// 가입 여부 확인
    	if(authRepository.findByEmail(request.getEmail()).isPresent()) {
    		throw new APIException(ErrorCode.DUPLICATE_USER_ERROR);
    	}
   
        return CreateMember.Response.fromEntity(authRepository.save(member));
    } 
    
    @Transactional
    public Login.Response login(Login.Request request) {
    	// 가입 여부 확인
    	Member member = authRepository.findByEmail(request.getEmail())
    		  .orElseThrow(() -> new APIException(ErrorCode.LOGIN_FAIL_ERROR));
    	
    	// 비밀번호 확인
    	if(passwordEncoder.matches(request.getPassword(), member.getPassword())) {
    		return Login.Response.fromEntity(jwtTokenProvider.createToken(member.getUsername()));
    	} else {
    		throw new APIException(ErrorCode.LOGIN_FAIL_ERROR);
    	}
    	
    }
    
    @Value("${spring.mail.username}")
    private String sender;
    
    @Transactional
    public SendPassword.Response resetAndSendPassword(SendPassword.Request request) {
    	// 가입 여부 확인
    	Member member = authRepository.findByEmailAndName(request.getEmail(), request.getName())
    			.orElseThrow(() -> new APIException(ErrorCode.RESET_PW_FAIL_ERROR));
    	
    	// 임시 비밀번호 설정
    	String tempPassword = RandomStringUtils.randomAlphanumeric(8);
    	member.setPassword(passwordEncoder.encode(tempPassword));
    	authRepository.save(member);
    	
		// 임시 비밀번호 이메일 발송      
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject(MessageUtil.getMessage("auth.passwordEmailTitle"));
        message.setText(MessageUtil.getMessage("auth.passwordEmailMessage", new Object[] { tempPassword }));
        message.setFrom(sender);
        message.setReplyTo(sender);
        mailSender.send(message);

        return SendPassword.Response.fromEntity(request.getEmail(), request.getName());
    }
    
}
