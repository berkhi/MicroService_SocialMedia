package com.berkhayta.manager;

import com.berkhayta.dto.request.UserUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://127.0.0.1:9090/api/v1/auth", name = "authManager", dismiss404 = true)
public interface AuthManager {

    @PutMapping("/update/{authId}")
    ResponseEntity<Boolean> updateAuth(@PathVariable("authId") Long authId, @RequestBody UserUpdateRequestDto dto);
}
