package com.innovative.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	/**
	 * 添加一个报告，并向中间表添加一个数据
	 * @param report
	 * @param FileData
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reportSave", method = RequestMethod.POST)
	public JsonResult reportSave( Report report, MultipartFile[] FileData,HttpServletRequest request){
		//用于存储上传后报告的地址
        StringBuffer buffer=new StringBuffer();
		//上传报告的操作
        if (FileData != null && FileData.length > 0) {
            try {
                String url = null;
                for (int i = 0; i < FileData.length; i++) {
                    url = FileUpload.copyFile(FileData[i], "report");
                    
                    if(i==FileData.length-1){
                    	buffer.append(url);
                    }else{
                    	buffer.append(url+",");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        report.setFile(buffer.toString());
		if(reportService.addReportAndOrder_report(report)>0){
			return new JsonResult(true, "添加成功");
		}
		return new JsonResult(false, "添加失败");
	}
	/**
	 * 通过id删除报告
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/reportDelete/{id}",method=RequestMethod.GET)
	public JsonResult reportDelete(@PathVariable(name="id")Integer id){
		if(reportService.updateReportDeleted(id)>0){
			return new JsonResult(true, "删除成功");
		}
		return new JsonResult(false, "删除失败");
	}
	
	
	@RequestMapping(value="/reportEdit",method=RequestMethod.POST)
	public JsonResult reportEdit( Report report ,MultipartFile[] FileData){
		//用于存储上传后报告的地址
        StringBuffer buffer=new StringBuffer();
		//上传报告的操作
        if (FileData != null && FileData.length > 0) {
            try {
                String url = null;
                for (int i = 0; i < FileData.length; i++) {
                    url = FileUpload.copyFile(FileData[i], "report");
                    if(i==FileData.length-1){
                    	buffer.append(url);
                    }else{
                    	buffer.append(url+",");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        report.setFile(buffer.toString());
		if(reportService.updateReport(report)>0){
			return new JsonResult(true, "修改成功");
		}
		return new JsonResult(false, "修改失败");
	}
	
	/**
	 * 通过订单id和报告类型来查询报告
	 * @param orderid
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/reportSelect/{orderid}/{type}",method=RequestMethod.GET)
	public JsonResult reportSelect(@PathVariable(name = "orderid") Integer orderid,@PathVariable(name = "type") String type){
		Report report = reportService.findReportById(orderid, type);
		if(null!=report){
			return new JsonResult(true, report);
		}
		return new JsonResult(true, "没有找到");
	}
	
	/**
	 * 查询同一订单下报告，并按创建时间排序
	 * @param order_id
	 * @return
	 */
	@RequestMapping(value="/reportRank/{order_id}",method=RequestMethod.GET)
	public JsonResult reportRank(@PathVariable(name = "order_id") Integer order_id){
		System.out.println(order_id);
		List<Report> listReport = reportService.rankReport(order_id);
		System.out.println(">>>>>>>>>>"+listReport);
		if(listReport.size()>0){
			return new JsonResult(true, listReport);
		}
		return new JsonResult(false, "没有找到");
	}
	
	
	
}
