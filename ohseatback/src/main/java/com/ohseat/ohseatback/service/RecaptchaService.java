package com.ohseat.ohseatback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecaptchaService {

    @Value("${recaptcha.secret-key}") // ✅ 중괄호 닫기!
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyCaptcha(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "secret=" + recaptchaSecret + "&response=" + token;
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                GOOGLE_RECAPTCHA_VERIFY_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        return (Boolean) responseBody.get("success");
    }
}
