package com.gom.mago.dto.post;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.gom.mago.dto.post.CreateRecordDTO.Response;
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

    private LocalDateTime startDatetime;

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
