package com.innovative.dao;


import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Approuver;
import com.innovative.bean.Informationpush;

/**
 * 关于点赞测试
 * @author cj
 *
 */


public interface ApprouverDao {


    /**
     * 增加点赞次数
     * @return
     */
	boolean insertApprouver(Approuver approuver);

    /**
     * 获取最近的一次点赞
     * @return
     */
	Approuver getApprouverLatest(@Param("approuverBy")String approuverBy,@Param("startIndex")Integer startIndex);
	/**
	 * 今天是否点赞
	 * @param approuverBy
	 * @param comentId
	 * @return
	 */
	Integer isTodayApprouverInfornaionPush(@Param("approuverBy")String approuverBy, @Param("comentId")String comentId);
	/**
	 * 根据点赞id获取点赞的推特信息与点赞人信息（用于消息那块）
	 * @param id 点赞ID
	 * @param cookieValue
	 * @return 推特信息id
	 */
	Informationpush getApprouverByIdForMessage(@Param("id")String id, @Param("userid")String userid);


}
