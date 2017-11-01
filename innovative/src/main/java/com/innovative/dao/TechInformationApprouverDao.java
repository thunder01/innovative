package com.innovative.dao;


import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Approuver;
import com.innovative.bean.TechInformationApprouver;

/**
 * 科技资讯点赞测试
 * @author cj
 *
 */


public interface TechInformationApprouverDao {


    /**
     * 增加点赞次数
     * @return
     */
	boolean insertTechInfornaionApprouver(TechInformationApprouver techInformationApprouver);

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
	Integer isTodayApprouverTechInfornaion(@Param("approuverBy")String approuverBy, @Param("informationId")String informationId);


}
