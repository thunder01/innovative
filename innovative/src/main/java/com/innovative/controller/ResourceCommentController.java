package com.innovative.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.ResourceComment;
import com.innovative.service.ResourceCommentService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
@CrossOrigin
@RestController
@RequestMapping("resourceComment")
public class ResourceCommentController {
	@Resource
	private ResourceCommentService resourceCommentService;
	
	/**
	 * 添加一个评论
	 * @param resourceComment
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public JsonResult addResourceComment(@RequestBody ResourceComment resourceComment){
		Map<String, Object> map = resourceCommentService.addResourceComment(resourceComment);
		return new JsonResult(true, map);
	}
	/**
	 * 详情页点赞
	 * @param resourceComment
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@RequestBody ResourceComment resourceComment){
		Map<String, Object> map = resourceCommentService.updateResourceComment(resourceComment);
		return new JsonResult(true, map);
	}
	/**
	 * 评论页点赞
	 * @param resourceComment
	 * @return
	 */
	@RequestMapping(value = "/updateComments", method = RequestMethod.POST)
	public JsonResult updateComments(@RequestBody ResourceComment resourceComment,@RequestParam(name="offset",defaultValue="0") Integer offset){
		ResourceComment comment = resourceCommentService.update(resourceComment);
		return new JsonResult(true, comment);
	}
	/**
	 * 评论页分页
	 * @param offset
	 * @param type
	 * @param resource_id
	 * @return
	 */
	@RequestMapping(value = "/getComments/{type}/{resource_id}", method = RequestMethod.GET)
	public JsonResult getComments(@RequestParam(name="pageNum",defaultValue="1") Integer pageNum ,@PathVariable(name = "type") Integer type,
			@PathVariable(name = "resource_id") String resource_id){
		Map<String, Object> map = resourceCommentService.getComments(type, resource_id, pageNum);
		return new JsonResult(true, map);
	}
}
