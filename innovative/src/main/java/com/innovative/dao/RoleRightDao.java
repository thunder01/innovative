package com.innovative.dao;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Role;

public interface RoleRightDao {

	/**
	 * 增加角色对应的权限
	 * @param role
	 * @return 
	 */
	boolean addRoleRight(Role role);
	/**
	 * 删除角色对用的权限
	 * @param roleId
	 */
	void deleteRoleRight(@Param("roleId")String roleId);


}
