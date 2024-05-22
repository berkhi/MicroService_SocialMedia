package com.berkhayta.manager;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(url = "http://localhost:9091/api/v1/user", name = "userManager", dismiss404 = true)
public interface UserManager {
	@PostMapping(EndPoints.SAVE)
	ResponseEntity<Boolean> save(@RequestBody UserSaveRequestDto dto);

	@PutMapping("/updatestatus/{authId}")
	ResponseEntity<String> updateUserStatus(@PathVariable Long authId);

	@DeleteMapping("/delete/{authId}")
	ResponseEntity<String> deleteUser(@PathVariable Long authId);
}