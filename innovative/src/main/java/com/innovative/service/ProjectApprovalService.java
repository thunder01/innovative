package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.User;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.UserDao;
import com.innovative.utils.PageInfo;
@Transactional
@Service("projectApprovalService")
public class ProjectApprovalService {
	
	@Resource
	private ProjectApprovalDao projectApprovalDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DisassembleReportDao disassembleReportDao;
	/**
	 * 判断拆解报告确认状态，并返给前段
	 * @param orderid
	 * @return
	 */
	public Map<String, Object> toApprovalUpload(Integer orderid){
		Map<String, Object> map=new HashMap<>();
		Order order=orderDao.selectOrderByOrderId(orderid);
		
		if (order!=null) {
			if (order.getConfirm_status()==1) {
				if (order.getPass_status()==1){
					map.put("message", "1");//拆解报告已通过
				}else{
					map.put("message", "2");//拆解报告未通过
				}		
			}else{
				map.put("message", "3");//拆解报告还未确认
			}	
		}else {
			map.put("message", "4");//需求不存在
		}
		map.put("orderid", orderid);
		
		return map;
	}
	
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public Map<String, Object> addProjectApproval(ProjectApproval projectApproval) {
		Map<String, Object> map=new HashMap<>();
		
		if (projectApproval!=null) {
			if ("1".equals(projectApproval.getMessage())) {
				/*添加一条立项表单信息*/
				Order order=orderDao.getOrderById(projectApproval.getOrder_id());
				projectApproval.setCreate_by(order.getCreate_byId());
				projectApprovalDao.addProjectApproval(projectApproval);
				map.put("message", "1");//拆解报告已通过
			}else if ("2".equals(projectApproval.getMessage())) {
				map.put("message", "2");//拆解报告未通过
			}else if ("3".equals(projectApproval.getMessage())) {
				map.put("message", "3");//拆解报告还未确认
			}else if ("4".equals(projectApproval.getMessage())) {
				map.put("message", "4");//需求不存在
			}		
			map.put("orderid", projectApproval.getOrder_id());			
		}	
		return map;
	}
	
	/**
	 * 发布立项信息
	 * @param pApproval
	 * @return
	 */
	public Map<String, Object> postApproval(ProjectApproval pApproval){
		Map<String, Object> map=new HashMap<>();
		
		/*根据订单id查出拆解报告信息*/
		DisassembleReport disassembleReport=disassembleReportDao.getDisassembleByOrderid(pApproval.getOrder_id());	
		map.put("disassemble", disassembleReport);
		
		/*根据订单id查询所有的立项表单*/
		List<ProjectApproval> list=projectApprovalDao.getApprovalListByOrderid(pApproval.getOrder_id());
		map.put("items", list);
		
		if (pApproval.getId()!=null) {
			int result = projectApprovalDao.postApproval(pApproval.getId());
			map.put("result", result);
			map.put("orderid", pApproval.getOrder_id());
		}else{
			map.put("result", "立项信息不存在");
		}
		return map;
	}
	
	/**
	 * 需求广场（寻源需求）查询所有的立项表单信息
	 * @param pageNum
	 * @return
	 */
	public Map<String , Object> getProjectApprovalList(Integer pageNum) {
		Map<String , Object> map=new HashMap<>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
        /*查询所有已发布的立项信息*/
        List<ProjectApproval> list=projectApprovalDao.selectSquare(pageInfo.getStartIndex(), pageInfo.getPageSize());
        
		for(ProjectApproval pro:list){
			if (pro!=null) {
				User user=userDao.getUser(pro.getSource_id());//获取寻源工程师的信息
				if (user!=null) {
					pro.setUserName(user.getNachn()+user.getVorna());
				}		
			}	
		}	
		map.put("items", list);
        map.put("totalCount",projectApprovalDao.getTotalCount());      
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
	
	/**
	 * 通过id来查询立项表单
	 * @param 订单id
	 * @return
	 */
	public Map<String, Object> getProjectApprovalById(Integer app_id) {
		Map<String, Object> map=new HashMap<>();
		
		/*查询对应的立项表单信息*/
		ProjectApproval projectApproval=projectApprovalDao.findApprovalById(app_id);
		
		/*补全需求方确认人的姓名*/
		String userId=projectApprovalDao.findUserIdByApp_id(app_id);//找出需求方确认人的id
		User user=userDao.getUser(userId);//然后查出其个人信息	
		if (user!=null&&projectApproval!=null) {
			projectApproval.setConfirmName(user.getNachn()+user.getVorna());//添加需求方确认人的姓名
		}
		
		/*补全寻源工程师的姓名信息*/
		String sourceId=projectApproval.getSource_id();//获取接单人的id，即是寻源工程师的id
		User user_source=userDao.getUser(sourceId);
		if (user_source!=null) {
			projectApproval.setUserName(user_source.getNachn()+user_source.getVorna());
		}
		int orderid=projectApproval.getOrder_id();
		
		map.put("orderid", orderid);
		map.put("approvalid",app_id);
		map.put("item", projectApproval);	

		return map;
	}
	
	/**
	 * 寻源工程师接单
	 * @param id 立项表单的id
	 * @return
	 */
	public Map<String, Object> updateProjectApprovalReceive(ProjectApproval projectApproval){
		int app_id=projectApproval.getId();
		Map<String, Object> map=getProjectApprovalById(app_id);
		if (projectApprovalDao.findApprovalById(app_id)!=null) {
			int status=projectApprovalDao.findSource_statusById(app_id);//查询立项表单的接单状态
			if (status==0) {//可接单
				projectApprovalDao.updateProjectApprovalReceive(app_id,projectApproval.getSource_id());
			}else{//已被接单
				map.put("message", "订单已经被人接了");
			}
		}
		return map;
	}
	
}
