package com.berkhayta.service;

import com.berkhayta.dto.request.UserSaveRequestDto;
import com.berkhayta.dto.request.UserUpdateRequestDto;
import com.berkhayta.entity.UserProfile;
import com.berkhayta.entity.enums.EStatus;
import com.berkhayta.exception.ErrorType;
import com.berkhayta.exception.UserServiceException;
import com.berkhayta.manager.AuthManager;
import com.berkhayta.mapper.UserMapper;
import com.berkhayta.repository.UserRepository;
import com.berkhayta.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final JwtTokenManager jwtTokenManager;
	private final AuthManager authManager;

	public Boolean save(UserSaveRequestDto dto) {
		UserProfile save = userRepository.save(UserMapper.INSTANCE.toUserProfile(dto));
		if (save == null)
			throw new UserServiceException(ErrorType.INTERNAL_SERVER_ERROR);
		return true;
	}

	public Boolean activateUserProfile(Long authId) {
		UserProfile userProfile = userRepository.findByAuthId(authId)
				.orElseThrow(() -> new UserServiceException(ErrorType.INVALID_USERPROFILE_ID));
		userProfile.setStatus(EStatus.ACTIVE);
		userRepository.save(userProfile);
		return true;
	}

	@Transactional
	public Boolean updateWithToken(String token, UserUpdateRequestDto dto) {
		Long userId = jwtTokenManager.getIdFromToken(token)
				.orElseThrow(() -> new UserServiceException(ErrorType.INVALID_TOKEN));

		UserProfile userProfile = userRepository.findByAuthId(userId)
				.orElseThrow(() -> new UserServiceException(ErrorType.INVALID_USERPROFILE_ID));
		String trim = dto.getAbout().trim();
		if (dto.getAbout() != null && !trim.isEmpty() && dto.getAbout().equals("string")) {
			userProfile.setAbout(dto.getAbout());
		}
		if (dto.getPhoto() != null && !trim.isEmpty() && dto.getAbout().equals("string")) {
			userProfile.setPhoto(dto.getPhoto());
		}
		if (dto.getPhone() != null && !trim.isEmpty() && dto.getAbout().equals("string")) {
			userProfile.setPhone(dto.getPhone());
		}
		if (dto.getAddress() != null && !trim.isEmpty() && dto.getAbout().equals("string")) {
			userProfile.setAddress(dto.getAddress());
		}
		if (dto.getEmail() != null && !trim.isEmpty() && dto.getAbout().equals("string")) {
			userProfile.setEmail(dto.getEmail());
		}

		userRepository.save(userProfile);
		try {
			authManager.updateAuth(userProfile.getAuthId(), dto);
		} catch (Exception e) {
			throw new UserServiceException(ErrorType.AUTH_SERVICE_UNAVAILABLE);
		}
		return true;
	}

	@Transactional
	public Boolean deleteUserProfile(Long authId) {
		UserProfile userProfile = userRepository.findByAuthId(authId)
				.orElseThrow(() -> new UserServiceException(ErrorType.INVALID_USERPROFILE_ID));

		userProfile.setStatus(EStatus.DELETED);
		userRepository.save(userProfile);
		return true;
	}
}