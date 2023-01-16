package com.gom.mago.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	public void sendEmail(SimpleMailMessage message) {
		message.setFrom(sender);
		message.setReplyTo(sender);
		mailSender.send(message);
	}
}
