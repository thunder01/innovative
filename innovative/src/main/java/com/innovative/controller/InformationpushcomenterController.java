package com.innovative.controller;
import com.innovative.bean.Informationpush;
import com.innovative.bean.Informationpushcomenter;
import com.innovative.service.InformationpushService;
import com.innovative.service.InformationpushcomenterService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

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


/**
 * 信息推特 评论表
 * @author cj
 *
 */
@RestController
@RequestMapping("/informationpushcomenter")
public class InformationpushcomenterController extends BaseController {




    @Autowired
    InformationpushService informationpushService;
    @Autowired
    InformationpushcomenterService informationpushcomenterService;//关于
  
    /**
     * 根据推特id获取所有评论
     *
     * @param id 推特id
     * @return
     */
    @RequestMapping(value = "/getInformationpushcoment/{pushId}", method = RequestMethod.GET)
    public JsonResult getInformationpushcoment(@PathVariable(name = "pushId") String pushId) {
    	
    	List<Informationpushcomenter> informationpushcomenters = informationpushcomenterService.getInformationpushcomenterByPushId(pushId);
        if (informationpushcomenters.size()>0) {
            return new JsonResult(true, informationpushcomenters);
        }
        return new JsonResult(false, "参数不合法");
    }

    /**
     * 根据id删除评论只有（作者）可以
     * @param id 评论id
     * @return
     */
    @RequestMapping(value = "/deleteInformationpushcoment", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteInformationpushcoment(@RequestBody Informationpushcomenter informationpushcomenter) {
    	//删除推特信息
       boolean flag = informationpushcomenterService.deleteInformationpushcoment(informationpushcomenter.getId());
        if (flag) {
        	//删除后返回推特信息的所有的评论
            return new JsonResult(true, informationpushcomenterService.getInformationpushcomenterByPushId(informationpushcomenter.getPushId()));
        }
        return new JsonResult(false, "参数不合法");
    }






/**
 * 新增评论
 * @param informationpush 专家对象封装
 * @return
 */
    @RequestMapping(value = "/addInformationpushcomenter", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addInformationpushcomenter(@RequestBody Informationpushcomenter informationpushcomenter,HttpServletRequest req) {

    	informationpushcomenter.setComentBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!informationpushcomenterService.addInformationpushcomenter(informationpushcomenter)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, informationpushcomenterService.getformationpushcomenter(informationpushcomenter.getId()));
    }






  /**
   * 修改评论信息
   * @param expert 专家对象
   * @param req
   * @return
   */
    @RequestMapping(value = "/updateInformationpushcomenter", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateInformationpushcomenter(@RequestBody Informationpushcomenter informationpushcomenter,HttpServletRequest req) {

        if (!informationpushcomenterService.updateInformationpushcomenter(informationpushcomenter)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        Informationpushcomenter informationpushcomenters = informationpushcomenterService.getformationpushcomenter(informationpushcomenter.getId());
        return informationpushcomenters != null ? new JsonResult(true, informationpushcomenters):new JsonResult(false, "获取新对象失败");
    }
    
    
    /**
     * 用户分享记录
     * @param collection 收藏
     * @param req
     * @return
     */
     @RequestMapping(value = "/getInformationPushComentersByUserid", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
     public JsonResult getInformationPushComentersByUserid(@RequestParam(name = "userId") String userId ,@RequestParam(name="offset",defaultValue="0" ) Integer offset,HttpServletRequest req) {
     	Integer page = offset/(new PageInfo().getPageSize()) +1;
     	//此用户收藏记录分页查找
         return new JsonResult(true,informationpushcomenterService.getInformationPushComentersByUserid(userId,page));
     }

     
    
    
     /**
      * 根据推特的评论获取评论信息（展示信息的地方用）
      *
      * @param id 推特id
      * @return
      */
     @RequestMapping(value = "/getInformationpushcomenterForMessage/{id}", method = RequestMethod.GET)
     public JsonResult getInformationpushcomenter(@PathVariable(name = "id") String id,HttpServletRequest req) {
     	
    	 Informationpush informationpush = informationpushcomenterService.getInformationpushcomenterForMessage(id,CookiesUtil.getCookieValue(req, "user_name"));
         return new JsonResult(true, informationpush);
     }




}
