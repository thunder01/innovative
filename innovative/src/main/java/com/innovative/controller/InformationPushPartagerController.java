package com.innovative.controller;
import com.innovative.bean.InformationPushPartager;
import com.innovative.bean.Informationpushcomenter;
import com.innovative.service.InformationPushPartagerService;
import com.innovative.service.InformationpushService;
import com.innovative.service.InformationpushcomenterService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import java.util.List;
import java.util.Map;

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
 * 信息推特分享记录
 * @author cj
 *
 */
@RestController
@RequestMapping("/informationpushpartager")
public class InformationPushPartagerController extends BaseController {




    @Autowired
    InformationPushPartagerService informationpushPartagerService;
  
    /**
     * 根据推特id获取所有分享记录
     *
     * @param id 推特id
     * @return
     */
    @RequestMapping(value = "/getInformationpushPartagers/{pushId}", method = RequestMethod.GET)
    public JsonResult getInformationpushPartagers(@PathVariable(name = "pushId") String pushId) {
    	
    	List<InformationPushPartager> informationPushPartagers = informationpushPartagerService.getInformationpushPartagersByPushid(pushId);
        if (informationPushPartagers.size()>0) {
            return new JsonResult(true, informationPushPartagers);
        }
        return new JsonResult(false, informationPushPartagers.size());
    }







	/**
	 *分享推特信息
	 * @param InformationPushPartager 
	 * @return
	 */
    @RequestMapping(value = "/addInformationPushPartager", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult addInformationPushPartager(@RequestBody InformationPushPartager informationPushPartager,HttpServletRequest req) {

    	informationPushPartager.setPartagerBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!informationpushPartagerService.addInformationPushPartager(informationPushPartager)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "分享成功");
    }
    
    
    /**
     * 根据推特id获取所有分享记录
     *
     * @param id 推特id
     * @return
     */
    @RequestMapping(value = "/getInformationpushPartagersByUserid", method = RequestMethod.GET)
    public JsonResult getInformationpushPartagers(@RequestParam(name="offset",defaultValue="0" ) Integer offset,HttpServletRequest req) {
    	String partagerBy = CookiesUtil.getCookieValue(req,"user_name");
    	 Integer page = offset/(new PageInfo().getPageSize()) +1;
    	 Map<String,Object> informationPushPartagermap = informationpushPartagerService.getInformationpushPartagersByUserid(partagerBy,page);
        return new JsonResult(false, "参数不合法");
    }
    
 











    
    





}
