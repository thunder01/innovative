package com.innovative.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.service.DisassembleReportService;
import com.innovative.service.OrderService;
import com.innovative.service.ProjectApprovalService;
import com.innovative.utils.JsonResult;

/**
 * 订单模块 web层
 * */
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;//订单业务
	@Autowired
	private DisassembleReportService disassembleService;//拆解报告业务
	@Autowired
	private ProjectApprovalService projectApprovalService;//立项报告业务
		
	/**
	 * 处理需求池中的接单操作，需求工程师接单则向订单表中新增一条记录
	 * @param userid 需求工程师的id
	 * @param demandid 需求id
	 * @return
	 * */
	@RequestMapping(value="/receive/{userid}/{demandid}",method=RequestMethod.GET)
	public JsonResult insertOrder(@PathVariable(name="userid")Integer userid,
			@PathVariable(name="demandid")Integer demandid){
		orderService.insertOrder(userid,demandid);
		
		return new JsonResult(true, "订单生成成功");
	}
	
	/**
	 * 需求广场,寻源工程师接单
	 * @param approvalid 立项id
	 * @return userid 用户id
	 * */
	@RequestMapping(value="/sourceOrder/{userid}/{approvalid}",method=RequestMethod.GET)
	public JsonResult updateOrderLate_byId(@PathVariable(name="approvalid")Integer approvalid,
			@PathVariable(name="userid")Integer userid){	
			
			orderService.updateOrderLate_byId(userid,approvalid);//修改订单信息
			return new JsonResult(true, "生成订单成功");
	}
	
	/**
	 * 我的订单，展示用户的订单列表
	 * @param uesrid 用户id
	 * @return
	 * */
	@RequestMapping(value="/myorder/{userid}",method=RequestMethod.GET)
	public JsonResult selectMyOrder(@PathVariable(name="userid") Integer userid){
		List<Order> list=orderService.selectMyOrder(userid);
		if (list.size()>0) {
			return new JsonResult(true, list);
		}else {
			return new JsonResult(false, "结果为空");
		}
	}
	
	/**
	 * 订单详情
	 * @param orderid 订单id
	 * @return
	 * */
	@RequestMapping(value="/orderdetail/{orderid}",method=RequestMethod.GET)
	public JsonResult selectById(@PathVariable(name="orderid") Integer orderid){
		Order order=orderService.selectOrderById(orderid);
		/*判断结果是否为空*/
		if (null!=order) {
			return new JsonResult(true, order);
		}else {
			return new JsonResult(false, "结果为空");
		}
	}
	
	/**
	 * 需求拆解报告列表，查出次订单的拆解报告
	 * @param orderid 订单id
	 * */
	@RequestMapping(value="/disassembleDetail/{orderid}",method=RequestMethod.GET)
	public JsonResult selectDisassemble(@PathVariable(name="orderid") Integer orderid){
		/*查询需求报告信息*/
		DisassembleReport report=disassembleService.getDisassembleReportById(orderid);
		
		/*判断结果是否为空*/
		if (null!=report){
			return new JsonResult(true, report);
		}else{
			return new JsonResult(false, "结果为空");
		}
	}
	
	/**
	 * 添加立项表单
	 * @param orderid
	 * */
	@RequestMapping(value="approvalSave/{orderid}",method=RequestMethod.POST)
	public JsonResult approvalSave(@RequestBody ProjectApproval projectApproval,@PathVariable(name="orderid") Integer orderid){
		/*保存立项表单*/
		projectApprovalService.addProjectApproval(projectApproval,orderid);
		return new JsonResult(true, "添加成功");
	}
	
	/**
	 * 查询立项表单
	 * @param orderid 订单id
	 * @return
	 * */
	@RequestMapping(value="/approvalSelect/{orderid}",method=RequestMethod.GET)
	public JsonResult selectApproval(@PathVariable(name="orderid") Integer orderid){
		/*根据订单*/
		ProjectApproval projectApproval=projectApprovalService.getProjectApprovalById(orderid);
		
		/*判断结果是否为空*/
		if (projectApproval!=null) {
			return new JsonResult(true, projectApproval);
		}else {
			return new JsonResult(false, "没有结果");
		}
	}
	
	/**
	 * 需求广场列表
	 * @return
	 * */
	@RequestMapping(value="demandSquare",method=RequestMethod.GET)
	public JsonResult getProjectApprovals(){
		List<ProjectApproval> list=projectApprovalService.getProjectApprovals();
		/*判断结果*/
		if (list.size()>0) {
			return new JsonResult(true, list);
		}else{
			return new JsonResult(false, "没有结果");
		}
	}
	
	
}
