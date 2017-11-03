package com.innovative.controller;

import com.innovative.bean.Association;
import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.bean.User;
import com.innovative.service.DemandService;
import com.innovative.service.MessageService;
import com.innovative.utils.BaseController;
import com.innovative.utils.HttpClientUpload;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 下订单的需求
 */
@RestController
@RequestMapping("/demand")
public class DemandController{

    @Autowired
     private DemandService demandService;
    @Autowired
     private  MessageService messageService;
     private  static int orderNum=0;
    /**
     * 订单号生成
     */
    @RequestMapping(value = "/getOder",method = RequestMethod.GET)
    public JsonResult getOder(){
        String oderNumber="";
        long no=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String nowdate = sdf.format(new Date());
        no=Long.parseLong(nowdate)*10000;
        no+=getNo();
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
     * 根据ID查询内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDemand/{id}",method = RequestMethod.GET)
    public JsonResult getDemand(@PathVariable("id") int id){
        Demand demand=demandService.getDemand(id);
        if (demand!=null){
            return  new JsonResult(true,demand);
        }
        return new JsonResult(false,"参数不合法！");
    }
    /**
     * 审核内容
     */
    @RequestMapping(value = "/update",method =RequestMethod.POST)
    public JsonResult update(@RequestBody Demand demand){
        String messge="审核成功!";
        Demand demandList=null;
        boolean code=true;
        if(demandService.updateDemand(demand)){
            //messageService.upStatus(demand.getId());
            demandList=demandService.getDemand(demand.getId());
        }else{
            messge="审核失败";
            code=false;
        }
        return  new JsonResult(code,demandList);
    }
    
    
    /**
     * 添加内容
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @Transactional
    public  JsonResult add(@RequestBody Demand demand,HttpServletRequest servletRequest){
        HttpSession session=servletRequest.getSession();
        User user= (User) session.getAttribute("userId");
        demand.setCteateBy(user.getUserId());
        String msg="保存成功!";
        boolean code=true;
        Message message=new Message();
        message.setType("0");
        message.setNotice(3);
        message.setUserid(demand.getCheckName());
        if(demandService.addDemand(demand)){
            message.setProjectId(demand.getId());
            messageService.addMessage(message);
            messageService.updateMsgCount(user.getUserId());
        }else{
        	msg="保存失败";
            code=false;
        }
//        if(demandService.addDemand(demand)){
//        	messageService.insertMessage(demand.getCheckName(), demand.getId()+"", "0", 3);
//        	messageService.updateMsgCount(demand.getCheckName());
//        }else {
//        	msg="保存失败";
//        	code=false;
//		}
        return  new JsonResult(code,msg);
    }
    /**
     *  查询list
     */
    @RequestMapping(value = "/getDemandList",method = RequestMethod.GET)
    public JsonResult getList(@RequestParam(name="offset",defaultValue="0") Integer offset,HttpServletRequest servletRequest){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=servletRequest.getSession();
        User user= (User) session.getAttribute("userId");
        String userName=user.getUserName();
        return new JsonResult(true, demandService.getDemandList(page,userName));
    }

    /**
     * 批量上传文件
     */
    @RequestMapping(value ="/upload",method = RequestMethod.POST)
    public  JsonResult upload(@RequestParam(name = "FileData", required = true) MultipartFile[] FileData){
        String path= HttpClientUpload.httpClientUploadFile(FileData,"demand");
        return new JsonResult(true,path);
    }
    /**
     * 个人信息查询需求内容
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.GET)
     public  JsonResult queryList(@RequestParam(name="offset",defaultValue="0")Integer offset,HttpServletRequest servletRequest){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        HttpSession session=servletRequest.getSession();
        User user= (User) session.getAttribute("userId");
        String userName=user.getUserName();
        return new JsonResult(true, demandService.getQueryList(page,userName));
     }
    
    
    //********************************************************************************************************
    /**
     * 添加内容
     */
    @RequestMapping(value = "/addDemand",method = RequestMethod.POST)
    @Transactional
    public  JsonResult addDemand(@RequestBody Demand demand,HttpServletRequest servletRequest){
        HttpSession session=servletRequest.getSession();
        User user= (User) session.getAttribute("userId");
        demand.setCteateBy(user.getUserId());
        String msg="保存成功!";
        boolean code=true;
        if(demandService.addDemand(demand)){
        	messageService.insertMessage(demand.getCheckName(), demand.getId()+"","0", 3);
        	messageService.updateMsgCount(demand.getCheckName());
        }else {
        	msg="保存失败";
        	code=false;
		}
        return  new JsonResult(code,msg);
    }
    /**
     * 审核内容
     */
    @RequestMapping(value = "/updateDemand",method =RequestMethod.POST)
    public JsonResult updateDemand(@RequestBody Demand demand,HttpServletRequest request){
    	User user = (User)request.getSession().getAttribute("userId");
        Demand demandList=null;
        boolean code=true;
        if(demandService.updateDemand(demand)){
        	demandList=demandService.getDemand(demand.getId());
        	Message message = messageService.getMessageByTypeAndProid("0", demand.getId()+"");
        	messageService.updateMessage(user.getUserId(),message.getId());
        	messageService.updateMsgCount(user.getUserId());
            //messageService.upStatus(demand.getId());
            messageService.insertMessage(demandList.getCteateBy(), demandList.getId()+"", "0", 2);
            messageService.updateMsgCount(demandList.getCteateBy());
        }else{
            code=false;
        }
        return  new JsonResult(code,demandList);
    }
}
