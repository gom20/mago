package com.gom.mago.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.error.APIException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	
	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * Simple 메일 서비스
	 * @param message 메일 제목, 수신인, 메일 내용
	 */
	public void sendEmail(SimpleMailMessage message) {
		message.setFrom(sender);
		message.setReplyTo(sender);
		mailSender.send(message);
	}
	
	/**
	 * thymeleaf 템플릿 이용한 메일 서비스
	 * @param message 메일 제목, 수신인
	 * @param template 메일 템플릿
	 * @param variables 템플릿 적용 변수
	 */
	public void sendEmail(SimpleMailMessage message, String template, Map<String, Object> variables) {
        Context context = new Context();
        variables.forEach((key, value)->{
	        context.setVariable(key, value);
	    });
        String html = templateEngine.process(template, context);
        
        try {
        	MimeMessage mimeMessage = mailSender.createMimeMessage();
    	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");	  
    	    mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setSubject(message.getSubject());
    	    mimeMessageHelper.setTo(message.getTo());
    	    mimeMessageHelper.setText(html, true);
    	    mailSender.send(mimeMessage);
        } catch(MessagingException e) {
        	throw new APIException(ErrorCode.EMAIL_SEND_EMAIL_FAIL);
        }
	}
	
	/**
	 * 본인 인증 메일 서비스
	 * @param to
	 * @param token
	 */
	public void sendVerifyEmail(String to, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("마운틴고 회원 가입 본인 인증");
		String verifyEmailUrl = "http://localhost:8080/api/auth/verifyEmail?token=" + token;
		sendEmail(message, "verify_email", new HashMap<String, Object>(){{put("verifyEmailUrl", verifyEmailUrl);}});
	}
	
	/**
	 * 임시 비밀번호 발급 메일 서비스
	 * @param to
	 * @param token
	 */
	public void sendTempPasswordEmail(String to, String tempPassword) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("마운틴고 임시 비밀번호 발급");
		sendEmail(message, "temp_password_email", new HashMap<String, Object>(){{put("tempPassword", tempPassword);}});
	}
    
}



