package com.innovative.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Expert;
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
	    public JsonResult getExpert(@PathVariable(name = "id") String id) {
	    
		 if(id == null || "".equals(id))
			 return new JsonResult(false, "参数不合法");
			User user = userService.getUser(id);
		        if (user != null) {
		            return new JsonResult(true, user);
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
		        if (user != null) {
		            return new JsonResult(true, user);
		        }
		       return new JsonResult(false, "参数不合法");
	 }
	 
	 /**
	   * 根据用户id 修改用户
	   * @param expert 用户对象
	   * @param req
	   * @return
	   */
	    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResult updateUser(@RequestBody User user,HttpServletRequest req) {

	        if("".equals(user.getUserId())||userService.getUser(user.getUserId()) == null){
	            return new JsonResult(false, "此用户不存在!");
	        }
	        user.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
	        
	        return userService.updateUser(user) ?   new JsonResult(true, "修改成功！") : new JsonResult(false, "修改失败！");
	    }
	    
	    /**
		   * 新增用户角色权限角色
		   * @param User 用户对象
		   * @param roleId 角色id
		   *  @param rights 权限id
		   * @param req
		   * @return
		   */
		    @RequestMapping(value = "/addUserRoleAndUserRight", method = RequestMethod.POST)
		    @ResponseBody
		    public JsonResult addUserRoleAndUserRight(@RequestParam(name = "userId") String userId,@RequestParam("roleId")String roleId,@RequestParam("rightId")String[] rights ,HttpServletRequest req) {

		    	boolean falg = userService.addUserRoleAndUserRight(userId,roleId,rights);
		        
		        return falg ?   new JsonResult(true, "授权成功！") : new JsonResult(false, "授权失败！");
		    }
	    
	    
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


}
