package com.innovative.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	  * 根据名字获取专家信息
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
	   * 根据专家id 修改专家
	   * @param expert 专家对象
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
