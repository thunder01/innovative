package com.innovative.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Expert;
import com.innovative.bean.Role;
import com.innovative.service.RoleService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

@RestController
@RequestMapping("/role")
public class RoleController {
	

    @Autowired
    RoleService roleService;
  

    /**
     * 获取所有角色
     *
     * @return
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getRoleList(@RequestParam(name="offset",defaultValue="0" ) Integer offset) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, roleService.getRoleLists(page));
    }
    /**
     * 获取所有角色
     *
     * @return
     */
    @RequestMapping(value = "/getRoleNameList", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getRoleNameList() {
        return new JsonResult(true, roleService.getRoleNameList());
    }
    /**
     * 增加角色
     * @param role
     * @param req
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addRole(@RequestBody Role role,HttpServletRequest req) {
    	
    	role.setCreateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	role.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	if(!roleService.addRole(role)){
    		return new JsonResult(false, "新增失败，请重试！");
    	}
        return new JsonResult(true, "新增成功!");
    }
    /**
     * 给角色分权限
     * @param role
     * @param req
     * @return
     */
    @RequestMapping(value = "/addRoleRight", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addRoleRight(@RequestBody Role role,HttpServletRequest req) {
    	
    	role.setCreateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	role.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	if(!roleService.addRoleRight(role)){
    		return new JsonResult(false, "新增失败，请重试！");
    	}
        return new JsonResult(true, "新增成功!");
    }
    
    
    

}
