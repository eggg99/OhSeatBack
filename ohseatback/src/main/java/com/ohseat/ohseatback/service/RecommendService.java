package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.Recommend;
import com.ohseat.ohseatback.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    public Recommend getCinemaList(Integer multiplexId, Integer areaId) {
        return recommendRepository.getCinemaList(multiplexId, areaId);
    }
}
