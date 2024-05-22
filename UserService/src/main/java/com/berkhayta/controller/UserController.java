package com.berkhayta.controller;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.UserSaveRequestDto;
import com.berkhayta.dto.request.UserUpdateRequestDto;
import com.berkhayta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping(EndPoints.USER)
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping(EndPoints.SAVE)
	public ResponseEntity<Boolean> save(@RequestBody UserSaveRequestDto dto) {
		return ResponseEntity.ok(userService.save(dto));
	}


	@PutMapping(EndPoints.ACTIVATE + "/{authId}")
	ResponseEntity<Boolean> activateUserProfile(@PathVariable Long authId){
		return ResponseEntity.ok(userService.activateUserProfile(authId));
	}

	@PutMapping("/updateWithToken")
	public ResponseEntity<Boolean> updateWithToken(@RequestParam String token, @RequestBody UserUpdateRequestDto dto) {
		return ResponseEntity.ok(userService.updateWithToken(token, dto));
	}

	@PutMapping("/delete/{authId}")
	public ResponseEntity<Boolean> deleteUserProfile(@PathVariable Long authId) {
		return ResponseEntity.ok(userService.deleteUserProfile(authId));
	}

	@GetMapping("/findByAuthId/{token}")
	public String getUserIdFromToken(@PathVariable Long authId) {
		return userService.getUserByAuthId(authId);
	}

}