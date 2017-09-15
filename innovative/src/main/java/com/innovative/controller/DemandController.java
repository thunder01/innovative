package com.innovative.controller;

import com.innovative.bean.Association;
import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.service.DemandService;
import com.innovative.service.MessageService;
import com.innovative.utils.BaseController;
import com.innovative.utils.HttpClientUpload;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        boolean code=true;
        Demand demands=demandService.getDemand(demand.getId());
        if(demands!=null){
            demandService.updateDemand(demand.getId());
        }else{
            messge="审核失败";
            code=false;
        }
        return  new JsonResult(code,messge);
    }
    /**
     * 添加内容
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @Transactional
    public  JsonResult add(@RequestBody Demand demand){
        Message message=new Message();
        String messge="保存成功!";
        boolean code=true;
        if(demandService.addDemand(demand)){
            message.setProjectId(demand.getId());
            message.setType("0");
            messageService.addMessage(message);
        }else{
            messge="保存失败";
            code=false;
        }
        return  new JsonResult(code,messge);
    }
    /**
     *  查询list
     */
    @RequestMapping(value = "/getDemandList",method = RequestMethod.GET)
    public JsonResult getList(@RequestParam(name="offset",defaultValue="0") Integer offset){
        Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, demandService.getDemandList(page));
    }
    /**
     * 批量上传文件
     */
    @RequestMapping(value ="/upload",method = RequestMethod.POST)
    public  JsonResult upload(@RequestParam(name = "FileData", required = true) MultipartFile[] FileData){
        String path= HttpClientUpload.httpClientUploadFile(FileData,"demand");
        return new JsonResult(true,path);
    }
}
