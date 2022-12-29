package com.gom.mago.dto.post;

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
public class PostDTO {
	
	@NotNull
	private Long uid;
	
	@NotNull
	private String email;
    
    @NotNull
	private String content;
    
    
}
