package com.innovative.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.ProjectApproval;
import com.innovative.service.ProjectApprovalService;
import com.innovative.utils.JsonResult;

@RestController
@RequestMapping("/projectApproval")
public class ProjectApprovalController {
	@Resource
	ProjectApprovalService projectApprovalService;
	/**
	 * 添加立项表单
	 * @param projectApproval
	 * @param req
	 * @return
	 */
	
	@RequestMapping(value = "/addProjectApproval", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
	public JsonResult addProjectApproval(@RequestBody ProjectApproval projectApproval,HttpServletRequest req){
		try {
			projectApprovalService.addProjectApproval(projectApproval);
			return new JsonResult(true, "新增成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "新增失败，请重试！");
		}
	}
	
	/**
	 * 通过id删除立项表单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteProjectApproval/{id}", method = RequestMethod.GET)
	public JsonResult deleteProjectApproval(@PathVariable(name = "id") Integer id){
		try {
			projectApprovalService.deleteProjectApproval(id);
			return new JsonResult(true, "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "删除失败，请重试！");
		}
	}
	/**
	 * 通过id查询立项表单
	 * @return
	 */
	@RequestMapping(value = "/getProjectApproval/{id}", method = RequestMethod.GET)
	public JsonResult getProjectApprovalById( @PathVariable(name = "id") Integer id){
		ProjectApproval projectApproval = projectApprovalService.getProjectApprovalById(id);
		if(projectApproval==null){
			return new JsonResult(false, "参数不合法");
		}
		return new JsonResult(true, projectApproval);
		
	}
	/**
	 * 查询所有的立项表单
	 * @return
	 */
	@RequestMapping(value = "/getProjectApprovalList", method = RequestMethod.GET)
	public JsonResult getProjectApprovalList(){
		List<ProjectApproval> listProjectApproval = projectApprovalService.getProjectApprovals();
		if(listProjectApproval.size()>0){
			return new JsonResult(true, listProjectApproval);
		}
		return new JsonResult(false, "目前没有立项表单");
	}
	
	
}
