package com.gom.mago.dto.member;

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
        @Size(min = 1, max = 40, message = "username size must be 1 to 40")
		private String username;
        
        @NotNull
        @Size(min = 1, max = 16, message = "password size must be 1 to 16")
		private String password;
	}
	
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String username;

        public static Response fromEntity(@NotNull Member member){
            return Response.builder()
                    .username(member.getUsername())
                    .build();
        }
    }

}
