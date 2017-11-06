package com.innovative.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.ResourceComment;
import com.innovative.service.ResourceCommentService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
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
	public Object updateComments(@RequestBody ResourceComment resourceComment,@RequestParam(name="offset",defaultValue="0") Integer offset){
		ResourceComment comment = resourceCommentService.update(resourceComment);
		return getComments(offset, comment.getType(), comment.getResource_id());
	}
	/**
	 * 评论页分页
	 * @param offset
	 * @param type
	 * @param resource_id
	 * @return
	 */
	@RequestMapping(value = "/getComments/{type}/{resource_id}", method = RequestMethod.GET)
	public JsonResult getComments(@RequestParam(name="offset",defaultValue="0") Integer offset,@PathVariable(name = "type") Integer type,
			@PathVariable(name = "resource_id") String resource_id){
		Integer pageNum = offset/(new PageInfo().getPageSize()) +1;
		Map<String, Object> map = resourceCommentService.getComments(type, resource_id, pageNum);
		return new JsonResult(true, map);
	}
}
