package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Information;

/**
 * 科技专栏
 * @author cj
 *
 */
public interface InformationDao {
/**
 * 添加科技资讯
 * @param sections
 * @return
 */
	boolean addInformation(Information information);
/**
 * 修改科技资讯
 * @param sections
 * @return
 */
	boolean updateInformation(Information information);
	/**
	 * 根据id获取科技专栏
	 * @param id
	 * @param userid 
	 * @return
	 */
	Information getInformationById(@Param("id")String id, @Param("userid")String userid);
    /**
     * 分页查询科技专栏
     * @param startIndex
     * @param pageSize
     * @param state 
     * @return
     */
	List<Information> getInformationLists(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize,@Param("state") String state);
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
	boolean deleteInformation(@Param("id")String id);
	/**
	 * 审批功能，通过就把state置为1
	 * @param id
	 * @return
	 */
	boolean approvalInformation(@Param("id")String id);
	/**
	 * 给科技资讯增加点赞次数
	 * @param informationId
	 */
	boolean  updateInformationApprouverNum(@Param("id") String id);

}
