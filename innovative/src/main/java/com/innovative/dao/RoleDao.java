package com.innovative.dao;

import com.innovative.bean.Role;


import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleDao {


	//过去角色列表
	List<Role> getRoleList();

	boolean addRole(Role role);
	
	/**
	 * 获取所有角色
	 * @param pageNum
	 * @return
	 */
	List<Role> getRoleList(@Param("pageSize") int pageSize , @Param("startIndex") int startIndex);
	/**
	 * 角色总数
	 * @return
	 */
	int getTotalCount();


}
