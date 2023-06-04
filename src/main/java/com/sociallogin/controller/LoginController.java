package com.sociallogin.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sociallogin.dto.NaverCallBackDto;
import com.sociallogin.dto.NaverRes;
import com.sociallogin.dto.NaverToken;
import com.sociallogin.service.NaverLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final NaverLoginService naverLoginService;

    @GetMapping("/naver-login")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        String url = naverLoginService.getNaverAuthorizeUrl();
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/login/social/naver")
    public void callBack(@ModelAttribute NaverCallBackDto naverCallBackDto) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {

        log.info("code========" + naverCallBackDto.getCode());
        log.info("state========" + naverCallBackDto.getState());

        String responseToken = naverLoginService.getNaverTokenUrl(naverCallBackDto);

        log.info("responseToken==========" + responseToken);


        try {
            ObjectMapper mapper = new ObjectMapper();
            NaverToken token = mapper.readValue(responseToken, NaverToken.class);
            String responseUser = naverLoginService.getNaverUserByToken(token);
            NaverRes naverUser = mapper.readValue(responseUser, NaverRes.class);

            System.out.println("naverUser.toString() : " + naverUser.toString());
            System.out.println("naverUser.getId().() : " + naverUser.getResponse().getId());
            System.out.println("naverUser.getEmail().() : " + naverUser.getResponse().getEmail());
            System.out.println("naverUser.getName().() : " + naverUser.getResponse().getName());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
