package com.ohseat.ohseatback.domain.cinesquare.service;
import com.ohseat.ohseatback.domain.cinesquare.dto.LocationResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LocationService {

    // ✅ CSV 데이터를 캐싱할 메모리 공간
    private final Map<String, List<LocationResponse>> locationCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        loadCsvData();
    }

    private void loadCsvData() {
        try {
            ClassPathResource resource = new ClassPathResource("data/restrict.csv");
            InputStream inputStream = resource.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length < 2) continue;

                    String city = parts[0].trim();
                    String district = parts[1].trim();

                    LocationResponse location = new LocationResponse(city, district);
                    locationCache.computeIfAbsent(city, k -> new ArrayList<>()).add(location);
                }

                System.out.println("✅ CSV 지역 데이터 캐싱 완료 (" + locationCache.size() + "개 시도)");
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV 로딩 실패", e);
        }
    }

    // 시도명, 시군구명 둘 다 포함 검색
    public List<LocationResponse> search(String keyword) {
        List<LocationResponse> result = new ArrayList<>();

        locationCache.values().forEach(list ->
                list.stream()
                        .filter(l -> l.getCity().contains(keyword) || l.getDistrict().contains(keyword))
                        .forEach(result::add)
        );
        return result;
    }

}
