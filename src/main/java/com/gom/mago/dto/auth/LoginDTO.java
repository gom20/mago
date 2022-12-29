package com.gom.mago.dto.auth;

import javax.validation.constraints.NotNull;

import com.gom.mago.dto.auth.CreateMemberDTO.Response;
import com.gom.mago.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class LoginDTO {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {
        @NotNull
		private String email;
        
        @NotNull
		private String password;
	}
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String token;
        
        private MemberDTO user;
        
        public static Response fromEntity(@NotNull String token, @NotNull MemberDTO memberDTO){
            return Response.builder()
                    .token(token)
                    .user(memberDTO)
                    .build();
        }
    }
}
