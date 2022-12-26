package com.gom.mago.dto.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gom.mago.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CreateMember {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {	       
        @NotNull
        @Size(min = 1, max = 40, message = "email size must be 1 to 40")
		private String email;
        
        @NotNull
        @Size(min = 1, max = 40, message = "name size must be 1 to 40")
		private String name;

        
        @NotNull
        @Size(min = 1, max = 40, message = "password size must be 1 to 16")
		private String password;
	}
	
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String email;

        public static Response from(@NotNull Member member){
            return Response.builder()
                    .email(member.getEmail())
                    .build();
        }
    }

}
