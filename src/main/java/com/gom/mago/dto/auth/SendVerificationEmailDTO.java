package com.gom.mago.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.gom.mago.validation.annotation.UniqueMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SendVerificationEmailDTO {
	
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @UniqueMember
	private String email;

}
