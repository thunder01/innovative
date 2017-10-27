package com.innovative.dao;

import com.innovative.bean.Integral;

/**
 * 积分模块持久层
 * @author huang
 *
 */
public interface IntegralDao {
	/**
	 * 用户第一次登入添加一个积分信息 且 初始积分为110
	 * @param integral
	 * @return
	 */
	public int addIntegral(Integral integral);
	/**
	 * 更新积分或者标签
	 * @param integral
	 * @return
	 */
	public int updateIntegral(Integral integral);
	/**
	 * 通过用户id来查询积分信息
	 * @param userid
	 * @return
	 */
	public Integral getIntegral(String userid);
}
