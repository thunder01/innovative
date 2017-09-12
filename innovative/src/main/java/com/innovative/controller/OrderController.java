package com.innovative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Order;
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
	private OrderService orderService;
	@Autowired
	private DisassembleReportService disassembleService;
	@Autowired
	private ProjectApprovalService projectApprovalService;
		
	/**
	 * 处理需求池中的接单操作，需求工程师接单则向订单表中新增一条记录
	 * @param userid 需求工程师的id
	 * @param demandid 需求id
	 * @return
	 * */
	@RequestMapping(value="/receive/{userid}/{demandid}",method=RequestMethod.GET)
	public JsonResult insertOrder(@PathVariable(name="userid")Integer userid,
			@PathVariable(name="demandid")Integer demandid){
		//首先查询该需求是否可接
		
		//若可接则生成订单，否则返回"此需求已被接单"
		Order order = new Order();
		order.setCreate_byId(userid);
		order.setDemandId(demandid);
		orderService.insertOrder(order);
		
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
		int orderId=orderService.selectOrderIdByApproval(approvalid);//根据立项id查出订单id
		
		//然后判断此订单是否已有接单人
		
		Order order = new Order();
		order.setId(orderId);//补全订单信息
		order.setLate_byId(userid);

		orderService.updateOrderLate_byId(order);//修改订单信息
		return new JsonResult(true, "生成订单成功");
	}
	
	
	
	
}
