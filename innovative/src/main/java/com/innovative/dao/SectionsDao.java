package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Information;
import com.innovative.bean.Sections;

/**
 * 科技专栏
 * @author cj
 *
 */
public interface SectionsDao {
/**
 * 添加科技资讯
 * @param sections
 * @return
 */
	boolean addSection(Sections sections);
/**
 * 修改科技资讯
 * @param sections
 * @return
 */
	boolean updateSection(Sections sections);
	/**
	 * 根据id获取科技专栏
	 * @param id
	 * @return
	 */
    Sections getSectionById(@Param("id")String id);
    /**
     * 分页查询科技专栏
     * @param startIndex
     * @param pageSize
     * @param state 
     * @return
     */
	List<Sections> getSectionsLists(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize, @Param("state") String state);
	/**
	 * 科技专栏的总条数
	 * @param state 
	 * @return
	 */
	int getTotalCountNum(@Param("state")String state);
	/**
	 * 删除科技专栏
	 * @param id
	 * @return
	 */
	boolean deleteSection(@Param("id")String id);
	/**
	 * 审批功能，通过state置为1
	 * @param id
	 * @return
	 */
	boolean approvalSections(@Param("id")String id);
	/**
	 * 更新科技专栏的点击次数
	 * @param id 科技专栏id 
	 */
	void updateSectionApprouverNum(@Param("id")String id);
	/**
	 * 查询科技专栏
	 * @param id 科技专栏id
	 * @param userid  当前登录人
	 * @return
	 */
	Sections getSectionByIdAndUserid(@Param("id")String id,@Param("userid") String userid);
	/**
	 * 更新阅读量
	 * @param sections
	 * @return
	 */
	boolean countUpdate(Sections sections);

}
