package com.innovative.dao;

import com.innovative.bean.InformationPushPartager;
import com.innovative.bean.Informationpush;
import com.innovative.bean.Informationpushcomenter;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationPushPartagerDao {






	/**
	 * 根据信息推特id获取该推特信息的所有的评论
	 * @param pushId 信息推特id
	 * @return
	 *//*
	List<Informationpushcomenter> getInformationpushcomenterByPushId(@Param("pushId")String pushId);
	*//**
	 * 删除评论
	 * @param id 评论id
	 * @return
	 *//*
	boolean deleteInformationpushcoment(@Param("id")String id);
	*//**
	 * 增加一条评论或者回复
	 * @param informationpushcomenter
	 * @return
	 *//*
	boolean addInformationpushcomenter(Informationpushcomenter informationpushcomenter);
	*//**
	 * 根据评论id获取评论
	 * @param id
	 * @return
	 *//*
	Informationpushcomenter getformationpushcomenter(@Param("id")String id);
	*//**
	 * 修改评论信息
	 * @param informationpushcomenter
	 * @return
	 *//*
	boolean updateInformationpushcomenter(Informationpushcomenter informationpushcomenter);*/
	
	
	/**
	 * 根据推特信息id获取所有的分享记录
	 * @param pushId 推特信息id
	 * @return
	 */
/*	List<InformationPushPartager> getInformationpushPartagersByPushid(@Param("pushId")String pushId);*/
	/**
	 * 增加一条分享记录
	 * @param informationPushPartager
	 * @return
	 */
	boolean addInformationPushPartager(InformationPushPartager informationPushPartager);
	/**
	 * 用户今天是否分享过推特信息
	 * @param pushId
	 * @param partagerBy
	 * @return
	 */
	Integer isTodayPartagerInfornaionPush(@Param("pushId")String pushId, @Param("partagerBy")String partagerBy);
	/**
	 * 获取用户分享的推特信息
	 * @param partagerBy
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Map<String,Object>> getInformationpushPartagersByUserid(@Param("partagerBy")String partagerBy, @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	/**
	 * 用户分享的总条数
	 * @param partagerBy
	 * @return
	 */
	Integer getTotalCountByUserId(@Param("partagerBy")String partagerBy);
	/**
	 * 获取分享一条推特信息的分享记录
	 * @param pushId
	 * @return
	 */
	List<InformationPushPartager> getInformationpushPartagersByPushid(String pushId);
	/**
	 * 根据收藏id获得收藏的推特id
	 * @param id 收藏的推特信息的id
	 * @param userid  推特信息的发布者
	 * @return
	 */
	Informationpush getInformationpushPartagersByIdForMessage(@Param("id")String id, @Param("userid")String userid);
	












}
