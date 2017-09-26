package com.innovative.controller;

import com.innovative.bean.Case;
import com.innovative.bean.FileBean;
import com.innovative.dao.FileDao;
import com.innovative.service.CaseService;
import com.innovative.utils.JsonResult;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value ="/case")
public class CaseController {
    @Autowired
    private CaseService service;
    @Autowired
    private FileDao dao;


    /**
     * 查询List
     */
    @RequestMapping(value = "/getCaseList",method = RequestMethod.GET)
    public JsonResult getList(){
        return new JsonResult(true,service.getFileList());
    }
    /**
     * 根据ID查新内容
     */
    @RequestMapping(value = "/getListById/{id}",method = RequestMethod.GET)
    public JsonResult getgetListById (@PathVariable("id") int id){
        return new JsonResult(true,service.getListById(id));
    }
    /**
     * 添加功能
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public  JsonResult add(@RequestBody Case cases){
        String messge="添加失败!";
        boolean code=false;
        cases.setTitleImage(dao.getFileById(cases.getTitleImage(),"logo").get(0).getUrl());
        if (service.add(cases)) {
            messge = "添加成功！";
            code = true;
        }
        return  new JsonResult(code,messge);
    }
    /**
     * 修改功能
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public  JsonResult update(@RequestBody Case cases){
        String messge="修改失败!";
        boolean code=false;
        cases.setTitleImage(dao.getFileById(cases.getTitleImage(),"logo").get(0).getUrl());
        if (service.update(cases)) {
            messge = "修改成功！";
            code = true;
        }
        return  new JsonResult(code,messge);
    }
    /**
     * 删除文件功能
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResult delete(@RequestBody FileBean file){
        String messge="删除失败!";
        boolean code=false;
        if (dao.deleteZdFile(file.getRefId(),file.getFileName())){
            messge = "删除成功！";
            code = true;
        }
        return  new JsonResult(code,messge);
    }
}
