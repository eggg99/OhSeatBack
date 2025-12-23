package com.ohseat.ohseatback.domain.notice.service;

import com.ohseat.ohseatback.domain.notice.dto.NoticeCreateRequest;
import com.ohseat.ohseatback.domain.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeMapper noticeMapper;

    @Transactional
    public void createNotice(NoticeCreateRequest request, Integer adminId) {
        noticeMapper.insertNotice(request.getTargetBoard(), adminId, request.getTitle(), request.getContent());
    }

    @Transactional
    public void deactiveNotice(Long noticeId) {
        noticeMapper.deactiveNotice(noticeId);
    }

}
