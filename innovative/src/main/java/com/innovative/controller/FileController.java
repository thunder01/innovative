package com.innovative.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innovative.bean.FileBean;
import com.innovative.service.FileService;
import com.innovative.utils.BaseController;
import com.innovative.utils.JsonResult;


@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

	@Autowired
	FileService fileservice;

    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST  )
    public void fileUpload(@RequestParam("FileData") MultipartFile [] FileData ,
				    		@RequestParam(name="modname",required=true) String modname,
				    		@RequestParam(name="userid",required=true) String userid,
				    		@RequestParam(name="introductions",required=false) String introductions,
				    		HttpServletResponse res,HttpServletRequest request) {
    	 res.setContentType("application/json;charset=UTF-8");
    	 res.setCharacterEncoding("utf-8");
    	 res.setHeader("Charset", "utf-8");
    	 res.setHeader("Cache-Control", "no-cache");
    	 System.out.println("<><><><>"+request.getServletContext().getRealPath("/"));
    	 boolean val = false;
    	if(FileData.length<=0){
    		val = false;
    	}
    	if(modname == null)
    		modname="file";
    	PrintWriter print ;
       try {
    	   boolean flag = fileservice.uploadFile(FileData,modname,userid,introductions,request);
    	   print  = res.getWriter();
    	   print.write(flag+"");
    	   print.flush();
    	   print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    @RequestMapping(value = "/loadFile", method = RequestMethod.GET )
    public JsonResult fileload(@RequestParam("userid")String userid , @RequestParam("modname")String modname){
    	List<FileBean> list = null;
    	if( userid!=null && userid.length()>0 && modname!=null && modname.length()>0 )
    		  list = fileservice.getFiles(userid,modname);
    	
    	return new JsonResult(true, list);
    }


  










}
