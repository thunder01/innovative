package com.innovative.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.CollectionPush;
import com.innovative.bean.TechSectionsCollection;

/**
 * 科技专栏收藏
 * @author cj
 *
 */


public interface TechSectionsCollectionDao {


    /**
     * 收藏
     * @return
     */
	boolean insertTechSectionsCollection(TechSectionsCollection collection);

	/**
	 * 用户是否收藏科技专题内容
	 * @param collectBy
	 * @param informationId 科技资讯id
	 * @return
	 */
	Integer isCollectionSections(@Param("collectBy")String collectBy, @Param("sectionId")String sectionId);
	
	
	TechSectionsCollection getById(String id);

}
