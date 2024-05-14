package com.berkhayta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "tbl_auth")
public class Auth extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(unique = true)
	String username;
	String password;
	String email;
	String activationCode;
	@Builder.Default
	@Enumerated(EnumType.STRING)
	ERole role = ERole.USER;
	@Builder.Default
	@Enumerated(EnumType.STRING)
	EStatus status = EStatus.PENDING;

}