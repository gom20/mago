package com.gom.mago.dto.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
		private String mountain;
        
        @NotNull
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startDatetime;
        
        @NotNull
        @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endDatetime;
        
        @NotNull
        private Float distance;
        
        @NotNull
        private Float minAltitude;
        
        @NotNull
        private Float maxAltitude;
        
        @NotNull
        private String imgPath; 
        
        public Post toEntity() {
            return Post.builder()
                     .email(email)
                     .yymmdd(startDatetime.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                     .startDatetime(startDatetime)
                     .endDatetime(endDatetime)
                     .mountain(mountain)
                     .distance(distance)
                     .minAltitude(minAltitude)
                     .maxAltitude(maxAltitude)
                     .imgPath(imgPath)
                     .build();
        }
 
	}
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
		private Long uid;
		
		private String email;
		
		private String yymmdd;
        
		private String mountain;

        private LocalDateTime startDatetime;
 
        private LocalDateTime endDatetime;
        
        private Float distance;
        
        private Float minAltitude;
        
        private Float maxAltitude;
        
        private String imgPath; 

        public static Response fromEntity(@NotNull Post post){
            return Response.builder()
                    .uid(post.getUid())
                    .email(post.getEmail())
                    .yymmdd(post.getYymmdd())
                    .startDatetime(post.getStartDatetime())
                    .endDatetime(post.getEndDatetime())
                    .mountain(post.getMountain())
                    .distance(post.getDistance())
                    .minAltitude(post.getMinAltitude())
                    .maxAltitude(post.getMaxAltitude())
                    .imgPath(post.getImgPath())
                    .build();
        }
    }

}
