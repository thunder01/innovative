package com.innovative.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.innovative.bean.Report;
import com.innovative.service.ReportService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

@RestController
@RequestMapping("/report")
public class ReportController {
	@Resource
	private ReportService reportService;
	
	/**
	 * 通过订单id和报告类型来查询报告
	 * @param orderid
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/reportSelect/{offset}/{order_id}/{type}",method=RequestMethod.GET)
	public JsonResult reportSelect(@PathVariable(name="offset") Integer offset, 
			@PathVariable(name = "order_id") Integer order_id,@PathVariable(name = "type") String type){
		Integer page = offset/(new PageInfo().getPageSize()) +1;		
		Map<String,Object> map = reportService.findReportByOrderId(order_id, type,page);		
		if((Integer)map.get("result")==1){
			return new JsonResult(true, map);
		}
		return new JsonResult(false, map);
	}
	
	/*跳转到添加报告的页面*/
	@RequestMapping(value="toSaveReport/{orderid}/{type}",method=RequestMethod.GET)
	public JsonResult toSaveReport(@PathVariable(name="orderid") Integer orderid,@PathVariable(name="type") Integer type){
		Map<String, Object> map=new HashMap<>();
		map.put("orderid", orderid);
		map.put("type", type);
		return new JsonResult(true, map);
	}
	
	/**
	 * 添加一个报告
	 * @param report
	 * @param order_id 订单id
	 * @param approval_id 立项表单id
	 * @return
	 */
	@RequestMapping(value = "/reportSave", method = RequestMethod.POST)
	public JsonResult reportSave( @RequestBody Report report){	
		Map<String, Object> map=reportService.addReport(report);

		if((Integer)map.get("result")==1){
			return new JsonResult(true, map);
		}
		return new JsonResult(false, "添加失败");
	}
	
	/**
	 * 通过id删除报告
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/reportDelete",method=RequestMethod.POST)
	public Object reportDelete(@RequestBody Report report){
		Map<String, Object> map = reportService.findReportById(report.getId(), 3);
		Report r =(Report) map.get("item");
		if(reportService.updateReportDeleted(report.getId(),report.getDelete_by())>0){
			return reportSelect(0,r.getOrder_id(),r.getType());
		}
		return new JsonResult(false, "删除失败");
	}
	
	/**
	 * 编辑报告
	 * @param report
	 * @param FileData
	 * @return
	 */
	@RequestMapping(value="/reportEdit",method=RequestMethod.POST)
	public JsonResult reportEdit( @RequestBody Report report){
		System.out.println(">>>>>>>>>>>>>>"+report);
		Map<String, Object> map = reportService.updateReport(report);
		if((int)map.get("result")>0){
			return new JsonResult(true, map);
		}
		return new JsonResult(false, "修改失败");
	}
	
	/**
	 * 根据报告id获取报告的详情
	 * @param report_id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/reportDetail/{report_id}/{type}",method=RequestMethod.GET)
	public JsonResult reportDetail(@PathVariable(name="report_id") Integer report_id,@PathVariable(name="type") Integer type){
		Map<String,Object> map=reportService.findReportById(report_id,type);
	
		if (map.get("item")!=null) {
			return new JsonResult(true, map);
		}else{
			return new JsonResult(false, "结果空");
		}
	}
}