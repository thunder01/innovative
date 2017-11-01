package com.innovative.controller;

import com.innovative.bean.Information;
import com.innovative.bean.TechInformationApprouver;
import com.innovative.bean.TechInformationCollection;
import com.innovative.service.InformationService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;
import javax.servlet.http.HttpServletRequest;
import org.elasticsearch.client.transport.TransportClient;
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
    @Autowired 
    private TransportClient client;
    /**
     * 增加科技资讯
     *
     * @param information 科技资讯实体
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
     * @param information 科技资讯实体
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
     * @param Sections 科技资讯id
     * @return
     */
    @RequestMapping(value = "/getInformationComentById/{id}", method = RequestMethod.GET)
    public JsonResult getInformationById(@PathVariable(name = "id") String id,HttpServletRequest req) {
    	if(id ==  null ||  id.length() <= 0)
    		 return new JsonResult(false, "请传递要修改的科技资讯!");
    	Information information = informationService.getInformationById(id,CookiesUtil.getCookieValue(req,"user_name"));
    	return  new JsonResult(true,information);
    }
    
    
    /**
     * 分页查询科技资讯
     * @param 
     * @return
     */
    @RequestMapping(value = "/getInformationList", method = RequestMethod.GET)
    public JsonResult getSectionList(@RequestParam(name="offset",defaultValue="0" ) Integer offset,@RequestParam(name="state",required=false) String state) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, informationService.getInformationLists(page,state));
    }
    
   /**
    * 根据id删除科技资讯
    * @param information
    * @return
    */
    @RequestMapping(value = "/deleteInformation", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult deleteInformation(@RequestBody Information information) {

       boolean flag = informationService.deleteInformation(information.getId());
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
    @RequestMapping(value = "/queryByKey/{key}", method = RequestMethod.GET)
    public JsonResult queryByKey(@PathVariable("key")String key){
    	JsonResult result=informationService.queryByKey(key);
    	return result;
    }
    
    /**
     * 增加科技资讯点赞记录
     *
     * @param information 科技资讯实体
     * @return
     */
    @RequestMapping(value = "/addApprouver", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult addApprouver(@RequestBody TechInformationApprouver techInformationApprouver,HttpServletRequest req) {
    	if(techInformationApprouver ==  null || techInformationApprouver.getInformationId() == null || techInformationApprouver.getInformationId().trim().equals("") )
    		 return new JsonResult(false, "没有获取到实体，添加科技资讯失败！");
    	techInformationApprouver.setId(Misc.uuid());
    	//设置创建人，修改人
    	techInformationApprouver.setApprouverBy(CookiesUtil.getCookieValue(req,"user_name"));
    	Information  information = informationService.addApprouver(techInformationApprouver);
    	return information != null ? new JsonResult(true,information) : new JsonResult(false, "您今天已经点赞！") ;
    }
    
    /**
     *收藏科技资讯
     *
     * @param techInformationCollection 科技资讯收藏实体
     * @return
     */
    @RequestMapping(value = "/collectionTechInformation", method = RequestMethod.POST)
    @ResponseBody 
    public JsonResult collectionTechInformation(@RequestBody TechInformationCollection techInformationCollection,HttpServletRequest req) {
    	if(techInformationCollection ==  null || techInformationCollection.getInformationId() == null || techInformationCollection.getInformationId().trim().equals("") )
    		 return new JsonResult(false, "没有获取到实体，添加科技资讯失败！");
    	techInformationCollection.setId(Misc.uuid());
    	//设置创建人，修改人
    	techInformationCollection.setCollectBy(CookiesUtil.getCookieValue(req,"user_name"));
    	boolean flag = informationService.collectionTechInformation(techInformationCollection);
    	return flag ? new JsonResult(true,"已收藏") : new JsonResult(false, "此科技资讯您之前收藏过！") ;
    }
    
    
}
