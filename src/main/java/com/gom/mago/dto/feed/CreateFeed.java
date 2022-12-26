package com.gom.mago.dto.feed;

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
		private String name;
        
        @NotNull
		private String feed;
	}
	
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String name;

        public static Response fromEntity(@NotNull Feed feed){
            return Response.builder()
                    .name(feed.getName())
                    .build();
        }
    }

}
