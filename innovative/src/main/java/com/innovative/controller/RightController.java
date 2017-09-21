package com.innovative.controller;


import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Right;
import com.innovative.service.RightService;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/right")
public class RightController {
	

    @Autowired
    RightService rightService;
  

    /**
     * 增加模块权限
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/addRight", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addRight(@RequestBody Right right) {
    	if(!rightService.addRight(right)){
    		return new JsonResult(false, "请检查您的数据");
    	}
        return new JsonResult(true, "添加成功！");
    }
    
    /**
     * 获取所有角色
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/deleteRight/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult deleteRight(@PathVariable(name ="id") String id) {
    	if(!rightService.deleteRight(id)){
    		return new JsonResult(false, "请检查数据！");
    	}
        return new JsonResult(true, "删除成功！");
    }
    
    
    

}
