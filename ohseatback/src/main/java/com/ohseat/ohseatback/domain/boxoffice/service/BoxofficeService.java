package com.ohseat.ohseatback.domain.boxoffice.service;

import com.ohseat.ohseatback.domain.boxoffice.dto.BoxofficeResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxofficeService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String KOBIS_URL = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String TMDB_URL = "https://api.themoviedb.org/3/search/movie";

    @Value("${kobis.api.key}")
    private String KOBIS_KEY;

    @Value("${tmdb.api.key}")
    private String TMDB_KEY;

    public List<BoxofficeResponse> getBoxofficeWithPoster() {
        List<BoxofficeResponse> list = new ArrayList<>();

        try {
            // 오늘날짜 포맷팅
            LocalDate today = LocalDate.now();
            String targetDt = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            // 영화진흥위원회 박스오피스 조회
            String kobisUrl = KOBIS_URL
                    + "?key=" + KOBIS_KEY
                    + "&targetDt=" + targetDt;

            JSONObject kobisJson = new JSONObject(restTemplate.getForObject(kobisUrl, String.class));
            JSONArray boxOfficeList = kobisJson
                    .getJSONObject("boxOfficeResult")
                    .getJSONArray("dailyBoxOfficeList");

            // 각 영화마다 TMDB에서 포스터 가져오기
            for (int i = 0; i < boxOfficeList.length(); i++) {
                JSONObject movie = boxOfficeList.getJSONObject(i);
                String movieNm = movie.getString("movieNm");
                String openDt = movie.optString("openDt", "");
                String rank = movie.getString("rank");
                String audiAcc = movie.optString("audiAcc", "");

                String posterUrl = getPosterFromTmdb(movieNm, openDt);

                list.add(BoxofficeResponse.builder()
                        .rank(rank)
                        .movieNm(movieNm)
                        .openDt(openDt)
                        .audiAcc(audiAcc)
                        .posterUrl(posterUrl)
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private String getPosterFromTmdb(String movieNm, String openDt) {
        try {
            String encodedTitle = URLEncoder.encode(movieNm, StandardCharsets.UTF_8);
            String url = TMDB_URL
                    + "?api_key=" + TMDB_KEY
                    + "&query=" + encodedTitle
                    + "&language=ko-KR";


            JSONObject json = new JSONObject(restTemplate.getForObject(url, String.class));
            JSONArray results = json.optJSONArray("results");

            if (results != null && results.length() > 0) {
                String posterPath = results.getJSONObject(0).optString("poster_path", "");
                if (!posterPath.isEmpty()) {
                    return "https://image.tmdb.org/t/p/w500" + posterPath;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
