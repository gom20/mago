package com.gom.mgo.dto.member;

import javax.validation.constraints.NotNull;

import com.gom.mgo.dto.member.CreateMember.Response;
import com.gom.mgo.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class Login {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {
        @NotNull
		private String id;
        
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
        
        public static Response fromEntity(@NotNull String token){
            return Response.builder()
                    .token(token)
                    .build();
        }
    }
}
