package com.gom.mago.dto.auth;

import javax.validation.constraints.NotNull;

import com.gom.mago.dto.auth.SendPasswordDTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ChangePasswordDTO {
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {
		
        @NotNull
		private String password;
        
        @NotNull
        private String newPassword;
        
        @NotNull
        private String newPasswordConfirm;
  
	}
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String email;
        public static Response fromEntity(@NotNull String email){
            return Response.builder()
                    .email(email)
                    .build();
        }
    }
}
