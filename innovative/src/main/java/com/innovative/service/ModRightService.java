package com.innovative.service;


import com.innovative.bean.ModRight;
import com.innovative.bean.Role;
import com.innovative.dao.ModRightDao;
import com.innovative.dao.RoleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModRightService {

    @Autowired
    private ModRightDao rightDao;

	/*public List<Role> getRoleLists() {
		// TODO Auto-generated method stub
		return rightDao.getRoleList();
	}
*/
	public List<ModRight> getModRightList(String type) {
		// TODO Auto-generated method stub
		return rightDao.getModRightByType(type);
	}

}
