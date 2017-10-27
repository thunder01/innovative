package com.innovative.controller;


import com.innovative.bean.Information;
import com.innovative.bean.Sections;
import com.innovative.service.InformationService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 科技资讯
 * @author cj
 *
 */
@RestController
@RequestMapping("/information")
public class InformationController {


    @Autowired
    private InformationService informationService;

    /**
     * 增加科技资讯
     *
     * @param Sections 科技资讯实体
     * @return
     */
    @RequestMapping(value = "/addInformation", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult addInformation(@RequestBody Information information,HttpServletRequest req) {
    	if(information ==  null )
    		 return new JsonResult(false, "没有获取到实体，添加科技资讯失败！");
    	//设置创建人，修改人
    	information.setCreateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	information.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	boolean flag = informationService.addInformation(information);
    	return flag ? new JsonResult(true,"添加成功") : new JsonResult(false, "添加科技资讯失败！") ;
    }
    
    /**
     * 修改编辑科技资讯
     *
     * @param Sections 科技资讯实体
     * @return
     */
    @RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult updateInformation(@RequestBody Information information) {
    	if(information.getId() ==  null ||  information.getId().length() <= 0)
    		 return new JsonResult(false, "没有要修改的科技资讯!");
    	boolean flag = informationService.updateInformation(information);
    	return flag ? new JsonResult(true,"修改成功") : new JsonResult(false, "修改科技资讯失败！");
    }
    /**
     * 查询科技资讯
     *
     * @param Sections 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/getInformationComentById/{id}", method = RequestMethod.GET)
    public JsonResult getInformationById(@PathVariable(name = "id") String id) {
    	if(id ==  null ||  id.length() <= 0)
    		 return new JsonResult(false, "请传递要修改的科技资讯!");
    	Information information = informationService.getInformationById(id);
    	return  new JsonResult(true,information);
    }
    
    
    /**
     * 分页查询科技专栏
     *
     * @param Sections 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/getInformationList", method = RequestMethod.GET)
    public JsonResult getSectionList(@RequestParam(name="offset",defaultValue="0" ) Integer offset) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, informationService.getInformationLists(page));
    }
    
    /**
     * 根据id获取仪器设备
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/deleteInformation", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult deleteInformation(@RequestBody Sections sections) {

       boolean flag = informationService.deleteInformation(sections.getId());
        if (flag) {
            return new JsonResult(true, "已删除");
        }
        return new JsonResult(false, "参数不合法");
    }



}
