package com.gom.mago.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gom.mago.constant.ErrorCode;
import com.gom.mago.dto.member.CreateMemberDTO;
import com.gom.mago.dto.member.DeleteMemberDTO;
import com.gom.mago.dto.member.SendTempPasswordDTO;
import com.gom.mago.dto.member.UpdatePasswordDTO;
import com.gom.mago.entity.Member;
import com.gom.mago.error.APIException;
import com.gom.mago.repository.MemberRepository;
import com.gom.mago.utils.MessageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	private final RecordService recordService;
	private final StampService stampService;
	private final AuthService authService;
	private final EmailService emailService;
	
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	/**
	 * 회원 가입 서비스
	 * @param request 회원 가입 정보
	 * @return
	 */
	@Transactional
	public CreateMemberDTO.Response createMember(CreateMemberDTO.Request request) {
		if(!authService.isVerifiedEmail(request.getEmail())) {
			throw new APIException(ErrorCode.AUTH_EMAIL_NOT_VERIFIED);
		}
		if(!request.getPassword().equals(request.getPasswordConfirm())) {
			throw new APIException(ErrorCode.AUTH_PASSWORD_CONFIRM_NOT_VALID);
			
		}
		
		Member member = modelMapper.map(request, Member.class);
		member.setPassword(passwordEncoder.encode(request.getPassword()));
		return CreateMemberDTO.Response.fromEntity(memberRepository.save(member));
	}

	/**
	 * 회원 탈퇴 서비스
	 * @param request 탈퇴 요청 회원 정보
	 * @param email 로그인 이메일
	 * @return
	 */
	@Transactional
	public DeleteMemberDTO.Response deleteMember(DeleteMemberDTO.Request request, String email, String accessToken) {
		Member member = authService.authenticateMember(email, request.getPassword());
		
		authService.logout(accessToken);
		authService.deleteVerifiedEmail(email);
		stampService.deleteByEmail(email);
		recordService.deleteByEmail(email);
		memberRepository.deleteByEmail(email);
		
		return DeleteMemberDTO.Response.builder().email(member.getEmail()).build();
	}
	
	/**
	 * 비밀번호 변경 서비스
	 * @param request 변경 요청 비밀번호 정보
	 * @param email 로그인 이메일
	 * @return
	 */
	@Transactional
	public UpdatePasswordDTO.Response updatePassword(UpdatePasswordDTO.Request request, String email){
		if(!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
			throw new APIException(ErrorCode.AUTH_PASSWORD_CONFIRM_NOT_VALID);
		}
		
		Member member = authService.authenticateMember(email, request.getPassword());	
		member.setPassword(passwordEncoder.encode(request.getNewPassword()));
		memberRepository.save(member);
		
		return UpdatePasswordDTO.Response.fromEntity(member.getEmail());
	}
	
	
	
	/**
	 * 임시 비밀번호 전송 서비스
	 * @param request 계정 정보
	 * @return
	 */
	@Transactional
	public SendTempPasswordDTO.Response sendTempPassword(SendTempPasswordDTO.Request request) {
		Member member = memberRepository.findByEmailAndName(request.getEmail(), request.getName())
				.orElseThrow(() -> new APIException(ErrorCode.MEMBER_USER_NOT_FOUND));

		String tempPassword = RandomStringUtils.randomAlphanumeric(8);
		member.setPassword(passwordEncoder.encode(tempPassword));
		memberRepository.save(member);
		emailService.sendTempPasswordEmail(request.getEmail(), tempPassword);
		
		return SendTempPasswordDTO.Response.fromEntity(member.getEmail(), member.getName());
	}

}
