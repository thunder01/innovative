package com.innovative.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.service.RoleService;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/role")
public class RoleController {
	

    @Autowired
    RoleService roleService;
  

    /**
     * 获取所有角色
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    public JsonResult getExpertList() {
        return new JsonResult(true, roleService.getRoleLists());
    }
    
    

}
