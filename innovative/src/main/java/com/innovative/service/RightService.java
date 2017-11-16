package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Right;
import com.innovative.bean.User;
import com.innovative.dao.RightDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

@Service
public class RightService {
	@Autowired
	RightDao rightDao;
	/**
	 * 增加一个模块的权限
	 * @param right
	 * @return
	 */
	public boolean addRight(Right right) {
		// TODO Auto-generated method stub
		right.setId(Misc.uuid());
		return rightDao.addRight(right);
	}
	/**
	 * 删除模块权限
	 * @param id
	 * @return
	 */
	public boolean deleteRight(String id) {
		// TODO Auto-generated method stub
		return rightDao.deleteRight(id);
	}
	/**
	 * 获取权限列表
	 * @return
	 */
	public List<Right> getRightlist() {
		// TODO Auto-generated method stub
		return rightDao.getRightlist();
	}


}
