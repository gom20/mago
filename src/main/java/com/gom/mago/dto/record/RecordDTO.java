package com.gom.mago.dto.record;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gom.mago.entity.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecordDTO {
	
	private Long uid;
	
	private String email;
	
	private String yymmdd;
    
	private String mountain;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDatetime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDatetime;
    
    private Float distance;
    
    private Float minAltitude;
    
    private Float maxAltitude;
    
    private String imgPath; 
    
    public static RecordDTO fromEntity(@NotNull Record post){
        return RecordDTO.builder()
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
