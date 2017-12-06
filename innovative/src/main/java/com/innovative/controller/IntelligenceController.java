package com.innovative.controller;

import com.innovative.bean.*;
import com.innovative.service.IntelligenceService;
import com.innovative.service.MessageService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/intelligence")
public class IntelligenceController {
    @Autowired
    private IntelligenceService service;
    @Autowired
    private MessageService messageService;
    private  static int orderNum=0;

    /**
     * 订单号生成
     */
    @RequestMapping(value = "/getOder",method = RequestMethod.GET)
    public JsonResult getOder(){
        String oderNumber="";
        int total=service.getTotalCounts();
        long no=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String nowdate = sdf.format(new Date());
        no=Long.parseLong(nowdate)*10000;
        no+=getNo();
        no=no+total;
        String oderNo=no+"";
        String oder=oderNo.substring(0,6);
        String code=oderNo.substring(6,10);
        oderNumber=oder+"-"+code;
        return new JsonResult(true,oderNumber);
    }
    public static int getNo(){//返回当天的订单数+1
        orderNum++;
        return orderNum;
    }

    /**
     * 情报池列表页。
     * @param offset
     * @return
     */
    @RequestMapping(value ="/getList",method = RequestMethod.GET)
    public JsonResult getList(@RequestParam(name = "offset",defaultValue = "0") Integer offset,HttpServletRequest request){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        String userName=user.getUserName();
        return new JsonResult(true,service.getList(page,userName));
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
//           messages.setUserid(user.getUserId());
//           messages.setNotice(3);
//           messages.setType("3");
//           messages.setProjectId(intelligence.getId());
//           messageService.addMessage(messages);
           //添加一个新的情报
           messageService.insertMessage(intelligence.getCheckname(), intelligence.getId()+"","3", 3);
           messageService.updateMsgCount(intelligence.getCheckname());
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
    public JsonResult checkIntelligence(@RequestBody Intelligence intelligence,HttpServletRequest request){
        boolean code=false;
        String message="审核失败";
        if (service.checkIntelligence(intelligence)){
            code=true;
            message="审核成功!";
        }
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        Intelligence intelligences=service.getIntelligence(intelligence.getId());
        //待办变成已办
        Message mess = messageService.getMessageByTypeAndProid("3", intelligence.getId()+"");
        messageService.updateMessage(user.getUserId(),mess.getId());
        messageService.updateMsgCount(user.getUserId());
//发给下单人一个消息
        messageService.insertMessage(intelligences.getCreateBy(), intelligence.getId()+"", "3", 1);
        messageService.updateMsgCount(intelligences.getCreateBy());
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
        Intelligence intelligence=service.getIntelligence(feedBack.getIntellId());
        if (service.addfeedback(feedBack)){
            code=true;
            System.out.println(">>>>>>>>>>>>>"+intelligence.getCreateBy());
            System.out.println("<<<<<<<<<<<<<"+feedBack.getId());
            messageService.insertMessage(intelligence.getCreateBy(), feedBack.getId()+"","14", 3);
            messageService.updateMsgCount(intelligence.getCreateBy());
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
    /**
     * 添加客户评价
     * @param score
     * @return
     */
    @RequestMapping(value = "/addScore",method = RequestMethod.POST)
    public  JsonResult addScore(@RequestBody Score score,HttpServletRequest request){
        boolean code=false;
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        String message="添加失败";
        if (service.addScore(score)){
            code=true;
            message="添加成功";
//            //添加已办内容
//            messageService.insertMessage(user.getUserId(), score.getIntellId()+"","15", 2);
//            messageService.updateMsgCount(user.getUserId());
        }
        return new JsonResult(code,message);
    }
    /**
     * 情报确认
     */
    @RequestMapping(value = "/updateFack",method = RequestMethod.POST)
    @Transactional
    public  JsonResult updateFack(@RequestBody FeedBack feedBack,HttpServletRequest request){
        boolean code=false;
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("userId");
        String message="添加失败";
        if (service.updateFack(feedBack)){
            code=true;
            message="添加成功";
            //添加已办内容
            messageService.insertMessage(user.getUserId(), feedBack.getId()+"","14", 2);
            messageService.updateMsgCount(user.getUserId());
            //给情报工程师待办通知
            Intelligence intelligence=service.getIntelligence(feedBack.getIntellId());
            System.out.println("!!!!!!!!!!!"+intelligence.getUserid());
            messageService.insertMessage(intelligence.getUserid(), intelligence.getId()+"","14", 3);
            messageService.updateMsgCount(intelligence.getUserid());

        }
        return new JsonResult(code,message);
    }
}
