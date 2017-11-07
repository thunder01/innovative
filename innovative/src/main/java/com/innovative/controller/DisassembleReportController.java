package com.innovative.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Demand;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Message;
import com.innovative.bean.User;
import com.innovative.service.DisassembleReportService;
import com.innovative.service.MessageService;
import com.innovative.utils.JsonResult;

/**
 * 拆解报告的上传web层
 * @author fzy
 * @version 1.0
 * */
@RestController
@RequestMapping("/disassemble")
public class DisassembleReportController {
	@Autowired
    private DisassembleReportService disassembleService;
	@Autowired
	private MessageService messageService;
	/*跳转上传页面*/
	@RequestMapping(value="/toUpload/{orderid}",method = RequestMethod.GET)
	public JsonResult toUpload(@PathVariable(name="orderid") Integer orderid){
		Map<String,Object> map=disassembleService.toUpload(orderid);
		return new JsonResult(true,map);
	}
	
	/**
	 * 拆解报告上传之后，将上传记录添加到数据库，并向消息表添加一条记录
	 * @param report 拆解报告表单提交
	 * @param reportid 订单id
	 * @return
	 * */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public JsonResult saveDisassembleReport(@RequestBody DisassembleReport report){
		System.out.println(report);
		Map<String, Object> map= disassembleService.saveDisassembleReport(report,report.getOrder_id());
        if (map!=null) {
            return new JsonResult(true, map);
        }
        return new JsonResult(false, "报告上传失败！");        
	}

	/**
	 * 删除拆解报告
	 * @param order_id
	 * @param id 拆解报告的id
	 * @param delete_by 删除人
	 * @return
	 * */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public JsonResult deleteDisassembleReportById(@RequestBody DisassembleReport dReport){
		Map<String,Object> map=disassembleService.deleteDisassembleReportById(dReport);
		/*判断是否删除成功*/
		if ((Integer)map.get("result")==1) {
			return new JsonResult(true, map);
		}else{
			return new JsonResult(false, "删除失败");
		}	
	}
	
	/**
	 * 根据id查找拆解报告，编辑信息时先查
	 * @param id 拆解报告id
	 * @param order_id
	 * @return
	 * */
	@RequestMapping(value="/select/{disid}/{order_id}", method=RequestMethod.GET)
	public JsonResult a(@PathVariable(name="disid") Integer disid,@PathVariable(name="order_id") Integer order_id){
		Map<String,Object> map=disassembleService.getDisassembleReportById(disid,order_id);
		/*判断结果是否存在*/
		if (map!=null) {
			return new JsonResult(true, map);
		}else {
			return new JsonResult(false, "没有结果");
		}	
	}
	
	/**
	 * 修改拆解报告
	 * @param id 拆解报告id
	 * @param order_id 订单id
	 * @param 修改信息
	 * @return
	 * */
	@RequestMapping(value="/edit" ,method=RequestMethod.POST)
	public JsonResult updateDisassembleReport(@RequestBody DisassembleReport report) {
		System.out.println(report);
		Map<String,Object> map=disassembleService.updateDisassembleReportById(report);
		/*判断是否修改成功*/
		if ((Integer)map.get("result")==1) {
			return new JsonResult(true, map);
		}else {
			return new JsonResult(false, "修改失败");
		}	
	}
	
	/**
	 * 确认拆解报告详情页
	 * @param demand_id
	 * @return
	 */
	@RequestMapping(value="/toConfirm/{demand_id}",method=RequestMethod.GET)
	public JsonResult confirmDisassemble(@PathVariable(name="demand_id") Integer demand_id){
		Map<String,Object> map=disassembleService.confirmDisassemble(demand_id);
		
		return new JsonResult(true, map);
	}
	
	/**
	 * 确认拆解报告
	 * @param id 拆解报告id
	 * @param status 0未通过，1通过
	 * @return
	 */
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	public JsonResult confirmDisassembleStatus(@RequestBody DisassembleReport disassembleReport,HttpServletRequest req){
		User user = (User) req.getSession().getAttribute("userId");
		Map<String,Object> map=disassembleService.confirmDisassembleStatus(disassembleReport,user.getUserId());
		return new JsonResult(true, map);
	}
	
	//********************************************************************************************插入新的消息	
	/**
	 * 拆解报告上传之后，将上传记录添加到数据库，并向消息表添加一条记录
	 * @param report 拆解报告表单提交
	 * @param reportid 订单id
	 * @return
	 * */
	@RequestMapping(value = "/saveDisassemble", method = RequestMethod.POST)
	public JsonResult saveDisassemble(@RequestBody DisassembleReport report){
		System.out.println(report);
		Map<String, Object> map= disassembleService.saveDisassemble(report,report.getOrder_id());
        if (map!=null) {
        	Demand demand = (Demand) map.get("demand");
        	//需要确认是谁来审批，是下需求的人还是需求的审批人
        	messageService.insertMessage(demand.getCteateBy(), demand.getId()+"", "1", 3);
        	messageService.updateMsgCount(demand.getCteateBy());
            return new JsonResult(true, map);
        }
        return new JsonResult(false, "报告上传失败！");        
	}
	
}
