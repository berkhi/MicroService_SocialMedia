package com.berkhayta.entity;

import com.berkhayta.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class UserProfile {
	@MongoId
	String id;
	Long authId;
	String username;
	String email;
	String photo;
	String phone;
	String address;
	String about;
	@Builder.Default
	EStatus status=EStatus.PENDING;
}