package com.ohseat.ohseatback.domain.common.dto;

import com.ohseat.ohseatback.domain.common.enums.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AdminPostBulkDeleteRequest {
    private BoardType boardType;
    private List<Integer> postIds;
}
