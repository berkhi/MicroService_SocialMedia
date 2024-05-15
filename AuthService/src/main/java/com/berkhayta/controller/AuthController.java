package com.berkhayta.controller;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.ActivateCodeRequestDto;
import com.berkhayta.dto.request.LoginRequestDto;
import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserUpdateRequestDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.ERole;
import com.berkhayta.service.AuthService;
import com.berkhayta.utility.JwtTokenManager;
import com.berkhayta.utility.TokenManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final JwtTokenManager jwtTokenManager;
	private final TokenManager tokenManager;
	
	/**
	 * Register İşlemleri:
	 */
	@PostMapping(EndPoints.REGISTER)
	public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
		return ResponseEntity.ok(authService.register(dto));
	}
	
	/**
	 * Login İşlemleri
	 */
	@PostMapping(EndPoints.LOGIN)
	public ResponseEntity<String> dologin(@RequestBody LoginRequestDto dto) {
		return ResponseEntity.ok(authService.doLogin(dto));
	}
	
	@PutMapping(EndPoints.ACTIVATECODE)
	public ResponseEntity<String> activatecode(@RequestBody ActivateCodeRequestDto dto) {
		return ResponseEntity.ok(authService.activateCode(dto));
	}
	
	@DeleteMapping(EndPoints.DELETE+"/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long authId) {
		return ResponseEntity.ok(authService.softDelete(authId));
	}
	
	@GetMapping("/getToken")
	public ResponseEntity<String> getToken(Long id) {
		return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
	}
	@GetMapping("/getTokenWithRole")
	public ResponseEntity<String> getTokenWithRole(Long id, ERole role) {
		return ResponseEntity.ok(jwtTokenManager.createToken(id,role).get());
	}
	
	@GetMapping("/getIdFromToken")
	public ResponseEntity<Long> getIdFromToken(String token) {
		return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
	}
	@GetMapping("/getRoleFromToken")
	public ResponseEntity<String> getRoleFromToken(String token) {
		return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
	}

	@PutMapping("/update/{authId}")
	public ResponseEntity<Boolean> updateAuth(@PathVariable Long authId, @RequestBody UserUpdateRequestDto dto) {
		return ResponseEntity.ok(authService.updateAuth(authId, dto));
	}
	
	/*
	@GetMapping(EndPoints.FINDALL)
	public ResponseEntity<List<Auth>> findAll(String token){
		return ResponseEntity.ok(authService.findAll(token));
	}*/
}