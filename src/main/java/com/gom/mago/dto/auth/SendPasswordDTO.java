package com.gom.mago.dto.auth;

import javax.validation.constraints.NotNull;

import com.gom.mago.dto.auth.LoginDTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class SendPasswordDTO {

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
        private String name;
  
	}
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String email;
        private String name;
        
        public static Response fromEntity(@NotNull String email, String name){
            return Response.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
    }
}
