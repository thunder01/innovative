package com.innovative.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.innovative.bean.User;
import com.innovative.service.UserService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

@RestController
@RequestMapping("/user")
public class UserController {
	
	 @Autowired
	    UserService userService;
	 /**
	  * 根据用户id获取用户信息
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
	    public JsonResult getUser(@PathVariable(name = "id") String id) {
		 if(id == null || "".equals(id))
			 return new JsonResult(false, "参数不合法");
			User user = userService.getUser(id);
		        if (user != null) {
		            return new JsonResult(true, user);
		        }
		       return new JsonResult(false, "参数不合法");
	 }
	 /**
	  * 根据用户id获取sid
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getSidByPernr/{id}", method = RequestMethod.GET)
	    public JsonResult getSidByPernr(@PathVariable(name = "id") String id) {
	    
		 if(id == null || "".equals(id))
			 return new JsonResult(false, "参数不合法");
			Map<String,String> sid = userService.getSidById(id);
		       return new JsonResult(true, sid);
	 }
	 /**
	  * 查看用户是否有该角色
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getUserRole/{userId}/{roleId}", method = RequestMethod.GET)
	    public JsonResult getUserRole(@PathVariable(name = "userId") String userId,@PathVariable(name = "roleId") String roleId) {
	    
		 if(userId == null || "".equals(userId))
			 return new JsonResult(false, "参数不合法");
			boolean flag = userService.getUserRole(userId,roleId);
		        if (flag == true) {
		            return new JsonResult(true, "该用户拥有该角色！");
		        }
		       return new JsonResult(false, "参数不合法");
	 }
	 /**
	  * 根据名字获取专家信息及上级的名字
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getUserByName/{name}", method = RequestMethod.GET)
	    public JsonResult getExpertByName(@PathVariable(name = "name") String name) {
	    
			List<User> user = userService.getUserByName(name);
			List<String> list = new ArrayList<String>();
		        if (user != null) {
		        	for(User u: user){
		        		list.add("username:"+u.getUserName()+";userpost:"+u.getStext()+";userdepart:"+u.getDstext());
		        	}
		        	System.out.println(JSON.toJSONString(JSON.toJSON(new JsonResult(true, user)),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue));
		        	/*JSON.toJSONString(user,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue)*/
		        	return new JsonResult(true, user);
		        }
		       return  new JsonResult(false, "获取失败");
	 }
	 
	 /**
	   * 根据用户id 修改用户
	   * @param expert 用户对象
	   * @param req
	   * @return
	   */
	   /* @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResult updateUser(@RequestBody User user,HttpServletRequest req) {

	        if("".equals(user.getUserId())||userService.getUser(user.getUserId()) == null){
	            return new JsonResult(false, "此用户不存在!");
	        }
	        user.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
	        
	        return userService.updateUser(user) ?   new JsonResult(true, "修改成功！") : new JsonResult(false, "修改失败！");
	    }*/
	    
	    
	    /**
	     * 用户列表页
	     *
	     * @return
	     */
	    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	    public JsonResult getUserList(@RequestParam(name="offset",defaultValue="0" ) Integer offset) {
	    	Integer page = offset/(new PageInfo().getPageSize()) +1;
	        return new JsonResult(true, userService.getUserLists(page));
	    }
	    /**
	     * 用户列表页
	     *
	     * @return
	     */
	    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	    public JsonResult getUsers() {
	    	List<User> list = userService.getUsers();
	    	System.out.println(list);
	        return new JsonResult(true, list);
	    }
	    /**
	     * 给用户分角色
	     * @param role
	     * @param req
	     * @return
	     */
	    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResult addUserRole(@RequestBody User user,HttpServletRequest req) {
	    	
	    	user.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
	    	user.setCreateBy(CookiesUtil.getCookieValue(req,"user_name"));
	    	//看这个用户是否有这个角色有的话返回false 
	    	if(userService.getUserRole(user.getUserId(), user.getRoleId())){
	    		return new JsonResult(false, "该用户角色已存在！不能重复添加！");
	    	}
	    	if(!userService.addUserRole(user)){
	    		return new JsonResult(false, "新增失败，请重试！");
	    	}
	        return new JsonResult(true, "新增成功!");
	    }

	    /**
	     * 给用户分多个角色
	     * @param role
	     * @param req
	     * @return
	     */
	    @RequestMapping(value = "/addUserRoles", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResult addUserRoles(@RequestBody User user,HttpServletRequest req) {
	    	
	    	user.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
	    	if(!userService.addUserRoles(user)){
	    		return new JsonResult(false, "新增失败，请重试！");
	    	}
	        return new JsonResult(true, "新增成功!");
	    }

}
