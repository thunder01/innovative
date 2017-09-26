package com.innovative.service;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innovative.bean.Demand;
import com.innovative.bean.DisassembleReport;
import com.innovative.bean.FileBean;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.Report;
import com.innovative.bean.User;
import com.innovative.dao.DemandDao;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.ReportDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.PageInfo;


/**
 * 处理订单业务逻辑
 * @author fzy
 * */
@Service
public class OrderService {
	@Autowired 
	private OrderDao orderDao;
	@Autowired
	private ProjectApprovalDao projectApprovalDao;
	@Autowired
	private DisassembleReportDao disassembleReportDao;
	@Autowired
	private DemandDao demandDao;
	@Autowired 
	private UserDao userDao;
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private FileDao fileDao;
	
	/**
	 * 新增订单信息 需求池接单
	 * @param userid 用户的id
	 * @param demandid 需求id
	 * @return 受影响的行数
	 * */
	public Map<String, Object> insertOrder(String userid,Integer demandid){
		Map<String, Object> map=new HashMap<>();
	
		//首先查询该需求是否可接
		Demand demand=demandDao.getDemand(demandid);
		
		if (demand!=null/*&&demand.get接单状态==0*/ ) {
			//若可接则生成订单,并将需求的接单状态改为1
			/*demandDao.updateDemand(demand);*/
			
			int flag=orderDao.insertOrder(demandid,userid);
			if (flag!=0) {
				/*demandDao.update接单状态;*/
				map.put("demand", demand);
				map.put("userid", userid);
			}else{
				map.put("message", "生成订单失败");
			}
		}else{
			map.put("message", "此需求已经有人接单");
		}		
		return map;
	}
	
	/**
	 * 根据当前用户id查询我的订单
	 * @param userid 用户id
	 * @param pageNum分页信息
	 * @return
	 * */
	public Map<String,Object> selectMyOrder(String userid,Integer pageNum){
		Map<String , Object> map=new HashMap<>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		/*首先根据用户id获取用户信息*/
		User user=userDao.getUser(userid);
		
		if (user!=null) {
			//String role=user.getRoleId();//获取用户角色
			//if ("需求工程师".equals(role)) {
				/*根据用户id获取其所有的订单*/
				List<Order> list=orderDao.selectOrderListByUserId(userid,pageInfo.getStartIndex(), pageInfo.getPageSize());
				map.put("totalCount", orderDao.getTotalCOuntMyorder(userid));
				if (list.size()>0) {
					for(Order order:list){
						Demand demand=demandDao.getDemand(order.getDemandId());
						order.setDemand(demand);
					}
				}else{
					map.put("message", "您还没有订单信息");
				}
				map.put("items", list);
			/*}else if ("寻源工程师".equals(role)) {
				查出其所有接过的立项表单
				List<ProjectApproval> listApp=projectApprovalDao.selectApprovalListByUserId(userid,pageInfo.getStartIndex(), pageInfo.getPageSize());
				map.put("totalCount", projectApprovalDao.getSourceCount(userid));
				if (listApp.size()==0) {
					map.put("message", "您还没有接过订单");	
				}
				map.put("items", listApp);
			}else {
				map.put("message", "不是相关角色");
			}*/
		}else{
			map.put("message", "用户不存在");
		}
		map.put("user", user);     
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
	
	/**
	 * 根据订单id，查询订单信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Map<String, Object> selectOrderByOrderId(Integer orderid){
		Map<String, Object> map=new HashMap<>();

		Order order=orderDao.selectOrderByOrderId(orderid);//根据订单id查询出订单信息
		if(order!=null){
			User user=userDao.getUser(order.getCreate_byId());
			Demand demand=demandDao.getDemand(order.getDemandId());
			order.setDemand(demand);
			map.put("item", order);
			map.put("user", user);
			map.put("orderid", orderid);
			map.put("result", 1);
		}else{
			map.put("result", 0);//结果为空
		}
		return map;
	}
	
	/**
	 * 根据订单id获取拆解报告信息和立项表单列表
	 * @param orderid 订单id
	 * @return
	 * */
	public Map<String, Object> getDisassembleAndApprovalListByOrderid(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		
		/*根据订单id查出拆解报告信息*/
		DisassembleReport disassembleReport=disassembleReportDao.getDisassembleByOrderid(orderid);	
		if (disassembleReport!=null) {
			/*获取文件id*/
			String fileid=disassembleReport.getFileid();
			List<FileBean> listFiles=fileDao.getFileById(fileid, "disassemble");
			disassembleReport.setList(listFiles);		
		}else{
			map.put("message1", "没有拆解报告");
		}
		map.put("disassemble", disassembleReport);
		
		/*根据订单id查询所有的立项表单*/
		List<ProjectApproval> list=projectApprovalDao.getApprovalListByOrderid(orderid);
		if (list.size()==0) {
			map.put("message2", "没有立项表单");			
		}
		map.put("items", list);
		
		map.put("orderid", orderid);
		return map;
	}
	
	/**
	 * 项目团队
	 * @param order_id
	 * @return
	 */
	public Map<String, Object> getTeamByOrderId(Integer order_id){
		Map<String, Object> map=new HashMap<>();
			
		User demandMaster;
		try {
			/*1 通过订单的id查询需求工程师的id */
			String demandMasterId=orderDao.findCreate_by_idById(order_id);
			
			/*2  通过订单的id查询寻源工程师的id数组*/
			String[] sourceMasterId=projectApprovalDao.findSource_idByOrder_id(order_id);
			
			/*3 获取用户信息*/
			demandMaster = userDao.getUser(demandMasterId);
			
			List<User> list = new ArrayList<User>();
			for(int i=0;i<sourceMasterId.length;i++){
				User source=userDao.getUser(sourceMasterId[i]);
				if (sourceMasterId[i]!=null) {
					list.add(source);
				}	
			}
			
			map.put("demandMaster", demandMaster);
			map.put("source", list);
			map.put("orderid", order_id);
		} catch (Exception e) {
			map.put("message", "结果错误");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 过程纪要
	 * @param order_id
	 * @return
	 */
	public Map<String, Object> rankReport(Integer order_id){
		Map<String, Object> map=new HashMap<>();
		
		/*通过订单id来找所有报告并按创建时间排序*/
		List<Report> list=reportDao.rankReport(order_id);
		
		for(Report report:list){
			if ("1".equals(report.getType())) {
				report.setTypeName("方案线索");
			}else if ("2".equals(report.getType())) {
				report.setTypeName("寻源报告");
			}else if ("3".equals(report.getType())) {
				report.setTypeName("会议记录");
			}else if ("4".equals(report.getType())) {
				report.setTypeName("出差报告");
			}else if ("5".equals(report.getType())) {
				report.setTypeName("问题记录");
			}else if ("6".equals(report.getType())) {
				report.setTypeName("项目总结");
			}
		}
		map.put("items", list);
		map.put("orderid", order_id);
		return map;
	}
	
	/**
	 * 项目评分
	 * @param order
	 * @return
	 */
	public Map<String, Object> projectGrade(Order order){
		Map<String, Object> map=new HashMap<>();
		
		int flag=orderDao.proEvaluate(order);//添加评分信息
		
		if (flag==1) {
			map.put("message", "成功");
		}else{
			map.put("message", "失败");
		}
		
		map.put("orderid", order.getId());
		return map;
	}
}
