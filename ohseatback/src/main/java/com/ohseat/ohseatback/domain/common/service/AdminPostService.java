package com.ohseat.ohseatback.domain.common.service;

import com.ohseat.ohseatback.domain.cinesquare.service.CineSquareService;
import com.ohseat.ohseatback.domain.common.enums.BoardType;
import com.ohseat.ohseatback.domain.event.service.EventAnnService;
import com.ohseat.ohseatback.domain.event.service.EventService;
import com.ohseat.ohseatback.domain.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPostService {

    private final CineSquareService cineSquareService;
    private final RecommendService recommendService;
    private final EventService eventService;
    private final EventAnnService eventAnnService;

    @Transactional
    public void bulkDelete(BoardType boardType, List<Integer> postIds) {

        if (postIds == null || postIds.isEmpty()) return;

        switch(boardType) {
            case CINESQUARE -> postIds.forEach(cineSquareService::deletePostWithFiles);

            case RECOMMEND -> postIds.forEach(recommendService::deletePost);

            case EVENT -> postIds.forEach(eventService::deleteEvent);

            case EVENTANNOUNCEMENT -> postIds.forEach(eventAnnService::delete);
        }
    }
}
