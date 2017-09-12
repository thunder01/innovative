package com.innovative.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.innovative.bean.Report;
import com.innovative.service.ReportService;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/report")
public class ReportController {
	@Resource
	private ReportService reportService;
	@RequestMapping(value = "/reportSave", method = RequestMethod.POST)
	public JsonResult reportSave(@RequestBody Report report/*,@RequestParam(name = "FileData", required = true) MultipartFile[] FileData*/,HttpServletRequest request){
		System.out.println(">>>>>>>>>>>>>");
		//用于存储上传后报告的地址
        StringBuffer buffer=new StringBuffer();
		//上传报告的操作
//        if (FileData != null && FileData.length > 0) {
//            try {
//                String url = null;
//                for (int i = 0; i < FileData.length; i++) {
//                    url = FileUpload.copyInputStreamToFile(FileData[i], "disassemble");
//                    
//                    if(i==FileData.length-1){
//                    	buffer.append(url);
//                    }else{
//                    	buffer.append(url+",");
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        report.setFile(buffer.toString());
		if(reportService.addReportAndOrder_report(report)>0){
			return new JsonResult(true, "添加成功");
		}
		return new JsonResult(false, "添加失败");
	}
	
	@RequestMapping(value="/reportDelete/{id}",method=RequestMethod.GET)
	public JsonResult reportDelete(@PathVariable(name="id")Integer id){
		if(reportService.updateReportDeleted(id)>0){
			return new JsonResult(true, "删除成功");
		}
		return new JsonResult(false, "删除失败");
	}
	
	
	@RequestMapping(value="/reportEdit",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult reportEdit(@RequestBody Report report){
		if(reportService.updateReport(report)>0){
			return new JsonResult(true, "修改成功");
		}
		return new JsonResult(false, "修改失败");
	}
	
	
	@RequestMapping(value="/reportSelect/{orderid}/{type}",method=RequestMethod.GET)
	public JsonResult reportSelect(@PathVariable(name = "orderid") Integer orderid,@PathVariable(name = "type") String type){
		Report report = reportService.findReportById(orderid, type);
		if(null!=report){
			return new JsonResult(true, report);
		}
		return new JsonResult(true, "没有找到");
	}
}
