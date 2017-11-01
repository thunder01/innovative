package com.innovative.dao;


import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Approuver;
import com.innovative.bean.TechInformationApprouver;
import com.innovative.bean.TechSectionsApprouver;

/**
 * 科技资讯点赞测试
 * @author cj
 *
 */


public interface TechSectionsApprouverDao {


    /**
     * 增加点赞次数
     * @return
     */
	boolean insertTechApprouver(TechSectionsApprouver techSectionsApprouver);

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
	Integer isTodayApprouverTechInfornaion(@Param("approuverBy")String approuverBy, @Param("sectionId")String sectionId);


}
