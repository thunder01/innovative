package com.innovative.controller;

import com.innovative.bean.FileBean;
import com.innovative.utils.BaseController;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController extends BaseController {



    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonResult getAssociation(@RequestParam("FileData") MultipartFile [] FileData ,@RequestParam(name="modname",required=false) String modname) {
    	
    	if(modname == null)
    		modname="file";
    	String url = FileUpload.getUrls(FileData, modname);
         if("error".equals(url)){
        	 return new JsonResult(false, "上传失败!");
         }
         
        return new JsonResult(true, "上传成功!");
    }





  










}
