package com.innovative.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innovative.bean.FileBean;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
import com.innovative.service.FileService;
import com.innovative.service.IntegralService;
import com.innovative.service.TechnicalReportService;
import com.innovative.service.UserService;
import com.innovative.utils.BaseController;
import com.innovative.utils.JsonResult;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

	@Autowired
	FileService fileservice;
	@Autowired
	private TechnicalReportService technicalReportService;
	@Autowired
	private UserService userService;
	@Autowired
	private IntegralService integralService;

    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST  )
    public void fileUpload(@RequestParam("FileData") MultipartFile [] FileData ,
				    		@RequestParam(name="modname",required=true) String modname,
				    		@RequestParam(name="userid",required=true) String userid,
				    		@RequestParam(name="type",required=false) String type,
				    		HttpServletResponse res,HttpServletRequest request) {
    	 res.setContentType("application/json;charset=UTF-8");
    	 res.setCharacterEncoding("utf-8");
    	 res.setHeader("Charset", "utf-8");
    	 res.setHeader("Cache-Control", "no-cache");
    	if(modname == null)
    		modname="file";
    	PrintWriter print ;
       try {
    	   boolean flag = fileservice.uploadFile(FileData,modname,userid,type,request);
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

    /**
     * 当有人下载技术报告的时候给技术报告上传人20积分
     * @param id 技术报告id
     * @return
     */
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET )
    public JsonResult download(@PathVariable(name = "id")String id){
    	TechnicalReport technicalReport = technicalReportService.getTechnicalReportById(id);
    	String userid = technicalReport.getCreatedBy();
    	User user = userService.getUser(userid);
    	if(user!=null){
    		integralService.managerIntegral(6, userid, id);
    	}
		return new JsonResult(true, technicalReport);
    }









}
