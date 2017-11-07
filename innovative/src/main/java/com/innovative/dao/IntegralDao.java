package com.innovative.dao;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Integral;

/**
 * 积分模块持久层
 * @author huang
 *
 */
public interface IntegralDao {
	/**
	 * 添加积分信息
	 * @param integral
	 * @return
	 */
	public int addIntegral(Integral integral);
	/**
	 * 通过用户id来查询积分信息
	 * @param userid
	 * @return
	 */
	public int getIntegralByUserid(String userid);
	/**
	 * 判断用户是否是首次登入
	 * @param userid
	 * @return
	 */
	public int isFirstLogin(String userid);
	/**
	 * 判断当天的登入次数
	 * @param userid
	 * @return
	 */
	public int todayLoginCount(String userid);
	/**
	 * 查询信息推特的收藏、转发、评论、点赞的总数
	 * @param type
	 * @param resource_id
	 * @return
	 */
	public int getCountBySzpd(@Param("type")int type,@Param("resource_id")String resource_id);
	
}
