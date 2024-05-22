package com.berkhayta.mapper;

import com.berkhayta.dto.response.PostFindAllResponseDto;
import com.berkhayta.entity.Post;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-21T21:14:26+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostFindAllResponseDto postToPostFindAllResponseDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostFindAllResponseDto.PostFindAllResponseDtoBuilder postFindAllResponseDto = PostFindAllResponseDto.builder();

        postFindAllResponseDto.id( post.getId() );
        postFindAllResponseDto.userId( post.getUserId() );
        postFindAllResponseDto.content( post.getContent() );
        postFindAllResponseDto.title( post.getTitle() );
        postFindAllResponseDto.photo( post.getPhoto() );
        postFindAllResponseDto.createAt( post.getCreateAt() );

        return postFindAllResponseDto.build();
    }
}
