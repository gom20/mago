package com.gom.mago.dto.stamp;

import javax.validation.constraints.NotNull;

import com.gom.mago.entity.MountainStamp;

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
public class StampDTO{

	private Long mountainId;
	
	private String mountainName;
	
	private String regionType;
	
	private String regionName;
	
	private Integer positionX;
	
	private Integer positionY;
	
	private boolean flag;
	
    public static StampDTO fromEntity(@NotNull MountainStamp mountainStamp){
        return StampDTO.builder()
                .mountainId(mountainStamp.getMountainId())
                .mountainName(mountainStamp.getMountainName())
                .regionType(mountainStamp.getRegionType())
                .regionName(mountainStamp.getRegionName())
                .positionX(mountainStamp.getPositionX())
                .positionY(mountainStamp.getPositionY())
                .flag(mountainStamp.getFlag() == 1? true : false)
                .build();
    }

 }
