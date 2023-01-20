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
    
    private Integer totalTime; 
    
    private Integer hikingTime;
    
    private Integer breakTime;
    
    private Float avgSpeed;
    
    private String imgPath; 
    
    public static RecordDTO fromEntity(@NotNull Record record){
        return RecordDTO.builder()
                .uid(record.getUid())
                .email(record.getEmail())
                .yymmdd(record.getYymmdd())
                .startDatetime(record.getStartDatetime())
                .endDatetime(record.getEndDatetime())
                .mountain(record.getMountain())
                .distance(record.getDistance())
                .minAltitude(record.getMinAltitude())
                .maxAltitude(record.getMaxAltitude())
                .totalTime(record.getTotalTime())
                .hikingTime(record.getHikingTime())
                .breakTime(record.getBreakTime())
                .avgSpeed(record.getAvgSpeed())
                .imgPath(record.getImgPath())
                .build();
    }
    
}
