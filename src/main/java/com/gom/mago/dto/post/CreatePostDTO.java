package com.gom.mago.dto.post;

import javax.validation.constraints.NotNull;

import com.gom.mago.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class CreatePostDTO {
	
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
		private String content;
	}
	
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
		private Long uid;

        public static Response fromEntity(@NotNull Post post){
            return Response.builder()
                    .uid(post.getUid())
                    .build();
        }
    }

}
