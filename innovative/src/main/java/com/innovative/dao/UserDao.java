package com.innovative.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.User;


public interface  UserDao {
		/**
		 * 根据id获取用户信息
		 * @param userId
		 * @return
		 */
		User  getUser(String userId);
		/**
		 * 根据用户名字获取用户信息
		 * @param name
		 * @return
		 */
		 List<User> getUserByName(@Param("name")String name);
		 /**
		  * 修改用户信息
		  * @param user
		  * @return
		  */
		boolean updateUser(User user);
		/**
		 * 获取列表用户信息
		 * @param startIndex
		 * @param pageSize
		 * @return
		 */
		List<User> getUserList(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

		int getTotalCount();
}

