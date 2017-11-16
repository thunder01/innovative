package com.innovative.service;


import com.innovative.bean.Role;
import com.innovative.dao.RoleDao;
import com.innovative.dao.RoleRightDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleRightDao roleRightDao;
    @Autowired
    private com.innovative.dao.RightDao RightDao;
    
    @Autowired
    private UserDao userdao;

	public Map<String, Object> getRoleLists(int pageNum) {
		// TODO Auto-generated method stub
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Role> role = roleDao.getRoleList( pageInfo.getPageSize(),pageInfo.getStartIndex());
        int totalCount = roleDao.getTotalCount();

        Map<String, Object> map = new HashMap<>();
        /*map.put("experts", experts);
        map.put("totalCount", totalCount);
        map.put("expertCount", experts.size());
        map.put("pageSize()", pageInfo.getPageSize());
        map.put("currentPageNum()", pageInfo.getCurrentPageNum());*/
        
        
        map.put("items", role);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
@Transactional
	public boolean addRole(Role role) {
		// TODO Auto-generated method stub
		role.setRoleId(Misc.uuid());
		roleRightDao.addRoleRight(role);
		return roleDao.addRole(role);
	}
	//给角色增加权限时先删除角色对应的权限
	@Transactional
	public boolean addRoleRight(Role role) {
		//删除角色之前的权限，从新给角色付权限
		roleRightDao.deleteRoleRight(role.getRoleId());
		return roleRightDao.addRoleRight(role);
	}
	/**
	 * 获取所有的rolename
	 * @return
	 */
	public List<Map<String, String>> getRoleNameList() {
		// TODO Auto-generated method stub
		return roleDao.getRoleNameList();
	}
	/**
	 * 修改角色权限
	 * @param role
	 * @return
	 */
	@Transactional
	public boolean updateRoleRight(Role role) {
		roleDao.updateRole(role);
		// 删除角色对应的权限
		roleRightDao.deleteRoleRight(role.getRoleId());
		return roleRightDao.addRoleRight(role);
	}
	/**
	 * 删除角色与角色对应的权限
	 * @param roleId
	 * @return
	 */
	@Transactional
	public boolean deleteRoleRight(String roleId) {
		// TODO Auto-generated method stub
		//删除角色应用的的权限
		roleRightDao.deleteRoleRight(roleId);
		//删除用户引用的角色
		userdao.deleteUserRolesByRoleId(roleId);
		//删除角色
		return roleDao.deleteRole(roleId);
	}
	/**
	 * 获取所有的角色对应的权限信息
	 * @param roleId
	 * @return
	 */
	public Map<String,Object> getRoleRight(String roleId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("role", roleDao.getRoleRight(roleId));
		map.put("rightList", RightDao.getRightlist());
		return map;
	}

}
