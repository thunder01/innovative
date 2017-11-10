package com.innovative.controller;

import com.innovative.bean.Sections;
import com.innovative.bean.TechInformationCollection;
import com.innovative.bean.TechSectionsApprouver;
import com.innovative.bean.TechSectionsCollection;
import com.innovative.service.SectionsService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
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
    	return flag ? new JsonResult(true,sections.getId()) : new JsonResult(false, "修改科技资讯失败！");
    }
    /**
     * 查询科技资讯
     *
     * @param id 科技专栏id
     * @return
     */
    @RequestMapping(value = "/getSectionById/{id}", method = RequestMethod.GET)
    public JsonResult getSectionById(@PathVariable(name = "id") String id,HttpServletRequest req) {
    	if(id ==  null ||  id.length() <= 0)
    		 return new JsonResult(false, "请传递要修改的科技资讯!");
    	Sections sections = sectionsService.getSectionByIdAndUserid(id,CookiesUtil.getCookieValue(req,"user_name"));
    	return  new JsonResult(true,sections);
    }
    
    
    /**
     * 分页查询科技专栏
     *
     * @param 
     * @return
     */
    @RequestMapping(value = "/getSectionList", method = RequestMethod.GET)
    public JsonResult getSectionList(@RequestParam(name="offset",defaultValue="0" ) Integer offset,@RequestParam(name="type",required=false) String type) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, sectionsService.getSectionLists(page,type));
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

    /**
     * 使用elastic search进行模糊搜索
     * @param key 搜索的关键字
     * @return
     */
    @RequestMapping(value = "/queryByKey", method = RequestMethod.GET)
    public JsonResult queryByKey(@RequestParam(name="sectors")String key,@RequestParam(name="offset",defaultValue="0" )Integer offset){
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
    	JsonResult result=sectionsService.queryByKey(key,page);
    	return result;
    }
    
    /**
     * 增加科技专栏点赞记录
     *
     * @param techSectionsApprouver 科技专栏实体
     * @return
     */
    @RequestMapping(value = "/addApprouver", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult addApprouver(@RequestBody TechSectionsApprouver techSectionsApprouver,HttpServletRequest req) {
    	if(techSectionsApprouver ==  null || techSectionsApprouver.getSectionId() == null || techSectionsApprouver.getSectionId().trim().equals("") )
    		 return new JsonResult(false, "没有获取到实体，添加科技专栏失败！");
    	techSectionsApprouver.setId(Misc.uuid());
    	//设置创建人，修改人
    	techSectionsApprouver.setApprouverBy(CookiesUtil.getCookieValue(req,"user_name"));
    	Sections  section = sectionsService.addApprouver(techSectionsApprouver);
    	return section != null ? new JsonResult(true,section) : new JsonResult(false, "您今天已经点赞！") ;
    }
    
    /**
     *收藏科技专栏
     *
     * @param techInformationCollection 科技专栏收藏实体
     * @return
     */
    @RequestMapping(value = "/collectionTechSection", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult collectionTechSection(@RequestBody TechSectionsCollection techSectionsCollection,HttpServletRequest req) {
    	if(techSectionsCollection ==  null || techSectionsCollection.getSectionId() == null || techSectionsCollection.getSectionId().trim().equals("") )
    		 return new JsonResult(false, "没有获取到实体，添加科技资讯失败！");
    	techSectionsCollection.setId(Misc.uuid());
    	//设置创建人，修改人
    	techSectionsCollection.setCollectBy(CookiesUtil.getCookieValue(req,"user_name"));
    	boolean flag = sectionsService.collectionTechSection(techSectionsCollection);
    	return flag ? new JsonResult(true,"已收藏") : new JsonResult(false, "此科技资讯您之前收藏过！") ;
    }

}
