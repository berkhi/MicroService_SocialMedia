package com.berkhayta.service;

import com.berkhayta.dto.request.ActivateCodeRequestDto;
import com.berkhayta.dto.request.LoginRequestDto;
import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserUpdateRequestDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.Auth;
import com.berkhayta.entity.EStatus;
import com.berkhayta.exception.AuthServiceException;
import com.berkhayta.exception.ErrorType;
import com.berkhayta.manager.UserManager;
import com.berkhayta.mapper.AuthMapper;
import com.berkhayta.repository.AuthRepository;
import com.berkhayta.utility.CodeGenerator;
import com.berkhayta.utility.JwtTokenManager;
import com.berkhayta.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
	private final AuthRepository authRepository;
	private final JwtTokenManager jwtTokenManager;
	private final UserManager userManager;
	
	public AuthService(JpaRepository<Auth, Long> jpaRepository, AuthRepository authRepository,
	                   JwtTokenManager jwtTokenManager, UserManager userManager) {
		super(jpaRepository);
		this.authRepository = authRepository;
		this.jwtTokenManager = jwtTokenManager;
		this.userManager = userManager;
	}
	@Transactional
	public RegisterResponseDto register(RegisterRequestDto dto) {
		//password ve repassword eşitliği kontrol edilir:
		if (!dto.getPassword().equals(dto.getRepassword())) {
			throw new AuthServiceException(ErrorType.PASSWORDS_NOT_MATCHED);
		}
		//username daha önce alınmış mı kontrol edilir:
		if (authRepository.existsByUsername(dto.getUsername())) {
			throw new AuthServiceException(ErrorType.USERNAME_ALREADY_TAKEN);
		}
		//Kontrollerden başarılı bir şekilde geçildiyse dto'dan gelen bilgilerle Auth nesnesi oluşturulur.
		Auth auth = AuthMapper.INSTANCE.toAuth(dto);
		auth.setActivationCode(CodeGenerator.generateActivationCode());
		System.out.println(auth.getActivationCode());
		//Bu auth nesnesi repository save metodu ile kaydedilir.
		authRepository.save(auth);
		userManager.save(AuthMapper.INSTANCE.toUserSaveRequestDto(auth));
		return AuthMapper.INSTANCE.authToDto(auth);
	}
	
	/**
	 * Username ve password ile doğrulama işlemi yapar. Eğer doğrulama başarısız olursa hata fırlatalım. Eğer doğrulama
	 * başarılı ise bir kimlik verelim.
	 *
	 * @param dto
	 * @return
	 */
	public String doLogin(LoginRequestDto dto) {
		Auth auth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(),
		                                                          dto.getPassword())
		                          .orElseThrow(() -> new AuthServiceException(ErrorType.EMAIL_OR_PASSWORD_WRONG));
		
		if (!auth.getStatus().equals(EStatus.ACTIVE)) {
			throw new AuthServiceException(ErrorType.ACCOUNT_NOT_ACTIVE);
		}
		
		return jwtTokenManager.createToken(auth.getId(),auth.getRole())
		                      .orElseThrow(()->new AuthServiceException(ErrorType.TOKEN_CREATION_FAILED));
		
	}
	public String activateCode(ActivateCodeRequestDto dto) {
		Auth auth = authRepository.findById(dto.getId())
		                          .orElseThrow(() -> new AuthServiceException(ErrorType.USER_NOT_FOUND));
		if (!auth.getActivationCode().equals(dto.getActivationCode())) {
			throw new AuthServiceException(ErrorType.ACTIVATION_CODE_MISMATCH);
		}
		
		return statusControl(auth);
	}
	
	private String statusControl(Auth auth) {
		switch (auth.getStatus()) {
			case ACTIVE -> throw new AuthServiceException(ErrorType.ACCOUNT_ALREADY_ACTIVE);
			case PENDING -> {
				auth.setStatus(EStatus.ACTIVE);
				try {
					userManager.activateUserProfile(auth.getId());
				} catch (Exception e) {
					auth.setStatus(EStatus.PENDING);
					authRepository.save(auth);
					throw new AuthServiceException(ErrorType.USER_SERVICE_UNAVAILABLE);
				}
				authRepository.save(auth);
				return "Aktivasyon Başarılı! Sisteme giriş yapabilirsiniz.";
			}
			case BANNED -> throw new AuthServiceException(ErrorType.ACCOUNT_BANNED);
			case DELETED -> throw new AuthServiceException(ErrorType.ACCOUNT_DELETED);
			default -> throw new AuthServiceException(ErrorType.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String softDelete(Long authId) {
		Auth auth = authRepository.findById(authId)
		                          .orElseThrow(() -> new AuthServiceException(ErrorType.USER_NOT_FOUND));
		if (auth.getStatus().equals(EStatus.DELETED)) {
			throw new AuthServiceException(ErrorType.ACCOUNT_ALREADY_DELETED);
		}
		auth.setStatus(EStatus.DELETED);
		authRepository.save(auth);

		try {
			userManager.deleteUserProfile(auth.getId());
		} catch (Exception e) {
			throw new AuthServiceException(ErrorType.USER_SERVICE_UNAVAILABLE);
		}

		return authId + " idli kullanıcı silindi.";
	}

	public Boolean updateAuth(Long authId, UserUpdateRequestDto dto) {
		Auth auth = authRepository.findById(authId)
				.orElseThrow(() -> new AuthServiceException(ErrorType.USER_NOT_FOUND));
		auth.setEmail(dto.getEmail());
		authRepository.save(auth);

		return true;
	}


}