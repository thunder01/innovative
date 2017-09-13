package com.innovative.service;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;


import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
@Transactional
@Service("projectApprovalService")
public class ProjectApprovalService {
	
	@Resource
	private ProjectApprovalDao projectApprovalDao;
	@Autowired
	private OrderDao orderDao;
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public int addProjectApproval(ProjectApproval projectApproval,Integer orderid) {
		/*添加一条立项表单信息*/
		int approvalId=projectApprovalDao.addProjectApproval(projectApproval);
		/*往订单表中添加一条数据*/
		Order order=new Order();
		order.setId(orderid);
		order.setApprovalId(projectApproval.getId());
		return orderDao.updateProjectApproval(order);
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
	public List<ProjectApproval> getProjectApprovals() {
		return projectApprovalDao.getProjectApprovals();
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
