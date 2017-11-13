package com.innovative.controller;

import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.service.DemandService;
import com.innovative.service.MessageService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 下订单的需求
 */
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;
    /**
     *  查询list
     */
    @RequestMapping(value = "/getMessageList",method = RequestMethod.GET)
    public JsonResult getList(@RequestParam(name="offset",defaultValue="0") Integer offset){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, messageService.getMessageList(page));
    }
    
    
    /*     分割线           =================================      */
    
    /**
     * 消息查询
     * @param offset 分页的偏移量
     * @param userid 用户的id
     * @param notice 消息的类型（1通知，2已办，3待办）
     * @return
     */
    @RequestMapping(value = "/getMessage/{userid}/{notice}",method = RequestMethod.GET)
    public JsonResult getMessage(@RequestParam(name="offset",defaultValue="0") Integer offset,@PathVariable(name="userid") String userid,
    		@PathVariable(name="notice") int notice){
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
    	Message message = new Message();
    	message.setUserid(userid);
    	message.setNotice(notice);
    	Map<String, Object> map = messageService.getMessage(message, page);
		return new JsonResult(true, map);
    }
    
}
