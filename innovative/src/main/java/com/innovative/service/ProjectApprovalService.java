package com.innovative.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.innovative.bean.ProjectApproval;
import com.innovative.dao.ProjectApprovalDao;
@Service
public class ProjectApprovalService {
	
	@Resource
	ProjectApprovalDao projectApprovalDao;
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public void addProjectApproval(ProjectApproval projectApproval) {
		projectApprovalDao.addProjectApproval(projectApproval);
	}
	/**
	 * 删除一个立项表单
	 * @param id
	 */
	public void deleteProjectApproval(Integer id) {
		projectApprovalDao.deleteProjectApproval(id);
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
