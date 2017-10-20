package com.innovative.dao;

import com.innovative.bean.MsgCount;

/**
 * 用户消息数存储
 * @author huang
 *
 */
public interface MsgCountDao {
	/**
	 * 增加一个用户消息，增加前先判断这个用户是否在这个表中存在
	 * @param msgCount Bean
	 * @return
	 */
	public int saveMsgCount(String userid);
	/**
	 * 用户存在的话就更新用户消息数
	 * @param msgCount Bean
	 * @return
	 */
	public int updateMsgCount(MsgCount msgCount);
	/**
	 * 通过用户id查询用户信息条数，即可知道该用户是否有新消息，读取之后更新
	 * @param userid 用户id
	 * @return
	 */
	public MsgCount showMsgCount(String userid);
	/**
	 * 判断是否存在   返回值0为不存在，返回值为1存在
	 * @param userid 用户id
	 * @return
	 */
	public int existMsgCount(String userid);
}
