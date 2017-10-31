package com.innovative.controller;

import com.innovative.bean.FeedBack;
import com.innovative.bean.Intelligence;
import com.innovative.bean.Message;
import com.innovative.bean.User;
import com.innovative.service.IntelligenceService;
import com.innovative.service.MessageService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/intelligence")
public class IntelligenceController {
    @Autowired
    private IntelligenceService service;
    private MessageService messageService;

    /**
     * 情报池列表页
     * @param offset
     * @return
     */
    @RequestMapping(value ="/getList",method = RequestMethod.GET)
    public JsonResult getList(@RequestParam(name = "offset",defaultValue = "0") Integer offset){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true,service.getList(page));
    }

    /**
     * 根据ID查询内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/getIntelligence/{id}",method = RequestMethod.GET)
    public JsonResult getIntelligence(@PathVariable("id") int id){
        Intelligence intelligence=service.getIntelligence(id);
        return  new JsonResult(true,intelligence);
    }

    /**
     * 添加内容
     * @param intelligence
     * @return
     */
    @RequestMapping(value = "/addIntelligence",method = RequestMethod.POST)
    @Transactional
    public JsonResult addIntelligence(@RequestBody Intelligence intelligence, HttpServletRequest request){
        boolean code=false;
        String message="添加失败！";
        Message messages=new Message();
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        intelligence.setCreateBy(user.getUserId());
       if (service.addIntelligence(intelligence)){
           messages.setUserid(user.getUserId());
           messages.setNotice(3);
           messages.setType("3");
           messages.setProjectId(intelligence.getId());
           messageService.addMessage(messages);
           code =true;
           message="添加成功！";
       }
        return  new JsonResult(code,message);
    }

    /**
     * 审核功能
     * @param intelligence
     * @return
     */
    @RequestMapping(value = "/checkIntelligence",method = RequestMethod.POST)
    public JsonResult checkIntelligence(@RequestBody Intelligence intelligence){
        boolean code=false;
        String message="审核失败";
        if (service.checkIntelligence(intelligence)){
            code=true;
            message="审核成功!";
        }
        return  new JsonResult(code,message);
    }

    /**
     * 下单功能
     * @param intelligence
     * @return
     */
    @RequestMapping(value = "/downOrder",method = RequestMethod.POST)
    public  JsonResult downOrder(@RequestBody Intelligence intelligence, HttpServletRequest request){
        boolean code=false;
        String message="下单失败";
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        intelligence.setUserid(user.getUserId());
        if (service.downOrder(intelligence)){
            code=true;
            message="下单成功";
        }
        return  new JsonResult(code,message);
    }

    /**
     * 添加情报反馈
     * @param feedBack
     * @return
     */
    @RequestMapping(value = "/addfeedback",method = RequestMethod.POST)
    public  JsonResult addfeedback(@RequestBody FeedBack feedBack){
        boolean code=false;
        String message="添加失败";
        if (service.addfeedback(feedBack)){
            code=true;
            message="添加成功";
        }
        return new JsonResult(code,message);
    }

    /**
     * 情报信息列表
     * @param offset
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFeedBack",method = RequestMethod.GET)
    public JsonResult getFeedBack(@RequestParam(name = "offset",defaultValue = "0") Integer offset,HttpServletRequest request){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=request.getSession();
       User user= (User) session.getAttribute("userId");
        return new JsonResult(true,service.getFeedBack(page,user.getUserId()));
    }

    /**
     * 查询附表内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/getfeedBacks/{id}",method = RequestMethod.GET)
    public  JsonResult getfeedBacks(@PathVariable("id") int id){
        return  new JsonResult(true,service.getfeedBacks(id));
    }
}
