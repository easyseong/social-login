package com.sociallogin.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NaverRes {
    private String resultcode;

    private String message;

    private NaverUser response;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public class NaverUser {

        private String id;

        private String name;

        private String email;

    }



}
