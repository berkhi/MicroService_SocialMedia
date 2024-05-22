package com.berkhayta.service;

import com.berkhayta.dto.request.ActivateCodeRequestDto;
import com.berkhayta.dto.request.LoginRequestDto;
import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserEmailUpdateRequestDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.Auth;
import com.berkhayta.entity.EStatus;
import com.berkhayta.exception.AuthServiceException;
import com.berkhayta.exception.ErrorType;
import com.berkhayta.manager.UserManager;
import com.berkhayta.mapper.AuthMapper;
import com.berkhayta.rabbitmq.producer.ActivateStatusProducer;
import com.berkhayta.repository.AuthRepository;
import com.berkhayta.utility.CodeGenerator;
import com.berkhayta.utility.JwtTokenManager;
import com.berkhayta.utility.ServiceManager;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
	private final AuthRepository authRepository;
	private final JwtTokenManager jwtTokenManager;
	private final UserManager userManager;
	private final RabbitTemplate rabbitTemplate;
	private final ActivateStatusProducer activateStatusProducer;

	public AuthService(JpaRepository<Auth, Long> jpaRepository, AuthRepository authRepository,
					   JwtTokenManager jwtTokenManager, UserManager userManager, RabbitTemplate rabbitTemplate,
					   ActivateStatusProducer activateStatusProducer) {
		super(jpaRepository);
		this.authRepository = authRepository;
		this.jwtTokenManager = jwtTokenManager;
		this.userManager = userManager;
		this.rabbitTemplate = rabbitTemplate;
		this.activateStatusProducer = activateStatusProducer;
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
		//openFeign ile microserviceler arası iletişim:
		userManager.save(AuthMapper.INSTANCE.toUserSaveRequestDto(auth));
		return AuthMapper.INSTANCE.authToDto(auth);
	}

	@Transactional
	public RegisterResponseDto registerWithRabbit(RegisterRequestDto dto) {
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
		//Rabbit ile microservice arası iletişim:
		Boolean gonderildiMi = (Boolean) rabbitTemplate.convertSendAndReceive("exchange.direct",
				"update.user.key",
				AuthMapper.INSTANCE.toUserSaveRequestDto(auth));
		System.out.println(gonderildiMi);
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
	@Transactional
	public String statusControl(Auth auth) {
		switch (auth.getStatus()) {
			case ACTIVE -> throw new AuthServiceException(ErrorType.ACCOUNT_ALREADY_ACTIVE);
			case PENDING -> {
				auth.setStatus(EStatus.ACTIVE);
				authRepository.save(auth);
				//OpenFeign ile
				//userManager.updateUserStatus(auth.getId());
				//Rabbit ile:
				activateStatusProducer.convertAndSend(auth.getId());
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
		userManager.deleteUser(authId);
		return authId + " idli kullanıcı silindi.";
	}

	public Boolean updatemail(Long authId, UserEmailUpdateRequestDto dto) {
		Auth auth =
				authRepository.findById(authId).orElseThrow(() -> new AuthServiceException(ErrorType.USER_NOT_FOUND));
		auth.setEmail(dto.getEmail());
		authRepository.save(auth);
		return true;
	}




	/*public List<Auth> findAll(String token) {
		Long idFromToken = null;

//			idFromToken = tokenManager.getIdFromToken(token);
		idFromToken =
				jwtTokenManager.getIdFromToken(token)
				               .orElseThrow(() -> new AuthServiceException(ErrorType.INVALID_TOKEN));

		authRepository.findById(idFromToken).orElseThrow(() -> new AuthServiceException(ErrorType.INVALID_TOKEN));

		return authRepository.findAll();

	}*/
}