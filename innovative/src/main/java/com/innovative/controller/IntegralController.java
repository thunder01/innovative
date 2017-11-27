package com.innovative.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.service.IntegralService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

@RestController
@RequestMapping("/integral")
public class IntegralController {
	@Resource
	private IntegralService integralService;
	
	/**
	 * 获取所有积分详情
	 * @param userid
	 * @param type
	 * @param offset
	 * @return
	 */
	@RequestMapping(value="/getIntegral",method=RequestMethod.GET)
	public JsonResult getMyIntegral(@RequestParam(name="userId")String userid,@RequestParam(name="type",defaultValue="1")int type,
			@RequestParam(name="offset",defaultValue="0") Integer offset){
		Integer pageNum = offset/(new PageInfo().getPageSize()) +1;
		Map<String, Object> map = integralService.getIntegral(userid, type, pageNum);
		return new JsonResult(true, map);
	}
}
