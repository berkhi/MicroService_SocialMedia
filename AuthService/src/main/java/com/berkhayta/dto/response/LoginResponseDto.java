package com.berkhayta.dto.response;

import com.berkhayta.entity.ERole;
import com.berkhayta.entity.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginResponseDto {
	Long id;
	String username;
	String email;
	ERole role;
	EStatus status;
}