package com.innovative.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Expert;

public interface PoiDao {



    //批量添加专家信息
    int addPoiExpert(Expert addExpert);

	int batchAddPoiExpert(@Param("urllist")List<Map<String, Object>> resultList);




}
