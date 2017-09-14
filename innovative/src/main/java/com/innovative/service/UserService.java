package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovative.bean.Expert;
import com.innovative.bean.User;
import com.innovative.dao.UserDao;
import com.innovative.utils.PageInfo;

@Service
public class UserService {
	@Autowired
	UserDao userdao;
	/**
	 * 根据职位获取用户信息
	 * @param userpost
	 * @return
	 */
	public  List<User>  getUserList (String userpost){
		return null;
	}
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return userdao.getUser(userId);
	}
	public List<User> getUserByName(String name) {
		name = "%"+name+"%";
		return userdao.getUserByName(name);
	}
	public boolean updateUser(User user) {
		return userdao.updateUser(user);
	}
	public Map<String,Object> getUserLists(Integer pageNum) {
		  PageInfo pageInfo = new PageInfo();
	        pageInfo.setCurrentPageNum(pageNum);

	        List<User> users = userdao.getUserList( pageInfo.getStartIndex(), pageInfo.getPageSize());
	        int totalCount = userdao.getTotalCount();

	        Map<String, Object> map = new HashMap<>();
	        map.put("items", users);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
	        return map;
	}
	


}
