package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Demand;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.User;
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
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public Map<String, Object> addProjectApproval(ProjectApproval projectApproval,Integer orderid) {
		Map<String, Object> map=new HashMap<>();
		/*添加一条立项表单信息*/
		int approvalId=projectApprovalDao.addProjectApproval(projectApproval);
		/*往订单表中添加一条数据*/
		Order order=new Order();
		order.setId(orderid);
		order.setApprovalId(projectApproval.getId());
		orderDao.updateProjectApproval(order);
		
		map.put("orderid", orderid);
		return map;
	}
	/**
	 * 通过id来查询立项表单
	 * @param 订单id
	 * @return
	 */
	public ProjectApproval getProjectApprovalById(Integer orderid) {
		return projectApprovalDao.getProjectApprovalById(orderid);
	}
	/**
	 * 查询所有的立项表单
	 * @return
	 */
	public Map<String , Object> getProjectApprovals(Integer pageNum) {
		Map<String , Object> map=new HashMap<>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
        List<ProjectApproval> list=projectApprovalDao.getProjectApprovals();
		for(ProjectApproval pro:list){
			if (pro!=null) {
				Order order=orderDao.selectOrderByApproval_id(pro.getId());
				User user=userDao.getUser(order.getLate_byId());
				System.out.println(user);
				if (user!=null) {
					pro.setUserName(user.getUserName());
				}		
			}	
		}
		
		map.put("items", list);
        map.put("totalCount", list.size());      
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
	/**
	 * 通过id查询立项表单的状态  0是未接单，1是已接单
	 * @param id
	 * @return 立项表单的状态
	 */
	public int getProjectApprovalStatusById(Integer id){
		return projectApprovalDao.getProjectApprovalStatusById(id);
	}
	
}
