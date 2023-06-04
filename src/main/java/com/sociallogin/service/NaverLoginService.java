package com.sociallogin.service;

import com.sociallogin.dto.NaverCallBackDto;
import com.sociallogin.dto.NaverToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NaverLoginService {

    @Value("${security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUrl;

    public String getNaverAuthorizeUrl() throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", URLEncoder.encode(redirectUrl, "UTF-8"))
                .queryParam("state", URLEncoder.encode("1234", "UTF-8"))
                .build();

        return uriComponents.toString();

    }

    public String getNaverTokenUrl(NaverCallBackDto callBack) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/token")
                .queryParam("grant_type", "authorization_code") // 인증과정에 대한 구분 값 (authorization_code : 발급)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", callBack.getCode())
                .queryParam("state", URLEncoder.encode(callBack.getState(), "UTF-8"))
                .build();

        try {
            URL url = new URL(uriComponents.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            BufferedReader bufferedReader;

            if (responseCode == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }

            bufferedReader.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String getNaverUserByToken(NaverToken token) {
        try {
            String accessToken = token.getAccess_token();
            String tokenType = token.getToken_type();

            URL url = new URL("https://openapi.naver.com/v1/nid/me");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", tokenType + " " + accessToken);

            int responseCode = con.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
