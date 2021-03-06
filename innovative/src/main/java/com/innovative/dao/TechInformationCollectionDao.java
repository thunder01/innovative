package com.innovative.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.CollectionPush;
import com.innovative.bean.TechInformationCollection;

/**
 * 科技资讯收藏
 * @author cj
 *
 */


public interface TechInformationCollectionDao {


    /**
     * 收藏
     * @return
     */
	boolean insertTechInformationCollection(TechInformationCollection collection);

	/**
	 * 用户今天是否收藏
	 * @param collectBy
	 * @param comentId
	 * @return
	 */
	Integer isCollectionTechInformation(@Param("collectBy")String collectBy, @Param("informationId")String informationId);
	
	
	TechInformationCollection getById(String id);
}
