package com.berkhayta.entity;

import com.berkhayta.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Post {
	@MongoId
	String id;
	
	String userId;

	String content;
	String title;
	String photo;
	
	@CreatedDate
	LocalDateTime createAt;
	@LastModifiedDate
	LocalDateTime updateAt;
	@Builder.Default
	EStatus status = EStatus.ACTIVE;
	@Builder.Default
	Integer likeCount = 0;
	@Builder.Default
	Integer reTweetCount = 0;
}