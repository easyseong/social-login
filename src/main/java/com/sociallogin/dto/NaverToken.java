package com.sociallogin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NaverToken {

    private String access_token;

    private String refresh_token;

    private String token_type;

    private Integer expires_in;

    private String error;

    private String error_description;

}
