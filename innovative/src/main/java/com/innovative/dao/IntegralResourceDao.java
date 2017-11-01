package com.innovative.dao;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.IntegralResource;

public interface IntegralResourceDao {
	/**
	 * 添加一个创新资源详情页进入次数（第一次进来为1）
	 * @param integralResource
	 * @return
	 */
	public int addIntegralResource(IntegralResource integralResource);
	/**
	 * 更新一个创新资源详情页进入次数
	 * @param integralResource
	 * @return
	 */
	public int updateIntegralResource(IntegralResource integralResource);
	/**
	 * 查询资源的访问数量，先查询，访问数量为4则增加20积分，其访问数量置0
	 * @param type 类型判断是哪个资源
	 * @param resource_id 资源的id
	 * @return
	 */
	public IntegralResource getIntegralResource(@Param("type") String type,@Param("resource_id") String resource_id);
}
