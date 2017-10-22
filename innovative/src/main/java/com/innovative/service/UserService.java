package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.User;
import com.innovative.dao.UserDao;
import com.innovative.utils.Config;
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
	public List<Map> getUserByName(String name) {
		name = "%"+name+"%";
		return userdao.getUserByName(name);
	}
	public boolean updateUser(User user) {
		return userdao.updateUser(user);
	}
	/**
	 * 用户分页查询
	 * @param pageNum
	 * @return
	 */
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
	/**
	 * 给用户付一个权限
	 * @param user 用户对象
	 * @return
	 */
	public boolean addUserRole(User user) {
		// TODO Auto-generated method stub
		return userdao.insertUserRole(user.getUserId(),user.getRoleId(),user.getCreateBy());
	}
	/**
	 * 获取一个用户的的莫个角色
	 * @param userId
	 * @return
	 */
	public boolean getUserRole(String userId,String roleId) {
		return userdao.getUserRole(userId, roleId)>=1? true : false;
	}
	/**
	 * 给用户添加多个角色
	 * @param user
	 * @return
	 */
	public boolean addUserRoles(User user) {
		//删除用户所有的角色
		userdao.deleteUserRoles(user.getUserId());
		
		return userdao.insertUserRoles(user);
	}
	//获取用户组织id
	public Map<String,String> getSidById(String id) {
		Map<String,String> sidmap = new HashMap<String,String>();
		Map<String,String> map =  userdao.getQcName(id);
		if(null==map){
			return sidmap;
		}
		String qc = (String)map.get("path");
		if(null == qc || "".equals(qc)){
			return sidmap;
		//成员公司
		}else if((qc.indexOf("石家庄燃气")>0 || qc.indexOf("青岛燃气")>0 || qc.indexOf("廊坊燃气")>0)){
			return  Config.cygsmap;
		//健康研究院
		}else if((qc.indexOf("健康研究院")>0)){
			return Config.jkyjymap;
		//研发项目管理系统（产业层）
		}else if(qc.indexOf("能源控股")>0 || qc.indexOf("新智云数据服务有限公司")>0 || qc.indexOf("技术工程")>0){
			return Config.cycmap;
		//石墨烯
		}else if((qc.indexOf("石墨烯")>0)){
			return Config.smxmap;
		}else{
			return null;
		
		}
			
	}
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userdao.getUsers();
	}


}
