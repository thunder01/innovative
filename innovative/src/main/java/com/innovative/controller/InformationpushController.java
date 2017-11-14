package com.innovative.controller;

import com.innovative.bean.Approuver;
import com.innovative.bean.CollectionPush;
import com.innovative.bean.Informationpush;
import com.innovative.service.InformationpushService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 信息推特
 * @author cj
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/informationpush")
public class InformationpushController extends BaseController {




    @Autowired
    InformationpushService informationpushService;
  
    /**
     * 根据id推特信息
     *
     * @param id 推特id
     * @return
     */
    @RequestMapping(value = "/getInformationpush/{id}", method = RequestMethod.GET)
    public JsonResult getInformationpush(@PathVariable(name = "id") String id,HttpServletRequest req) {
    	Informationpush informationpush = informationpushService.getInformationpush(id,CookiesUtil.getCookieValue(req,"user_name"));
        if (informationpush != null) {
            return new JsonResult(true, informationpush);
        }
        return new JsonResult(false, "参数不合法");
    }

    /**
     * 根据id删除推特信息
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/deleteInformationpush", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteInformationpush(@RequestBody Informationpush informationpush,HttpServletRequest req) {

       boolean flag = informationpushService.deleteInformationpush(informationpush.getId());
        if (flag) {
            return new JsonResult(true, informationpushService.getInformationpushList(1,CookiesUtil.getCookieValue(req, "user_name")));
        }
        return new JsonResult(false, "参数不合法");
    }




    /**
     * 推特信息列表(分页获取推特信息)
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getInformationpushList", method = RequestMethod.GET)
    public JsonResult getInformationpushList(@RequestParam(name="offset",defaultValue="0" ) Integer offset,HttpServletRequest req) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, informationpushService.getInformationpushList(page,CookiesUtil.getCookieValue(req, "user_name")));
    }
    
    /**
     * 推特信息列表（点击更多过去列表）
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getInformationpushListSurplus", method = RequestMethod.GET)
    public JsonResult getInformationpushListSurplus(@RequestParam(name="offset",defaultValue="10") Integer offset) {
        return new JsonResult(true, informationpushService.getInformationpushListSurplus(offset));
    }

    /**
     * 获取一个推特信息的评论
     *
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getInformationpushListByPid/{pid}", method = RequestMethod.GET)
    public JsonResult getInformationpushListByPid(@PathVariable(name = "pid")String pid) {
        return new JsonResult(true, informationpushService.getInformationpushListByPid(pid));
    }


/**
 * 新增推特信息
 * @param informationpush 推特信息对象
 * @return
 */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,  
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,  
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")  
    @RequestMapping(value = "/addInformationpush", method = RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addInformationpush(@RequestBody Informationpush informationpush,HttpServletRequest req,HttpServletResponse response) {
    	//解决跨域问题
    	/*response.setHeader("Access-Control-Allow-Origin","*");*/
    	informationpush.setComentBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!informationpushService.addInformationpush(informationpush)) {
            return new JsonResult(false, "新增失败，请重试！");
        
				//response.getWriter().print(JSON.toJSONString(new JsonResult(false, "新增失败，请重试！"),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue));
        }
        //response.getWriter().print(JSON.toJSONString(informationpushService.getInformationpush(informationpush.getId(),informationpush.getComentBy()),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue));
        return new JsonResult(true, informationpushService.getInformationpush(informationpush.getId(),informationpush.getComentBy()));
    }






