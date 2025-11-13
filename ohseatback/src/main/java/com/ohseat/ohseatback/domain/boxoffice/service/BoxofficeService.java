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
import java.util.*;

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

                Map<String, String> tmdbInfo = getMovieInfoFromTmdb(movieNm, openDt);

                String posterUrl = tmdbInfo != null ? tmdbInfo.get("posterUrl") : "";
                String certification = tmdbInfo != null ? tmdbInfo.get("certification") : "";


                list.add(BoxofficeResponse.builder()
                        .rank(rank)
                        .movieNm(movieNm)
                        .openDt(openDt)
                        .audiAcc(audiAcc)
                        .posterUrl(posterUrl)
                        .certification(certification)
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

    private Map<String, String> getMovieInfoFromTmdb(String movieNm, String openDt) {
        try {
            String encodedTitle = URLEncoder.encode(movieNm, StandardCharsets.UTF_8);
            String searchUrl = TMDB_URL
                    + "?api_key=" + TMDB_KEY
                    + "&query=" + encodedTitle
                    + "&language=ko-KR";

            String response = restTemplate.getForObject(searchUrl, String.class);
            if (response == null) return null;

            JSONObject json = new JSONObject(response);
            JSONArray results = json.optJSONArray("results");
            if (results == null || results.isEmpty()) return null;

            JSONObject movie = results.getJSONObject(0);
            int movieId = movie.optInt("id");
            String posterPath = movie.optString("poster_path", "");

            String posterUrl = posterPath.isEmpty()
                    ? ""
                    : "https://image.tmdb.org/t/p/w500" + posterPath;

            // 등급 조회
            String ratingUrl = "https://api.themoviedb.org/3/movie/" + movieId
                    + "/release_dates?api_key=" + TMDB_KEY;

            String ratingResponse = restTemplate.getForObject(ratingUrl, String.class);
            String certification = "";

            if (ratingResponse != null) {
                JSONObject ratingJson = new JSONObject(ratingResponse);
                JSONArray resultsArr = ratingJson.optJSONArray("results");
                if (resultsArr != null) {
                    for (int i = 0; i < resultsArr.length(); i++) {
                        JSONObject country = resultsArr.getJSONObject(i);
                        if ("KR".equals(country.optString("iso_3166_1"))) {
                            JSONArray releaseDates = country.optJSONArray("release_dates");
                            if (releaseDates != null && !releaseDates.isEmpty()) {
                                certification = releaseDates.getJSONObject(0)
                                        .optString("certification", "");
                                break;
                            }
                        }
                    }
                }
            }
            Map<String, String> info = new HashMap<>();
            info.put("posterUrl", posterUrl);
            info.put("certification", certification);
            return info;
        } catch (RestClientException e) {
            log.error("TMDB API 호출 실패. 영화명: {}", movieNm, e);
        } catch (Exception e) {
            log.error("TMDB 포스터 처리 중 예외 발생. 영화명: {}", movieNm, e);
        }

        Map<String, String> info = new HashMap<>();
        info.put("posterUrl", "");
        info.put("certification", "");
        return info;
    }
}
