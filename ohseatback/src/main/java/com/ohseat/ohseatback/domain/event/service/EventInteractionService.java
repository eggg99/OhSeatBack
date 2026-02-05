package com.ohseat.ohseatback.domain.event.service;

import com.ohseat.ohseatback.domain.event.mapper.EventInteractionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventInteractionService {

    private final EventInteractionMapper interactionMapper;

    @Transactional
    public void like(Integer eventId, Integer userId) {
        if (interactionMapper.isEventLiked(eventId, userId) == 0){
            interactionMapper.insertEventLike(eventId, userId);
            interactionMapper.increaseEventLikeCount(eventId);
        }
    }

    @Transactional
    public void unlike(Integer eventId, Integer userId) {
        if (interactionMapper.isEventLiked(eventId, userId) > 0) {
            interactionMapper.deleteEventLike(eventId, userId);
            interactionMapper.decreaseEventLikeCount(eventId);
        }
    }

    public boolean isLiked(Integer eventId, Integer userId) {
        return interactionMapper.isEventLiked(eventId, userId) > 0;
    }

}
