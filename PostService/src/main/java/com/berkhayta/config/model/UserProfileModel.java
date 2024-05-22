package com.berkhayta.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserProfileModel
{
    Long authId;
    String username;
    String email;
    String phone;
    String photo;
    String address;
    String about;
    String status;
}
