package com.berkhayta.mapper;

import com.berkhayta.dto.request.RegisterRequestDto;
import com.berkhayta.dto.request.UserSaveRequestDto;
import com.berkhayta.dto.response.LoginResponseDto;
import com.berkhayta.dto.response.RegisterResponseDto;
import com.berkhayta.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {
	AuthMapper INSTANCE= Mappers.getMapper(AuthMapper.class);
	
	Auth toAuth(RegisterRequestDto dto);
	
	RegisterResponseDto authToDto(Auth auth);
	
	LoginResponseDto toLoginResponseDto(Auth auth);
	@Mapping(source = "id",target = "authId")
	UserSaveRequestDto toUserSaveRequestDto(Auth auth);
}