  /**
   * 修改推特信息
   * @param expert 专家对象
   * @param req
   * @return
   */
    @RequestMapping(value = "/updateInformationpush", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateInformationpush(@RequestBody Informationpush informationpush,HttpServletRequest req) {

        if("".equals(informationpush.getId())||informationpushService.getInformationpush(informationpush.getId(),CookiesUtil.getCookieValue(req,"user_name")) == null){
            return new JsonResult(false, "此记录不存在");
        }
        if (!informationpushService.updateInformationpush(informationpush)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        Informationpush newInformationpush = informationpushService.getInformationpush(informationpush.getId(),CookiesUtil.getCookieValue(req,"user_name"));
        return newInformationpush != null ? new JsonResult(true, newInformationpush):new JsonResult(false, "获取新对象失败");
    }



	/**
	 * 点赞
	 * @param informationpush 信息点赞
	 * @return
	 */
    @RequestMapping(value = "/addApprouver", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addApprouver(@RequestBody Approuver approuver,HttpServletRequest req) {

    	approuver.setApprouverBy(CookiesUtil.getCookieValue(req,"user_name"));
    	//生成主键
    	approuver.setId(Misc.uuid());
        if (!informationpushService.addApprouver(approuver)) {
            return new JsonResult(false, "今天已点赞，一天只能点赞一次!");
        }
        return new JsonResult(true, informationpushService.getCommenterNum(approuver.getComentId()));
    }
    
    /**
	 * 根据点赞id获取点赞得推特信息
	 * @param informationpush 信息点赞
	 * @return
	 */
    @RequestMapping(value = "/getApprouverByIdForMessage/{id}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult getApprouverByIdForMessage(@PathVariable(name ="id" ,required = true) String id ,HttpServletRequest req) {
        return new JsonResult(true, informationpushService.getApprouverByIdForMessage(id,CookiesUtil.getCookieValue(req, "user_name")));
    }
    
    
	/**
	 * 收藏
	 * @param informationpush 推特信息收藏
	 * @return
	 */
    @RequestMapping(value = "/collectInformationpush", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult collectInformationpush(@RequestBody CollectionPush collection,HttpServletRequest req) {

    	collection.setCollectBy(CookiesUtil.getCookieValue(req,"user_name"));
    	collection.setId(Misc.uuid());
        if (!informationpushService.collectInformationpush(collection)) {
            return new JsonResult(false, "您已收藏!一天只能收藏一次");
        }
        return new JsonResult(true, informationpushService.getCollectNum(collection.getComentId()));
    }
    
    
   /**
    * 用户收藏记录分页查找
    * @param collection 收藏
    * @param req
    * @return
    */
    @RequestMapping(value = "/getCollects/{userid}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public JsonResult getCollects(@PathVariable(name = "userid") String userid ,@RequestParam(name="offset",defaultValue="0" ) Integer offset,HttpServletRequest req) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
    	//此用户收藏记录分页查找
        return new JsonResult(true,informationpushService.getCollects(userid,page));
    }
    
    /**
     *根据收藏id获取收藏信息（用户消息那查询）
     * @param id 收藏的id
     * @param req
     * @return
     */
     @RequestMapping(value = "/getCollectInformationForMessage/{id}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
     public JsonResult getCollectInformationForMessage(@PathVariable(name = "id") String id ,HttpServletRequest req) {
     	//根据收藏id获取收藏信息
         return new JsonResult(true,informationpushService.getCollectInformationForMessage(id,CookiesUtil.getCookieValue(req, "user_name")));
     }
    
    /**
     * 获取前5条收藏
     * @param collection 收藏
     * @param req
     * @return
     */
     @RequestMapping(value = "/getCollectFive/{userid}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
     public JsonResult getCollectFive(@PathVariable(name = "userid") String userid ,HttpServletRequest req) {
     	
         return new JsonResult(true,informationpushService.getCollectFive(userid));
     }
     
     /**
      * 获取所有推特信息，（每条推特信息的转发数量，收藏数量，评论数量，点赞数量）用户信息，用户收藏信息（用户收藏的总数）
      * @param userid
      * @param req
      * @return
      */
  /*   @RequestMapping(value = "/getAllinformations/{userid}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
     public JsonResult getAllinformations(@PathVariable(name = "userid") String userid ,HttpServletRequest req) {
     	
         return new JsonResult(true,informationpushService.getAllinformation(userid));
     }*/
     
     
     /**
      * 根据用户id获取所有的分享记录以及条数,评论数与评论的推特信息,赞的数量与咱的信息推特信息，评论的推特信息与数量
      * @param userid
      * @return
      */
     @RequestMapping(value = "/getInformationpushsMessageByUserid/{userid}/{page}", method = RequestMethod.GET)
     public JsonResult getInformationpushsMessageByUserid(@PathVariable(name = "userid") String userid,@PathVariable(name = "page") int page) {
     	
     	Map<String,Object> informationPushPartagers = informationpushService.getInformationpushsMessageByUserid(userid,page);
     	
         return new JsonResult(true, informationPushPartagers);
     }
     
    
    





}
