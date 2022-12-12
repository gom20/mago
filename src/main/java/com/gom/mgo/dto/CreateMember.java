package com.gom.mgo.dto;

import com.gom.mgo.entity.Member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @Size(min = 1, max = 16, message = "userId size must be 1 to 16")
		private String email;
        
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
        private String email;

        public static Response fromEntity(@NotNull Member member){
            return Response.builder()
                    .email(member.getEmail())
                    .build();
        }
    }

}
