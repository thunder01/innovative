package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Collect;

/**
 * 征集的持久层接口
 * @author huang
 *
 */
public interface CollectDao {
	/**
	 * 添加征集
	 * @param collection
	 * @return
	 */
	public int saveCollection(Collect collect);
	/**
	 * 查询所有征集即全员征集
	 * @param startIndex  开始条数
	 * @param pageSize  	展示条数
	 * @return
	 */
	public List<Collect> findAllCollection(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	/**
	 * 查询征集总条数
	 * @return
	 */
	public int findTotal();
	/**
	 * 通过id查询征集详情
	 * @param id
	 * @return
	 */
	public Collect findCollectionById(Integer id);
}
