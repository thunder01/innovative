package com.innovative.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Report;
import com.innovative.service.ReportService;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/report")
public class ReportController {
	@Resource
	private ReportService reportService;
	@RequestMapping(value = "/reportSave", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult reportSave(@RequestBody Report report){
		if(reportService.addReportAndOrder_report(report)>0){
			return new JsonResult(true, "添加成功");
		}
		return new JsonResult(false, "添加失败");
	}
	
	@RequestMapping(value="/reportDelete/{id}",method=RequestMethod.GET)
	public JsonResult reportDelete(Integer id){
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
	@RequestMapping(value="/reportSelect/{orderid}/{type}",method=RequestMethod.POST)
	public JsonResult reportSelect(@PathVariable(name = "orderid") Integer orderid,@PathVariable(name = "type") String type){
		Report report = reportService.findReportById(orderid, type);
		if(null!=report){
			return new JsonResult(true, report);
		}
		return new JsonResult(true, "没有找到");
	}
}
