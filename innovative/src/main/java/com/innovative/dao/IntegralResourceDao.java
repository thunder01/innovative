package com.innovative.dao;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.IntegralResource;

public interface IntegralResourceDao {
	/**
	 * 插入一个创新资源详情页查看信息
	 * @param integralResource
	 * @return
	 */
	public int addIntegralResource(IntegralResource integralResource);
	/**
	 * 通过类型和资源id来定位资源信息 来查询当前资源被查看的次数
	 * @param resource_id
	 * @param type
	 * @return
	 */
	public int getCountByResourceId(String resource_id);
}
