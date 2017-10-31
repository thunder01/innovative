package com.innovative.controller;

import com.innovative.bean.Sections;
import com.innovative.service.SectionsService;
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
 * 科技专栏
 * @author cj
 *
 */
@RestController
@RequestMapping("/sections")
public class SectionsController {


    @Autowired
    private SectionsService sectionsService;

    /**
     * 增加科技专栏
     *
     * @param Sections 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult addSection(@RequestBody Sections sections,HttpServletRequest req) {
    	if(sections ==  null )
    		 return new JsonResult(false, "没有获取到实体，添加科技资讯失败！");
    	//设置创建人，修改人
    	sections.setCreateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	sections.setUpdateBy(CookiesUtil.getCookieValue(req,"user_name"));
    	boolean flag = sectionsService.addSection(sections);
    	return flag ? new JsonResult(true,"添加成功") : new JsonResult(false, "添加科技资讯失败！") ;
    }
    
    /**
     * 修改编辑科技专栏
     *
     * @param Sections 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/updateSection", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult updateSection(@RequestBody Sections sections) {
    	if(sections.getId() ==  null ||  sections.getId().length() <= 0)
    		 return new JsonResult(false, "没有要修改的科技资讯!");
    	boolean flag = sectionsService.updateSection(sections);
    	return flag ? new JsonResult(true,"修改成功") : new JsonResult(false, "修改科技资讯失败！");
    }
    /**
     * 查询科技资讯
     *
     * @param id 科技专栏id
     * @return
     */
    @RequestMapping(value = "/getSectionById/{id}", method = RequestMethod.GET)
    public JsonResult getSectionById(@PathVariable(name = "id") String id) {
    	if(id ==  null ||  id.length() <= 0)
    		 return new JsonResult(false, "请传递要修改的科技资讯!");
    	Sections sections = sectionsService.getSectionById(id);
    	return  new JsonResult(true,sections);
    }
    
    
    /**
     * 分页查询科技专栏
     *
     * @param 
     * @return
     */
    @RequestMapping(value = "/getSectionList", method = RequestMethod.GET)
    public JsonResult getSectionList(@RequestParam(name="offset",defaultValue="0" ) Integer offset) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, sectionsService.getSectionLists(page));
    }
    
    /**
     * 根据id删除科技专栏
     * @param sections 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/deleteSection", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult deleteSection(@RequestBody Sections sections) {

       boolean flag = sectionsService.deleteSection(sections.getId());
        if (flag) {
            return new JsonResult(true, "已删除");
        }
        return new JsonResult(false, "参数不合法");
    }



}
