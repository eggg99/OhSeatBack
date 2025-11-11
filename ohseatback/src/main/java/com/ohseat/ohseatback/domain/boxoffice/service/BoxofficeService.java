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
import java.util.Collections;
import java.util.List;

import org.springframework.web.client.RestClientException;
import org.json.JSONException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            // 어제날짜 포맷팅
            LocalDate yesterday = LocalDate.now().minusDays(1);
            String targetDt = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));


            // 영화진흥위원회 박스오피스 조회
            String kobisUrl = KOBIS_URL
                    + "?key=" + KOBIS_KEY
                    + "&targetDt=" + targetDt;

            JSONObject kobisJson = new JSONObject(restTemplate.getForObject(kobisUrl, String.class));

            // faultInfo가 존재할 경우 처리
            if (kobisJson.has("faultInfo")) {
                JSONObject fault = kobisJson.getJSONObject("faultInfo");
                String message = fault.optString("message", "알 수 없는 오류");
                String errorCode = fault.optString("errorCode", "unknown");
                log.warn("KOBIS API 오류 발생 - code: {}, message: {}", errorCode, message);
                return Collections.emptyList(); // 혹은 기본 응답 리턴
            }
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
        } catch (RestClientException e) {
            log.error("KOBIS API 통신 실패", e);
        } catch (JSONException e) {
            log.error("KOBIS API 응답 파싱 실패", e);
        } catch (Exception e) {
            log.error("박스오피스 데이터 처리 중 예외 발생", e);
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


            String response = restTemplate.getForObject(url, String.class);
            if (response == null) {
                log.warn("TMDB API 응답이 null 입니다. 영화명: {}", movieNm);
                return "";
            }

            JSONObject json = new JSONObject(response);
            JSONArray results = json.optJSONArray("results");

            if (results != null && results.length() > 0) {
                String posterPath = results.getJSONObject(0).optString("poster_path", "");
                if (!posterPath.isEmpty()) {
                    return "https://image.tmdb.org/t/p/w500" + posterPath;
                } else {
                    log.info("TMDB 결과에 poster_path 없음. 영화명: {}", movieNm);
                }
            } else {
                log.info("TMDB 검색 결과 없음. 영화명: {}", movieNm);
            }
        } catch (RestClientException e) {
            log.error("TMDB API 호출 실패. 영화명: {}", movieNm, e);
        } catch (Exception e) {
            log.error("TMDB 포스터 처리 중 예외 발생. 영화명: {}", movieNm, e);
        }

        return "";
    }
}
