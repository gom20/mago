package com.gom.mago.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gom.mago.entity.Member;
import com.gom.mago.validation.annotation.UniqueMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CreateMemberDTO {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {	       
	
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @UniqueMember
		private String email;
        
        @NotBlank(message = "이름은 필수 입력값입니다.")
        @Size(min = 1, max = 10, message = "이름은 10자 이내여야 합니다.")
		private String name;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp="^[A-Za-z[0-9]]{8,20}", message="비밀번호는 영문과 숫자를 포함해서 8~20 자리 이내로 입력해주세요.")
		private String password;
        
        @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
		private String passwordConfirm;
	}
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String email;

        public static Response fromEntity(@NotNull Member member){
            return Response.builder()
                    .email(member.getEmail())
                    .build();
        }
    }

}
