package com.berkhayta.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:9091/api/v1/user", name = "userManager", dismiss404 = true)
public interface UserManager {
	@GetMapping("/findByAuthId/{authId}")
	String getUserIdFromAuthId(@PathVariable Long authId);
}