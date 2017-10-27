package com.innovative.dao;

import com.innovative.bean.Informationpush;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationpushDao {



    /**
     * 根据id获取专家详情
     * @param id 专家id
     * @param userid 当前登录人
     * @return
     */
	Informationpush getInformationpush(@Param("id") String id,@Param("userid")String userid);




    /**
     * 专家列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    List<Informationpush> getInformationpushList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);



    /**
     * 查询推特信息的总条数
     * @param sectors 行业领域（多个用逗号隔开）
     * @return
     */
    int getTotalCount();



    /**
     * 添加专家信息
     * @param params 新增参数
     * @return
     */
    int addInformationpush(Map<String, Object> params);


    /**
     * 修改专家信息
     * @param expert 修改参数
     * @return
     */
    int updateInformationpush(Informationpush informationpush);




	int addInformationpush(Informationpush informationpush);



	/**
	 * 获取配置表中的合作状态对应的 name value  title rank 等信息 （这个方法需要挪动到 util 类中）
	 * @param cooperType  类型
	 * @param cooperationStatus  合作状态
	 */
	List<Map<String,Object>> getCodeAtatusList(@Param("cooperType")String cooperType, @Param("cooperationStatus")int cooperationStatus);




	/*List<Informationpush> getInformationpushLists(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);*/



/**
 * 查询一个用户的 评论总数
 * @param comentBy
 * @return
 */
	int getTotalCountNum(@Param("comentBy")String comentBy); 



	/**
	 * 删除id
	 * @param id 专家id
	 * @return
	 */
	boolean deleteInformationpush(String id);



	//增加点赞数量
	void updateApprouverNum(@Param("id")String id);



	//获取剩余的评价
	List<Informationpush> getInformationpushSurplus(Integer offset);



/**
 * 根据推特信息获取 评论（一个树结构）
 * @param pid
 * @return
 */
	List<Informationpush> getInformationpushListByPid(@Param("pid")String pid);



	//获取这个推特信息的点赞次数
	int  getCommenterNum(@Param("id") String id);
	
	/**
	 * 获取用户的评论总数
	 */
	
	int getComentTotalCount(@Param("comentBy")String comentBy);




	/**
	 * 查询用户分享的推特信息
	 * @param userid
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	List<Informationpush> getInformationpushPartagersByUserid(@Param("partagerBy")String partagerBy,@Param("limit") int limit, int startIndex);



	/**
	 * 根据用户id 获取当前用户的分享的推特信息条数
	 * @param userid
	 * @return
	 */
	Integer getInformationpushPartagersNumByUserid(@Param("userid")String userid);



	/**
	 * 获取评论的总条数
	 * @param userid
	 * @return
	 */
	Integer getInformationpushComentNumByUserid(@Param("userid")String userid);



	/**
	 * 根据用户id获取5条评论的推特信息
	 * @param userid
	 * @param pageSize
	 * @param startIndex
	 * @return 
	 */
	List<Informationpush> getInformationpushByComenterByUserId(@Param("comentBy")String comentBy, @Param("pageSize")int pageSize, @Param("startIndex")int startIndex);



	/**
	 * 根据用户id获取收藏的推特条数
	 * @param userid
	 * @return
	 */
	Integer getInformationpushCollectNumByUserid(@Param("userid")String userid);



	/**
	 * 用户收藏的推特信息
	 * @param userid
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	List<Informationpush> getInformationpushByCollectByUserId(@Param("collectBy")String collectBy, @Param("pageSize")int pageSize, @Param("startIndex")int startIndex);


	
	



}
