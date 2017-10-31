package com.innovative.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * 新增订单信息 ，需求池接单
	 * @param userid 用户的id
	 * @param demandid 需求id
	 * @return 
	 * */
	public Map<String, Object> insertOrder(String userid,Integer demandid){
		Map<String, Object> map=new HashMap<>();
	
		//首先查询该需求是否可接
		Integer orderid=orderDao.getOrderIdByDemandId(demandid);
		Demand demand=demandDao.getDemand(demandid);//查询需求信息
		if (orderid==null) {//若是订单不存在，则新产生一条订单信息；若订单已经存在，说明此需求已经被人接单了
			orderDao.insertOrder(demandid,userid);
		}		
	
		map.put("demand", demand);
		map.put("userid", userid);				
		return map;
	}
	
	/**
	 * 根据当前用户id查询我的订单，用户需求下单之后经过审批进入需求池，需求工程师接单之后才会在我的订单展示，
	 * 注意：此时展示的还是客户的需求下单信息
	 * @param userid 用户id
	 * @param pageNum分页信息
	 * @return
	 * */
	public Map<String,Object> selectMyOrder(String userid,Integer pageNum,int type){
		Map<String , Object> map=new HashMap<>();		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		/*首先根据用户id获取用户信息*/
		User user=userDao.getUser(userid);
		List<Demand> list=null;
		List<ProjectApproval> list2 = null;
		int total = 0;
		if (user!=null) {
			if(type==1){/*查询该用户的所有需求订单*/
				list=demandDao.getMyDemand(pageInfo.getStartIndex(), pageInfo.getPageSize(), userid);
				total = demandDao.getMyDemandTotal(userid);
				for (Demand demand : list) {
					if(demand!=null){
						demand.setOrderid(orderDao.getOrderIdByDemandId(demand.getId()));
					}
				}
				System.out.println(list);
				map.put("items", list);
			}
			if(type==2){
				//需求工程师的订单
				list = demandDao.getAllMyDemand(pageInfo.getStartIndex(), pageInfo.getPageSize(), userid);	
				total = demandDao.getAllMyDemandCount(userid);
				for (Demand demand : list) {
					if(demand!=null){
						demand.setOrderid(orderDao.getOrderIdByDemandId(demand.getId()));
					}
				}
				map.put("items", list);
			}
			if(type==3){
				//寻源工程师的订单
				list2 = projectApprovalDao.selectApprovalListByUserId(userid,pageInfo.getStartIndex(), pageInfo.getPageSize());
				total = projectApprovalDao.getSourceCount(userid);
				for (ProjectApproval projectApproval : list2) {
					if(projectApproval!=null){
						projectApproval.setCreate_date(projectApproval.getCreate_date().substring(0, 16));
					}
				}
				map.put("items", list2);
			}
			
		}
		map.put("user", user); 
		map.put("userid", userid);  
		map.put("totalCount", total);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize()); 
		return map;
	}
	
	/**
	 * 根据订单id查询订单的详细信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Map<String, Object> selectOrderByOrderId(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		int demandid=orderDao.getDemandIdByOrderId(orderid);//根据需求id查出对应的订单id
		Demand demand=demandDao.getDemand(demandid);//根据需求id查询需求的信息
		User user=null;
		if (demand!=null) {
			user=userDao.getUser(demand.getCteateBy());//查询需求下单人的详细
		}
		
		map.put("id", demandid);
		map.put("item", demand);
		map.put("user", user);
		map.put("orderid", orderid);			
		return map;
	}
	
	/**
	 * 根据订单id获取拆解报告信息和立项表单列表
	 * @param orderid 订单id
	 * @return
	 * */
	public Map<String, Object> getDisassembleAndApprovalListByOrderid(Integer orderid){
		long start=System .currentTimeMillis();
		
		Map<String, Object> map=new HashMap<>();		
		/*根据订单id查出拆解报告信息*/
		DisassembleReport disassembleReport=disassembleReportDao.getDisassembleByOrderid(orderid);	
		if (disassembleReport!=null) {
			/*查出拆解报告所拥有的文件*/
			String fileid=disassembleReport.getFileid();
			List<FileBean> listFiles=fileDao.getFileById(fileid, "disassemble");
			disassembleReport.setList(listFiles);		
		}else{
			map.put("message1", "没有拆解报告");
		}		
		/*根据订单id查询所有的立项表单*/
		List<ProjectApproval> list=projectApprovalDao.getApprovalListByOrderid(orderid);
		
		map.put("disassemble", disassembleReport);
		map.put("items", list);		
		map.put("orderid", orderid);
		Order order = orderDao.getOrderById(orderid);
		map.put("order", order);
		long time=System.currentTimeMillis()-start;
		System.out.println(time);
		return map;
	}
	
	/**
	 * 项目团队
	 * @param order_id 订单id
	 * @return
	 */
	public Map<String, Object> getTeamByOrderId(Integer order_id){
		Map<String, Object> map=new HashMap<>();		
		/*1 、通过订单的id查询需求工程师的id */
		String demandMasterId=orderDao.findCreate_by_idById(order_id);	
		/*2、 通过订单的id查询次项目所有参与的寻源工程师*/
		String[] sourceMasterId=projectApprovalDao.findSource_idByOrder_id(order_id);			
		/*3、获取用户信息*/
		User demandMaster = userDao.getUser(demandMasterId);//需求工程师		
		List<User> list = new ArrayList<User>();//寻源工程师
		for(int i=0;i<sourceMasterId.length;i++){
			User source=userDao.getUser(sourceMasterId[i]);
			if (sourceMasterId[i]!=null) {
				list.add(source);
			}	
		}
		
		map.put("demandMaster", demandMaster);
		map.put("source", list);
		map.put("orderid", order_id);
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
		orderDao.proEvaluate(order);//添加评分信息		
		map.put("orderid", order.getId());
		return map;
	}
}
