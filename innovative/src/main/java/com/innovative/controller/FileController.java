package com.innovative.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.innovative.bean.FileLog;
import com.innovative.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.innovative.bean.FileBean;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
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
	@Autowired
	private FileLogService fileLogService;

	/**
	 *
	 * @param FileData
	 * @param modname
	 * @param userid
	 * @param type
	 * @param res
	 * @param request
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

	/**
	 *
	 * @param fileLog
	 * @param request
	 * @return
	 */

    @RequestMapping(value = "/downfile",method =RequestMethod.POST)
	public JsonResult downFile(@RequestBody FileLog fileLog,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("userId");
		fileLog.setUserid(user.getUserId());
		fileLog.setNumbers(1);
		FileLog fileLog1=fileLogService.queryList(fileLog.getFileName());
		if (fileLog1!=null&&fileLog1.getFileName().equals(fileLog.getFileName())){
           fileLog1.setNumbers(fileLog1.getNumbers()+1);
           fileLogService.update(fileLog1);
		}else {
			fileLogService.addFileLog(fileLog);
		}
		return new JsonResult(true,"成功");
	}









}
