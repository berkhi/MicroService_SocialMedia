package com.berkhayta.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostUpdateRequestDto {
	String token;
	String postId;
	String content;
	String title;
	String photo;
}