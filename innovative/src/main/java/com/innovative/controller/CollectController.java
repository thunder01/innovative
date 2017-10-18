package com.innovative.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Collect;
import com.innovative.service.CollectService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

@RestController
@RequestMapping("/collect")
public class CollectController {
	@Resource
	private CollectService collectService;
	/**
	 * 添加征集
	 * @param collection
	 * @return
	 */
	@RequestMapping(value="/saveCollection",method=RequestMethod.POST)
	public JsonResult saveCollection(@RequestBody Collect collection){
		System.out.println(collection);
		Map<String, Object> map = collectService.saveCollection(collection); 
		int result = (int) map.get("result");
		if(result>0){
			return new JsonResult(true, map);
		}
		if(result==0){
			return new JsonResult(false, "添加失败");
		}
		return null;
	 }
	/**
	 * 分页查询全员征集
	 * @param offset 
	 * @return
	 */
	@RequestMapping(value="/listCollection",method=RequestMethod.GET)
	public JsonResult listCollection(@RequestParam(name="offset",defaultValue="0") Integer offset){
		Integer page = offset/(new PageInfo().getPageSize()) +1;
		Map<String, Object> map = collectService.listCollection(page);
		return new JsonResult(true, map);
	}
	/**
	 * 查询征集详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findCollection/{id}",method=RequestMethod.GET)
	public JsonResult findCollection(@PathVariable("id") Integer id){
		System.out.println(id);
		return new JsonResult(true, collectService.findCollectionById(id));
	}
}
