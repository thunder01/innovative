package com.innovative.dao;


import java.util.List;
import java.util.Map;

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
		 * 根据用户名字获取用户信息及该用用领导层
		 * @param name 用户姓名
		 * @return
		 */
		 List<Map> getUserByName(@Param("name")String name);
		 
		 /**
			 * 根据工号字获取用户信息
			 * @param jobNumber  工号
			 * @return
			 */
		 User getUserByJobNumber(@Param("jobNumber")String jobNumber);
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
		 * @param keyword 
		 * @param roleName 
		 * @return
		 */
		List<User> getUserList(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize,  @Param("roleName")String roleName,  @Param("keyword")String keyword);
		/**
		 * 获取用户总条数
		 * @param keyword 
		 * @param roleName 
		 * @return 用户总条数
		 */
		int getTotalCount(@Param("roleName")String roleName, @Param("keyword")String keyword);
		/**
		 * 查看用户是否有用户角色记录
		 * @param userId
		 * @param roleId
		 * @return
		 */
		int getUserRole(@Param("userId")String userId, @Param("roleId")String roleId);
		/**
		 * 增加用户角色记录
		 * @param userId 用户id
		 * @param roleId 角色id
		 * @param string 
		 * @return
		 */
		Boolean insertUserRole(@Param("userId")String userId, @Param("roleId")String roleId, @Param("createBy") String createBy,@Param("sfqy") int sfqy);
		/**
		 * 检查是否有权限
		 * @param right  权限id
		 * @param userId  用户id
		 * @return
		 */
		boolean getUserRight(@Param("userId")String userId, @Param("right")String right);
		/**
		 * 增加用户权限
		 * @param userId 用户id
		 * @param right 权限id
		 * @return
		 */
		Boolean insertUserRight(@Param("userId")String userId, @Param("right")String right);
		/**
		 * 增加多个用户角色
		 * @param user
		 * @return
		 */
		boolean insertUserRoles(User user);
		/**
		 * 删除用户角色
		 * @param userId  用户id
		 */
		boolean deleteUserRoles(@Param("userId")String userId);
		
		/**
		 * 引用该角色的用户解除角色用户关系
		 * @param userId  用户id
		 */
		boolean deleteUserRolesByRoleId(@Param("roleId")String roleId);
		//根据员工号获取员工所在组织全称
		Map<String,String> getQcName(String pernr);
		/**
		 * 获取所有在职的用户
		 * @return
		 */
		List<User> getUsers();
		/**
		 * 根据用户信息获取用户角色信息
		 * @param userid
		 * @return
		 */
		User getUserroleMessessage(@Param("userId")String userid);
}



