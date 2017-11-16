package com.innovative.dao;

import com.innovative.bean.Role;


import java.util.List;
import java.util.Map;

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
	/**
	 * 获取roleName
	 * @return
	 */
	List<Map<String, String>> getRoleNameList();
	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	boolean deleteRole(@Param("roleId")String roleId);
	/**
	 * 获取角色对应的权限信息
	 * @param roleId
	 * @return
	 */
	Role getRoleRight(@Param("roleId")String roleId);
	//修改角色
	boolean updateRole(Role role);


}
