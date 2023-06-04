package com.sociallogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverCallBackDto {

    private String code;

    private String state;

    private String error;

    private String error_description;
}
