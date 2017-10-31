package com.innovative.controller;
import com.innovative.bean.Technology;
import com.innovative.service.TechbologyService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/techbology")
public class TechbologyController {
    @Autowired
    TechbologyService service;

    /**
     * 添加内容
     * @param technology
     * @return
     */
    @RequestMapping(value = "/addTechnology")
    public JsonResult addTechnology(@RequestBody Technology technology){
        boolean code=false;
        String  message="添加失败!";
        if (service.addTechnology(technology)){
            code=true;
            message="添加成功";
        }
        return  new JsonResult(code,message);
    }

    /**
     * 科技资讯查询列表
     * @param offset
     * @param request
     * @return
     */
    public JsonResult getinformation(@RequestParam(name = "offset",defaultValue = "0") Integer offset,HttpServletRequest request){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=request.getSession();
        //User user= (User) session.getAttribute("userId");
        return new JsonResult(true,service.getinformation(page));
    }

    public  JsonResult getSpecial(@RequestParam(name = "offset",defaultValue = "0") Integer offset,HttpServletRequest request){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=request.getSession();
        //User user= (User) session.getAttribute("userId");
        return new JsonResult(true,service.getSpecial(page));
    }
}
