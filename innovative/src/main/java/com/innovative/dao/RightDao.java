package com.innovative.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Right;

public interface  RightDao {
	/**
	 * 增加一个权限
	 * @param right
	 * @return
	 */
		boolean addRight(Right right);
		/**
		 * 删除用户权限
		 * @param id
		 * @return
		 */
		boolean deleteRight(@Param("id")String id);
		/**
		 * 获取权限列表
		 * @return
		 */
		List<Right> getRightlist();
}



