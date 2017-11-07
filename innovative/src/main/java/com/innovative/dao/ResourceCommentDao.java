package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.ResourceComment;

/**
 * 创新资源评论的持久层
 * @author huang
 *
 */
public interface ResourceCommentDao {
	/**
	 * 添加一个评论
	 * @param resourceComment
	 * @return
	 */
	public int addResourceComment(ResourceComment resourceComment);
	/**
	 * 打赏一个评论
	 * @param resourceComment
	 * @return
	 */
	public int updateResourceComment(ResourceComment resourceComment);
	/**
	 * 通过类型和资源id来查询评论
	 * @param type
	 * @param resource_id
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ResourceComment> getResourceCommentByResourcd_idAndType(@Param("type")Integer type,@Param("resource_id")String resource_id,
			@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
	//查询资源的总数
	public int getCountResourceCommentByResourcd_idAndType(@Param("type")Integer type,@Param("resource_id")String resource_id);
	/**
	 * 获得当前资源的前5条评论
	 * @param type
	 * @param resource_id
	 * @return
	 */
	public List<ResourceComment> getComment(@Param("type")Integer type,@Param("resource_id")String resource_id);
	
	public ResourceComment getResourceComment(Integer id);
	
}
