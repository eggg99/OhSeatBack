package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.common.service.ViewCountService;
import com.ohseat.ohseatback.domain.event.dto.EventCreateRequestDTO;
import com.ohseat.ohseatback.domain.event.dto.EventDetailResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventListResponseDTO;
import com.ohseat.ohseatback.domain.event.dto.EventUpdateRequestDTO;
import com.ohseat.ohseatback.domain.event.entity.Event;
import com.ohseat.ohseatback.domain.event.mapper.EventMapper;
import com.ohseat.ohseatback.domain.file.service.FileService;
import com.ohseat.ohseatback.security.SecurityUtil;
import com.ohseat.ohseatback.utils.CustomPageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final FileService fileService;
    private final ViewCountService viewCountService;

    // 이벤트 전체 조회
    public Page<EventListResponseDTO> getEventList(Integer categoryId, Integer searchType, String searchValue, String orderType, int page, int size) {

        Pageable pageable = CustomPageUtils.getPageable(page, size);

        long totalCount = (categoryId == 0)
                ? eventMapper.selectEventTotalCount(searchType, searchValue)
                : eventMapper.selectEventCategoryCount(categoryId, searchType, searchValue);

        List<EventListResponseDTO> list = eventMapper.selectEventList(categoryId, searchType, searchValue, orderType, (int)pageable.getOffset(), size);

        list.forEach(dto -> dto.setEnd(dto.getEndDt().isBefore(LocalDate.now())));

        return new PageImpl<>(list, pageable, totalCount);
    }

    // 이벤트 상세 조회
    @Transactional
    public EventDetailResponseDTO getEventDetail(int eventId, HttpServletRequest request, HttpServletResponse response) {

        EventDetailResponseDTO dto = eventMapper.selectEventDetail(eventId);

        if (dto == null) {
            throw new IllegalStateException("이벤트가 존재하지 않습니다.");
        }

        Integer userId = SecurityUtil.getCurrentUserId();

        if (viewCountService.canIncrease("event", eventId, userId, dto.getAuthorId(), request, response)) {
            eventMapper.increaseViews(eventId);
            dto.setViews(dto.getViews() + 1);
        }

        dto.setEnd(dto.getEndDt().isBefore(LocalDate.now()));

        dto.setFiles(fileService.getFiles("EVENT", eventId, "CONTENT"));

        dto.setPrevSeq(eventMapper.selectPrevEventId(eventId, dto.getCategoryId()));
        dto.setNextSeq(eventMapper.selectNextEventId(eventId, dto.getCategoryId()));

        return dto;
    }

    // 게시글 작성
    @Transactional
    public int createEvent(EventCreateRequestDTO dto, Integer authorId, MultipartFile poster, MultipartFile thumbnail, MultipartFile banner, List<MultipartFile> contents) {
        Event event = new Event();
        event.setCategoryId(dto.getCategoryId());
        event.setTitle(dto.getTitle());
        event.setStartDt(dto.getStartDt());
        event.setEndDt(dto.getEndDt());
        event.setAnnCount(dto.getAnnCount());
        event.setAuthorId(authorId);

        eventMapper.insertEvent(event);

        int eventId = event.getEventId();

        fileService.save(poster, eventId, "EVENT", "POSTER");
        fileService.save(thumbnail, eventId, "EVENT", "THUMB");
        fileService.save(banner, eventId, "EVENT", "BANNER");
        fileService.save(contents, eventId, "EVENT", "CONTENT");

        return eventId;
    }

    // 게시글 수정
    @Transactional
    public void updateEvent(int eventId, EventUpdateRequestDTO dto,
                            MultipartFile newPoster, List<Integer> deletePosterIds,
                            MultipartFile newThumbnail, List<Integer> deleteThumbnailIds,
                            MultipartFile newBanner, List<Integer> deleteBannerIds,
                            List<MultipartFile> newFiles, List<Integer> deleteFileIds) {

        // 이벤트 기본 정보 수정
        Event event = new Event();
        event.setEventId(eventId);
        event.setCategoryId(dto.getCategoryId());
        event.setTitle(dto.getTitle());
        event.setStartDt(dto.getStartDt());
        event.setEndDt(dto.getEndDt());
        event.setAnnCount(dto.getAnnCount());

        eventMapper.updateEvent(event);

        // 파일 삭제 (기존 파일 유지)
        fileService.deleteFiles(deletePosterIds);
        fileService.deleteFiles(deleteThumbnailIds);
        fileService.deleteFiles(deleteBannerIds);
        fileService.deleteFiles(deleteFileIds);

        // 새 파일 추가
        fileService.save(newPoster, eventId, "EVENT", "POSTER");
        fileService.save(newThumbnail, eventId, "EVENT", "THUMB");
        fileService.save(newBanner, eventId, "EVENT", "BANNER");
        fileService.save(newFiles, eventId, "EVENT", "CONTENT");

    }

    // 게시글 삭제
    @Transactional
    public void deleteEvent(int eventId) {
        fileService.deleteByEntity("EVENT", eventId);
        eventMapper.deleteEvent(eventId);
    }


}
