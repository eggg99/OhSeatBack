package com.ohseat.ohseatback.repository;

import com.ohseat.ohseatback.domain.Recommend;
import com.ohseat.ohseatback.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendRepository {

    Recommend getArea();

}
