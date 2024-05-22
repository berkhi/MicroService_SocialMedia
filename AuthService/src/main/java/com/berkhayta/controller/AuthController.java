package com.berkhayta.controller;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.ActivateCodeRequestDto;
import com.berkhayta.dto.request.LoginRequestDto;
import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserEmailUpdateRequestDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.ERole;
import com.berkhayta.service.AuthService;
import com.berkhayta.utility.JwtTokenManager;
import com.berkhayta.utility.TokenManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	@PostMapping(EndPoints.REGISTERWITHRABBIT)
	public ResponseEntity<RegisterResponseDto> registerWithRabbit(@RequestBody @Valid RegisterRequestDto dto) {
		return ResponseEntity.ok(authService.registerWithRabbit(dto));
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
	
	@PutMapping("/updatemail/{authId}")
	public ResponseEntity<Boolean> updatemail(@PathVariable Long authId, @RequestBody UserEmailUpdateRequestDto dto) {
		return ResponseEntity.ok(authService.updatemail(authId,dto));
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
	
	
	/*
	@GetMapping(EndPoints.FINDALL)
	public ResponseEntity<List<Auth>> findAll(String token){
		return ResponseEntity.ok(authService.findAll(token));
	}*/
}