package com.innovative.controller;

import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.service.DemandService;
import com.innovative.service.MessageService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 下订单的需求
 */
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
}
