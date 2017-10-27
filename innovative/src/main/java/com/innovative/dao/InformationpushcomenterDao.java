package com.innovative.dao;

import com.innovative.bean.Informationpushcomenter;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationpushcomenterDao {






	/**
	 * 根据信息推特id获取该推特信息的所有的评论
	 * @param pushId 信息推特id
	 * @return
	 */
	List<Informationpushcomenter> getInformationpushcomenterByPushId(@Param("pushId")String pushId);
	/**
	 * 删除评论
	 * @param id 评论id
	 * @return
	 */
	boolean deleteInformationpushcoment(@Param("id")String id);
	/**
	 * 增加一条评论或者回复
	 * @param informationpushcomenter
	 * @return
	 */
	boolean addInformationpushcomenter(Informationpushcomenter informationpushcomenter);
	/**
	 * 根据评论id获取评论
	 * @param id
	 * @return
	 */
	Informationpushcomenter getformationpushcomenter(@Param("id")String id);
	/**
	 * 修改评论信息
	 * @param informationpushcomenter
	 * @return
	 */
	boolean updateInformationpushcomenter(Informationpushcomenter informationpushcomenter);
	/**
	 * 用户评论推特信息，分页查找
	 * @param comentBy 评论人
	 * @param startIndex
	 * @param pageSize
	 * @return 返回推特信息
	 */
	List<Map<String, Object>> getInformationPushComentersByUserid(@Param("comentBy")String comentBy, @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	/**
	 * 用户评论的推特信息数
	 * @param comentBy
	 * @return
	 */
	Integer getTotalCountByUserId(@Param("comentBy") String comentBy);












}
