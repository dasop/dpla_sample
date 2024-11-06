package com.kt.mddp.sample.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kt.mddp.sample.api.domain.Test;

@Mapper
public interface ApiTestMapper {

	void insertVo(Test test);
}
