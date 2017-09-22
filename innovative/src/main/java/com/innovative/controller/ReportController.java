package com.innovative.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.innovative.bean.Report;
import com.innovative.service.ReportService;
import com.innovative.utils.HttpClientUpload;
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
		Map<String,Object> map = reportService.findReportById(order_id, type,page);
		
		if((Integer)map.get("result")==1){
			return new JsonResult(true, map);
		}
		return new JsonResult(false, map);
	}
	
	/*跳转到添加报告的页面*/
	@RequestMapping(value="toSaveReport/{orderid}/{approval_id}/{type}",method=RequestMethod.GET)
	public JsonResult toSaveReport(@PathVariable(name="orderid") Integer orderid,@PathVariable(name="approval_id") Integer approval_id,
			@PathVariable(name="type") Integer type){
		Map<String, Object> map=new HashMap<>();
		map.put("orderid", orderid);
		map.put("approval_id", approval_id);
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
		Map<String, Object> map=reportService.addReportAndOrder_report(report);

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
	@RequestMapping(value="/reportDelete/{id}/{userid}",method=RequestMethod.GET)
	public JsonResult reportDelete(@PathVariable(name="id")Integer id,@PathVariable String userid){
		if(reportService.updateReportDeleted(id,userid)>0){
			return new JsonResult(true, "删除成功");
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
		if(reportService.updateReport(report)>0){
			return new JsonResult(true, "修改成功");
		}
		return new JsonResult(false, "修改失败");
	}
	
	/**
	 * 根据报告id获取报告的详情
	 * 
	 * */
	@RequestMapping(value="reportDetail/{report_id}/{approval_id}/{type}",method=RequestMethod.GET)
	public JsonResult reportDetail(@PathVariable(name="report_id") Integer report_id,
		@PathVariable(name="approval_id") Integer approval_id,@PathVariable(name="type") Integer type){

		Map<String,Object> map=reportService.findReportById(report_id,approval_id,type);
		if (map.get("item")!=null) {
			return new JsonResult(true, map);
		}else{
			return new JsonResult(false, "结果空");
		}
	}
}