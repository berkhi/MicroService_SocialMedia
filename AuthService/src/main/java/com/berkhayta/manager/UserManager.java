package com.berkhayta.manager;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "http://localhost:9091/api/v1/user", name = "userManager", dismiss404 = true)
public interface UserManager {
	@PostMapping(EndPoints.SAVE)
	ResponseEntity<Boolean> save(@RequestBody UserSaveRequestDto dto);

	@PutMapping(EndPoints.ACTIVATE + "/{authId}")
	ResponseEntity<Boolean> activateUserProfile(@PathVariable("authId") Long authId);

	@PutMapping("/delete/{authId}")
	ResponseEntity<Boolean> deleteUserProfile(@PathVariable("authId") Long authId);


}