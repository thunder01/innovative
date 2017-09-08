package com.innovative.dao;

import java.util.List;

import com.innovative.bean.ProjectApproval;

public interface ProjectApprovalDao {
	/**
	 * 添加一个立项表单
	 * @param projectApproval
	 */
	public void addProjectApproval(ProjectApproval projectApproval);
	/**
	 * 删除一个立项表单
	 * @param id
	 */
	public void deleteProjectApproval(Integer id);
	/**
	 * 通过id来查询立项表单
	 * @param id
	 * @return
	 */
	public ProjectApproval getProjectApprovalById(Integer id);
	/**
	 * 查询所有的立项表单
	 * @return
	 */
	public List<ProjectApproval> getProjectApprovals();
	
}
