package com.gom.mago.dto.record;

import javax.validation.constraints.NotNull;

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
public class FeedDTO {
	
	private Long id;
	
    @NotNull
	private String username;
    
    @NotNull
	private String feed;
    
    
}
