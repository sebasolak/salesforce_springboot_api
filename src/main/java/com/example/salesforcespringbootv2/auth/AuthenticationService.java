package com.example.salesforcespringbootv2.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    @Value("${login.login_url}")
    private String login_url;

    @Value("${login.grant_type:password}")
    private String grant_type;

    @Value("${login.username}")
    private String username;

    @Value("${login.password}")
    private String password;

    @Value("${login.client_id}")
    private String client_id;

    @Value("${login.client_secret}")
    private String client_secret;

    public AuthenticationResponse login() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grant_type);
        params.add("client_id", client_id);
        params.add("client_secret", client_secret);
        params.add("username", username);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate
                .postForEntity(
                        login_url,
                        request,
                        AuthenticationResponse.class
                );
//        <AuthenticationResponse>
        return (AuthenticationResponse) responseEntity.getBody();
    }
}















