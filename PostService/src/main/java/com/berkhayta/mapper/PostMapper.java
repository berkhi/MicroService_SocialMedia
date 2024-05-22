package com.berkhayta.mapper;


import com.berkhayta.dto.request.PostUpdateRequestDto;
import com.berkhayta.dto.response.PostFindAllResponseDto;
import com.berkhayta.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
	PostMapper INSTANCE= Mappers.getMapper(PostMapper.class);
	
	
	PostFindAllResponseDto postToPostFindAllResponseDto(Post post);
	
}