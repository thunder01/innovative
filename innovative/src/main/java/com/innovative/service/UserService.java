package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.innovative.bean.User;
import com.innovative.dao.UserDao;
import com.innovative.utils.Config;
import com.innovative.utils.PageInfo;
import com.innovative.utils.SerializeUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class UserService {
	@Autowired
	UserDao userdao;
	//注入redis数据库连接池
	@Autowired
	JedisCluster jedisCluster;
	@Autowired
	TransportClient client;
	
	/**
	 * 根据职位获取用户信息
	 * @param userpost
	 * @return
	 */
	public  List<User>  getUserList (String userpost){
		return null;
	}
	
	/**
	 * 根据用户id获取用户信息,先从缓存中中查，再从数据库中查
	 * @param userId
	 * @return
	 */
	public User getUser(String userId) {
		//引入序列化工具
		SerializeUtil serializeUtil=SerializeUtil.getInstance();
		User user = null;
		if (userId!=null) {
			//redis数据库中存储的内容
			String serStr=jedisCluster.get(userId);
			//反序列化
			user=(User)serializeUtil.objectDeserialization(serStr);
			//判断从redis中查的结果是否为空，为空再到数据库中查询
			if (user==null) {
				user=userdao.getUser(userId);
				//若结果不为空，再加进缓存中
				if (user!=null) {
					//序列化
					serStr=serializeUtil.objectSerialiable(user);
					jedisCluster.set(userId, serStr);
				}
			}
		}
			
		return user;
	}
	
	/**
	 * 根据用户的姓、名查找用户信息，使用
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getUserByName(String name) {
		//先从索引库查
		List<Map> listUser = Lists.newArrayList();
		SearchRequestBuilder qBuilder=client.prepareSearch("user_index").setTypes("user");  
		QueryBuilder qb = QueryBuilders.disMaxQuery()
							.add(QueryBuilders.matchQuery("username", name))
							.add(QueryBuilders.wildcardQuery("itcode", "*"+name+"*"))
							.boost(1.2f)                             
						    .tieBreaker(0.7f);
		qBuilder.setQuery(qb);
		SearchResponse response = qBuilder.execute().actionGet();  
		SearchHits hits = response.getHits();
		for(SearchHit hit:hits){
			listUser.add(hit.getSource());
		}
		System.out.println("数量"+listUser.size());
		//若是从索引库中没有查到结果，则到数据库再查一遍
		if (listUser.size()==0) {
			name = "%"+name+"%";
			listUser = userdao.getUserByName(name);
		}
		
		return listUser;
	}
	
	
	public boolean updateUser(User user) {
		return userdao.updateUser(user);
	}
	/**
	 * 用户分页查询
	 * @param pageNum
	 * @param keyword 
	 * @param roleName 
	 * @return
	 */
	public Map<String,Object> getUserLists(Integer pageNum, String roleName, String keyword) {
		  PageInfo pageInfo = new PageInfo();
	        pageInfo.setCurrentPageNum(pageNum);
	        keyword = "%"+keyword+"%";
	        List<User> users = userdao.getUserList( pageInfo.getStartIndex(), pageInfo.getPageSize(),roleName,keyword);
	        int totalCount = userdao.getTotalCount(roleName,keyword);
      
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
		return userdao.insertUserRole(user.getUserId(),user.getRoleId(),user.getCreateBy(),user.getSfqy());
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
		//组织编号
		String orgeh = (String)map.get("orgeh");
		
		sidmap.put("xpmobs_sid", Config.xpmobs_sidmap.get(orgeh));
		if(null == qc || "".equals(qc)){
			return sidmap;
		//成员公司
		}else if((qc.indexOf("石家庄燃气")>0 || qc.indexOf("青岛燃气")>0 || qc.indexOf("廊坊燃气")>0)){
			 Config.cygsmap.put("xpmobs_sid", Config.xpmobs_sidmap.get(orgeh));
			 return Config.cygsmap;
		//健康研究院
		}else if((qc.indexOf("健康研究院")>0)){
			 Config.jkyjymap.put("xpmobs_sid", Config.xpmobs_sidmap.get(orgeh));
			 return Config.jkyjymap;
		//研发项目管理系统（产业层）
		}else if(qc.indexOf("能源控股")>0 || qc.indexOf("新智云数据服务有限公司")>0 || qc.indexOf("技术工程")>0){
			Config.cycmap.put("xpmobs_sid", Config.xpmobs_sidmap.get(orgeh));
			return Config.cycmap;
		//石墨烯
		}else if((qc.indexOf("石墨烯")>0)){
			Config.smxmap.put("xpmobs_sid", Config.xpmobs_sidmap.get(orgeh));
			return Config.smxmap;
		}
		return sidmap;
			
	}
	
	/**
	 * 获取所有的在职员工
	 * @return
	 */
	public List<User> getUsers() {
		List<User> listUser=userdao.getUsers();	
		return listUser;
	}
/**
 * 更新用户角色
 * @param user
 * @return
 */
	@Transactional
	public User updateUserRole(User user) {
		//删除之前的用户角色
		userdao.deleteUserRoles(user.getUserId());
		//给用户赋值新的角色
		userdao.insertUserRole(user.getUserId(),user.getRoleId(),user.getUpdateBy(),user.getSfqy());
		//从新获取新的用户
		return userdao.getUser(user.getUserId());
	}
/**
 * 获取用户角色信息(目前一个用户只有一个角色)
 * @param id
 * @return
 */
public User getUserroleMessessage(String userid) {
	// TODO Auto-generated method stub
	return userdao.getUserroleMessessage(userid);
}
	/**
	 * 删除用户角色信息
	 * @param userid
	 * @return
	 */
	public boolean deleteUserRole(String userid) {
		// TODO Auto-generated method stub
		return userdao.deleteUserRoles(userid);
		
	}
}
