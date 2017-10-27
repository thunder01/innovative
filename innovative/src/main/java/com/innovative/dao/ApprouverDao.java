package com.innovative.dao;


import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Approuver;

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


}
