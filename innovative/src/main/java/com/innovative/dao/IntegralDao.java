package com.innovative.dao;

import java.util.List;

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
	
	
	/**
	 * 获取当前角色的总积分
	 * @param userid
	 * @return
	 */
	public int getTotalCountMyIntegral(String userid);
	/**
	 * 获取用户当天的积分明细
	 * @param userid
	 * @return
	 */
	public List<Integral> getThisDayIntegralDetail(@Param("userid")String userid,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	public int getCountThisDay(String userid);
	/**
	 * 获取用户7天内的积分明细
	 * @param userid
	 * @return
	 */
	public List<Integral> getThisWeekIntegralDetail(@Param("userid")String userid,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	public int getCountThisWeek(String userid);
	/**
	 * 获取用户本月的积分明细
	 * @param userid
	 * @return
	 */
	public List<Integral> getThisMonthIntegralDetail(@Param("userid")String userid,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	public int getCountThisMonth(String userid);
	/**
	 * type=5的创新资源的当天的进入次数resource_id即uuid不会重复  
	 * @param resource_id
	 * @return
	 */
	public int getCountByResource_id(String resource_id);
}
