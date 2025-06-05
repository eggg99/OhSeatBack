package com.ohseat.ohseatback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API 테스트 편의를 위해 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 개발 중 API 테스트 위해 인증 없이 허용
                        .anyRequest().permitAll()

                        // 운영 시 주석 해제
//                        .requestMatchers("/api/user/**", "/api/user/login", "/api/user/join").permitAll() // 로그인, 회원가입은 인증 없이 허용
//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .httpBasic(Customizer.withDefaults()); // 기본 인증 (테스트용)

        return http.build();
    }
}
