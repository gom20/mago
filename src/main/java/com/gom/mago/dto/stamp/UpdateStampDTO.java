package com.gom.mago.dto.stamp;

import javax.validation.constraints.NotNull;

import com.gom.mago.entity.Stamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class UpdateStampDTO {
	
	@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
	public static class Request {
		
		private String email;
        
        @NotNull
		private Long mountainId;
        
        @NotNull
        private boolean flag;
        
        public Stamp toEntity() {
            return Stamp.builder()
                     .email(email)
                     .mountainId(mountainId)
                     .flag(flag)
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
        
		private Long mountainId;
        
        private boolean flag;

        public static Response fromEntity(@NotNull Stamp stamp){
            return Response.builder()
            		.uid(stamp.getUid())
                    .email(stamp.getEmail())
                    .mountainId(stamp.getMountainId())
                    .flag(stamp.isFlag())
                    .build();
        }
    }

}
