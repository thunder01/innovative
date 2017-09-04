package com.innovative.utils;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CodeItemUtil {


    
	/**
	 * 获取配置表中的合作状态对应的 name value  title rank 等信息 （这个方法需要挪动到 util 类中）
	 * @param type  类型
	 * @param code  合作状态
	 */
	 List<Map<String,Object>> getCodeItemList(@Param("type")String type, @Param("code")int code);


}
