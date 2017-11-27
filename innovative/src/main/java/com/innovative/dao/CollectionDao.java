package com.innovative.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.CollectionPush;
import com.innovative.bean.Informationpush;

/**
 * 信息推特收藏记录
 * @author cj
 *
 */


public interface CollectionDao {


    /**
     * 增加点赞次数
     * @return
     */
	boolean insertCollection(CollectionPush collection);

    /**
     * 最近收藏的记录5条记录
     * @return
     */
	List<Map<String,String>> getCollectionLatest(@Param("collectBy")String collectBy,@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
	
	/**
     * 获取一个人所有的收藏
	 * @param pageSize  一页5条数据
	 * @param startIndex 
     * @return
     */
	List<Map<String,Object>> getCollectionlist(@Param("collectBy")String collectBy, @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	
	 /**
     * 删除收藏
     * @return
     */
	boolean deleteCollection(@Param("id")String id);
	//获取用户收藏总数
	int getTotalCountByUser(@Param("collectBy")String collectBy);
	/**
	 * 用户今天是否收藏
	 * @param collectBy
	 * @param comentId
	 * @return
	 */
	Integer isTodayCollectionInfornaionPush(@Param("collectBy")String collectBy, @Param("comentId")String comentId);
/**
 * 主要用于收藏（一条推特信息收藏的数）
 * @param comentId 推特信息id
 * @return
 */
	Integer getCollectNum(@Param("comentId")String comentId);
	/**
	 * 获取收藏当前登陆人的推特信息被收藏的记录
	 * @param id 收藏id
	 * @param userid  当前登陆人
	 * @return
	 */
	Informationpush getCollectInformationForMessage(@Param("id")String id, @Param("userid")String userid);

	/**
	 * 删除收藏的推特信息
	 * @param id 收藏id
	 * @return
	 */
	boolean deleteCollectInformationpush(@Param("id")String id);
	

}
