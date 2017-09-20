package com.innovative.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.service.DisassembleReportService;
import com.innovative.service.OrderService;
import com.innovative.service.ProjectApprovalService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

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
	 * 我的订单，展示用户的订单列表
	 * @param uesrid 用户id
	 * @return
	 * */
	@RequestMapping(value="/myorder",method=RequestMethod.GET)
	public JsonResult selectMyOrder(@RequestParam(name="userid") String userid,@RequestParam(name="offset",defaultValue="0") Integer offset){
		Integer page = offset/(new PageInfo().getPageSize()) +1;
		Map<String, Object> map=orderService.selectMyOrder(userid,page);
		
		return new JsonResult(true, map);
	}
	
	/**
	 * 订单详情
	 * @param orderid 订单id
	 * @return
	 * */
	@RequestMapping(value="/orderdetail/{orderid}",method=RequestMethod.GET)
	public JsonResult selectById(@PathVariable(name="orderid") Integer orderid){
		Map<String, Object> map=orderService.selectOrderByOrderId(orderid);
		/*判断结果是否为空*/
		if (map!=null) {
			if ((Integer)map.get("result")==1) {
				return new JsonResult(true, map);
			}
		}
		return new JsonResult(false, "结果为空");
	}
	
	/**
	 * 需求拆解、立项报告列表
	 * @param orderid 订单id
	 * */
	@RequestMapping(value="/disassembleDetail/{orderid}",method=RequestMethod.GET)
	public JsonResult selectDisassemble(@PathVariable(name="orderid") Integer orderid){
		/*查询需求报告信息*/
		Map<String, Object> map=orderService.getDisassembleAndApprovalListByOrderid(orderid);

		return new JsonResult(true, map);
	}
	
	/**
	 * 跳转立项表单(需要用户的id信息)
	 * */
	@RequestMapping(value="/toApprovalUpload/{orderid}",method = RequestMethod.GET)
	public JsonResult toUpload(@PathVariable(name="orderid") Integer orderid){
		Map<String,Object> map=new HashMap<>();
		map.put("orderid", orderid);
		return new JsonResult(true,map);
	}
	
	/**
	 * 添加立项表单
	 * @param orderid
	 * */
	@RequestMapping(value="/approvalSave",method=RequestMethod.POST)
	public JsonResult approvalSave(@RequestBody ProjectApproval projectApproval){
		/*保存立项表单*/
		Map<String, Object> map = projectApprovalService.addProjectApproval(projectApproval);
		
		return new JsonResult(true, map);
	}
	
	/**
	 * 发布需求
	 * @return
	 * */
	@RequestMapping(value="postApproval",method=RequestMethod.POST)
	public JsonResult postApproval(@RequestBody ProjectApproval pApproval){
		Map<String,Object> map=projectApprovalService.postApproval(pApproval);
		
		return new JsonResult(true, map);
	}
	
	/**
	 * 需求广场列表,寻源需求
	 * @return
	 * */
	@RequestMapping(value="/demandSquare",method=RequestMethod.GET)
	public JsonResult getProjectApprovals(@RequestParam(name="offset",defaultValue="0") Integer offset){
		Integer page = offset/(new PageInfo().getPageSize()) +1;
		
		Map<String, Object> map=projectApprovalService.getProjectApprovalList(page);
		/*判断结果*/
		return new JsonResult(true, map);
	}
	
	/**
	 * 寻源需求的每一项就是一个立项表单，查询立项表单（列表中的每一项）
	 * @param orderid 订单id
	 * @return
	 * */
	@RequestMapping(value="/approvalSelect/{app_id}",method=RequestMethod.GET)
	public JsonResult selectApproval(@PathVariable(name="app_id") Integer app_id){
		/*根据订单*/
		Map<String, Object> map=projectApprovalService.getProjectApprovalById(app_id);
		System.out.println(map);
		if(map.get("item")!=null){
			return new JsonResult(true, map);
		}else{
			return new JsonResult(false, "没有结果");
		}	
	}
	
	/**
	 * 需求广场,寻源工程师接单
	 * @param id 立项表单id
	 * @param source_id 寻源工程师的id
	 * */
	@RequestMapping(value="/sourceOrder",method=RequestMethod.POST)
	public JsonResult updateOrderLate_byId(@RequestBody ProjectApproval pApproval){		
		/*修改订单表的订单信息*/
		int flag=projectApprovalService.updateProjectApprovalReceive(pApproval.getId());
		if (flag==1) {
			return new JsonResult(true, pApproval);
		}
		return new JsonResult(false,"接单失败");
	}
	
	/**
	 * 处理需求池中的接单操作，需求工程师接单则向订单表中新增一条记录
	 * @param userid 需求工程师的id
	 * @param demandid 需求id
	 * @return
	 * */
	@RequestMapping(value="/receive",method=RequestMethod.POST)
	public JsonResult insertOrder(@RequestBody Order order){
		Map<String, Object> map=orderService.insertOrder(order.getCreate_byId(),order.getDemandId());
		return new JsonResult(true, map);
	}	
	
	
	/**
	 * 项目团队 
	 * @param demand_id 需求id
	 * @return
	 */
	/*@RequestMapping(value="/getTeam/{demand_id}",method=RequestMethod.GET)
	public JsonResult getTeam(@PathVariable(name="demand_id",required=true)Integer demand_id){
			Map<String, Object> map = orderService.getTeamByDemand_id(demand_id);
			if(map.size()==0){
				return new JsonResult(false, "没有查到");
			}
			return new JsonResult(true, map);
	}*/
	/**
	 * 项目同队里的需求工程师或者寻源工程师上传过的附件
	 * @param demand_id 需求id
	 * @param user_role 需求工程师是eoms 寻源工程师是se
	 * @return
	 */
	/*@RequestMapping(value="/getTeam/{demand_id}/{user_role}",method=RequestMethod.GET)
	public JsonResult getTeam(@PathVariable(name="demand_id")Integer demand_id,@PathVariable(name="user_role")String user_role){
		//需求工程师
		if("eoms".equals(user_role)){
			Map<String, Object> map = orderService.getDemandFile(demand_id);
			if(map.size()==0){
				return new JsonResult(false, "还没有上传拆解报告");
			}
			return new JsonResult(true, map);
		}
		//寻源工程师
		if("se".equals(user_role)){
			List<Report> list = orderService.getReportFiles(demand_id);
			if(null==list||list.size()==0){
				return new JsonResult(false, "没有上传");
			}
			return new JsonResult(true, list);
		}
		return new JsonResult(false, "传入参数有误");
	}
	*/
}
