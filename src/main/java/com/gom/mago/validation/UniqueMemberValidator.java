package com.gom.mago.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gom.mago.repository.MemberRepository;
import com.gom.mago.validation.annotation.UniqueMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueMemberValidator implements ConstraintValidator<UniqueMember, String> {
	
	final MemberRepository memberRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) { 
		if(memberRepository.findByEmail(value).isPresent()) return false;
		return true;
	}
}
