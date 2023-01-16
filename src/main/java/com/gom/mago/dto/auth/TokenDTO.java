package com.gom.mago.dto.auth;

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
public class TokenDTO {
	
	@NotNull
	private String accessToken;
	
	@NotNull
	private String refreshToken;
	
}
