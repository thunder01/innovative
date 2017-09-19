package com.innovative.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.ModRight;
import com.innovative.service.ModRightService;
import com.innovative.service.RoleService;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/modright")
public class ModRightController {
	

    @Autowired
    ModRightService rightService;
  

    /**
     * 获取所有权限
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getMOdRightList", method = RequestMethod.GET)
    public JsonResult getRightList(@RequestParam(name="type" ,required = false) String type) {
    	List<ModRight> modRightlist =  rightService.getModRightList(type);
    	
        return modRightlist.size()>0 ? new JsonResult(true, modRightlist) : new JsonResult(false, "查询失败!") ;
    }
    
    
    

}
