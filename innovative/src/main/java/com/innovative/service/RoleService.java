package com.innovative.service;


import com.innovative.bean.Role;
import com.innovative.dao.RoleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

	public List<Role> getRoleLists() {
		// TODO Auto-generated method stub
		return roleDao.getRoleList();
	}

}
