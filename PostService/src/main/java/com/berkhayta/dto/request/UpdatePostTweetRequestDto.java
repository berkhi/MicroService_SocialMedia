package com.berkhayta.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdatePostTweetRequestDto
{
    String tweetId;
    Long authId;
    String newContext;

}