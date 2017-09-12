package com.innovative.service;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.ProjectApproval;
import com.innovative.dao.ProjectApprovalDao;
@Transactional
@Service("projectApprovalService")
public class ProjectApprovalService {
	
	@Resource
	ProjectApprovalDao projectApprovalDao;
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public int addProjectApproval(ProjectApproval projectApproval) {
		return projectApprovalDao.addProjectApproval(projectApproval);
	}
	/**
	 * 通过id来查询立项表单
	 * @param id
	 * @return
	 */
	public ProjectApproval getProjectApprovalById(Integer id) {
		return projectApprovalDao.getProjectApprovalById(id);
	}
	/**
	 * 查询所有的立项表单
	 * @return
	 */
	public List<ProjectApproval> getProjectApprovals() {
		return projectApprovalDao.getProjectApprovals();
	}
	
}
