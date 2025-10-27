package com.ohseat.ohseatback.utils;

import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.Map;

@Component
public class LocationUtils {

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
    @Value("${kakao.api.key}")
    private String KAKAO_API_KEY;

//    private static final String KAKAO_API_KEY = "KakaoAK f2b6f05d5df8b3011f9aac5ffcd6eeab";

    /**
     * 위도, 경도를 기반으로 LocationResponse DTO 반환
     */
    public LocationResponse getLocation(Double longitude, Double latitude) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", KAKAO_API_KEY);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_API_URL)
                .queryParam("x", longitude)
                .queryParam("y", latitude);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        LocationResponse locationResponse = new LocationResponse();

        if (body != null) {
            List<?> documents = (List<?>) body.get("documents");
            if (documents != null && !documents.isEmpty()) {
                Map<String, Object> doc = (Map<String, Object>) documents.get(0);
                Map<String, Object> address = (Map<String, Object>) doc.get("address");

                if (address != null) {
                    locationResponse.setCity((String) address.getOrDefault("region_1depth_name", ""));
                    locationResponse.setDistrict((String) address.getOrDefault("region_2depth_name", ""));
                }
            }
        }

        // documents가 비어있거나 address가 없으면 city, district는 기본값 "" 유지
        return locationResponse;
    }
}
