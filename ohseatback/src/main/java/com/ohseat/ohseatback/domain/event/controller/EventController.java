package com.ohseat.ohseatback.domain.event.controller;

import com.ohseat.ohseatback.domain.event.dto.EventCreateRequestDTO;
import com.ohseat.ohseatback.domain.event.dto.EventListResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventMainResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventUpdateRequestDTO;
import com.ohseat.ohseatback.domain.event.service.EventService;
import com.ohseat.ohseatback.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    // 메인 이벤트 조회 (진행중+ 랜덤)
    @GetMapping("/main")
    public ResponseEntity<List<EventMainResponseDTO>> getMainEvents(@RequestParam(defaultValue = "2") int count) {
        return ResponseEntity.ok(eventService.getMainEvents(count));
    }

    // 이벤트 전체 조회
    @GetMapping("/list")
    public ResponseEntity<Page<EventListResponseDTO>> list(@RequestParam(defaultValue = "0") Integer categoryId,
                                                           @RequestParam(defaultValue = "0") Integer searchType,
                                                           @RequestParam(required = false) String searchValue,
                                                           @RequestParam(defaultValue = "latest") String orderType,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(eventService.getEventList(categoryId, searchType, searchValue, orderType, page, size));
    }

    // 이벤트 상세 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<?> detail(@PathVariable int eventId, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(eventService.getEventDetail(eventId, request, response));
    }

    // 좋아요
    @PostMapping("/{eventId}/like")
    public ResponseEntity<Map<String, String>> like(@PathVariable Integer eventId) {
        eventService.like(eventId);
        return ResponseEntity.ok(Map.of("msg", "좋아요 성공"));
    }

    // 좋아요 취소
    @DeleteMapping("/{eventId}/like")
    public ResponseEntity<Map<String, String>> unlike(@PathVariable Integer eventId) {
        eventService.unlike(eventId);
        return ResponseEntity.ok(Map.of("msg", "좋아요 취소"));
    }

    // 게시글 작성
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEvent(@RequestPart("data") EventCreateRequestDTO dto,
                                                           @RequestPart(value = "poster", required = false) MultipartFile poster,
                                                           @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                                           @RequestPart(value = "banner", required = false) MultipartFile banner,
                                                           @RequestPart(value = "content", required = false)List<MultipartFile> contentFiles) {

        int eventId = eventService.createEvent(dto, SecurityUtil.getCurrentUserId(), poster, thumbnail, banner, contentFiles);

        Map<String, Object> result = new HashMap<>();
        result.put("eventId", eventId);
        result.put("msg", "이벤트 등록 성공");

        return ResponseEntity.ok(result);
    }

    // 게시글 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> updateEvent(@PathVariable int eventId, @RequestPart("data") EventUpdateRequestDTO dto,
                                                           @RequestPart(value = "new_poster", required = false) MultipartFile newPoster, @RequestPart(value = "delete_poster_ids", required = false) List<Integer> deletePosterIds,
                                                           @RequestPart(value = "new_thumbnail", required = false) MultipartFile newThumbnail, @RequestPart(value = "delete_thumbnail_ids", required = false) List<Integer> deleteThumbnailIds,
                                                           @RequestPart(value = "new_banner", required = false) MultipartFile newBanner, @RequestPart(value = "delete_banner_ids", required = false) List<Integer> deleteBannerIds,
                                                           @RequestPart(value = "new_content", required = false) List<MultipartFile> newContentFiles, @RequestPart(value = "delete_content_ids", required = false) List<Integer> deleteContentIds) {

        eventService.updateEvent(eventId, dto, newPoster, deletePosterIds, newThumbnail, deleteThumbnailIds, newBanner, deleteBannerIds, newContentFiles, deleteContentIds);

        return ResponseEntity.ok(Map.of("msg", "이벤트 수정 성공"));
    }

    // 게시글 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> deleteEvent(@PathVariable int eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(Map.of("msg", "이벤트 삭제 성공"));
    }

}
