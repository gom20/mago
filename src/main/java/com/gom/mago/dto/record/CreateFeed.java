package com.gom.mago.dto.record;

import javax.validation.constraints.NotNull;

import com.gom.mago.entity.Feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CreateFeed {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {
        @NotNull
		private String username;
        
        @NotNull
		private String feed;
	}
	
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String username;

        public static Response fromEntity(@NotNull Feed feed){
            return Response.builder()
                    .username(feed.getUsername())
                    .build();
        }
    }

}
