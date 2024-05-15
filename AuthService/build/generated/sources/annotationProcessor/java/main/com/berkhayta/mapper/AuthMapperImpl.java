package com.berkhayta.mapper;

import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserSaveRequestDto;
import com.berkhayta.dto.response.LoginResponseDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.Auth;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-15T15:37:14+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public Auth toAuth(RegisterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Auth.AuthBuilder<?, ?> auth = Auth.builder();

        auth.username( dto.getUsername() );
        auth.password( dto.getPassword() );
        auth.email( dto.getEmail() );

        return auth.build();
    }

    @Override
    public RegisterResponseDto authToDto(Auth auth) {
        if ( auth == null ) {
            return null;
        }

        RegisterResponseDto.RegisterResponseDtoBuilder registerResponseDto = RegisterResponseDto.builder();

        registerResponseDto.id( auth.getId() );
        registerResponseDto.activationCode( auth.getActivationCode() );
        registerResponseDto.username( auth.getUsername() );

        return registerResponseDto.build();
    }

    @Override
    public LoginResponseDto toLoginResponseDto(Auth auth) {
        if ( auth == null ) {
            return null;
        }

        LoginResponseDto.LoginResponseDtoBuilder loginResponseDto = LoginResponseDto.builder();

        loginResponseDto.id( auth.getId() );
        loginResponseDto.username( auth.getUsername() );
        loginResponseDto.email( auth.getEmail() );
        loginResponseDto.role( auth.getRole() );
        loginResponseDto.status( auth.getStatus() );

        return loginResponseDto.build();
    }

    @Override
    public UserSaveRequestDto toUserSaveRequestDto(Auth auth) {
        if ( auth == null ) {
            return null;
        }

        UserSaveRequestDto.UserSaveRequestDtoBuilder userSaveRequestDto = UserSaveRequestDto.builder();

        userSaveRequestDto.authId( auth.getId() );
        userSaveRequestDto.username( auth.getUsername() );
        userSaveRequestDto.email( auth.getEmail() );

        return userSaveRequestDto.build();
    }
}
