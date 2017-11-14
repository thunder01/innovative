package com.innovative.service;


import com.innovative.bean.Role;
import com.innovative.dao.RoleDao;
import com.innovative.dao.RoleRightDao;
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

	public List<Role> getRoleLists(int pageNum) {
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
		return roleDao.getRoleList();
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
	public List<String> getRoleNameList() {
		// TODO Auto-generated method stub
		return roleDao.getRoleNameList();
	}

}
