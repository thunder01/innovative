package com.innovative.dao;

import java.util.List;
import java.util.Map;

import com.innovative.bean.Expert;

public interface PoiDao {



    //批量添加专家信息
    int addPoiExpert(Expert addExpert);

	int batchAddPoiExpert(List<Map<String, Object>> resultList);




}
