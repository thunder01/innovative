package com.innovative.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Expert;

public interface PoiDao {



    //批量添加专家信息
    int addPoiExpert(Expert addExpert);

	int batchAddPoiExpert(@Param("urllist")List<Map<String, Object>> resultList);
	//批量增加行业协会
	int batchAddPoiAssociation(@Param("urllist")List<Map<String, Object>> resultList);

	//批量增加仪器设备
	int batchAddPoiEquipment(@Param("urllist")List<Map<String, Object>> resultList);
	
	//批量增加组织机构
	int batchAddPoiOrganization(@Param("urllist")List<Map<String, Object>> resultList);
	

	//批量增加方案
	int batchAddSolution(@Param("urllist")List<Map<String, Object>> resultList);
	
	
	//批量增加技术报告
	int batchAddTechnicalReports(@Param("urllist")List<Map<String, Object>> resultList);
	

	

}
