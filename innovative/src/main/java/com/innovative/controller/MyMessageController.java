package com.innovative.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.Message;
import com.innovative.bean.MsgCount;
import com.innovative.bean.User;
import com.innovative.dao.MsgCountDao;
import com.innovative.service.DemandService;
import com.innovative.service.MessageService;
import com.innovative.service.MyMessageService;
import com.innovative.service.OrderService;
import com.innovative.service.UserService;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;
/**
 * 我的消息
 * @author huang
 *
 */
@RestController
@RequestMapping("/myMessage")
public class MyMessageController {
	
	@Resource
	private MessageService messageService;
	@Resource
	private DemandService demandService;
	@Resource
	private UserService userService;
	@Resource
	private OrderService orderService;
	@Resource
	private MyMessageService myMessageService;
	@Resource
	private MsgCountDao msgCountDao;
	
	/**
	 * 个人信息里的消息
     * @param offset 分页的偏移量
     * @param userid 用户的id
     * @param notice 消息的类型（1通知，2已办，3待办）
	 * @return
	 */
	@RequestMapping(value = "/getMsg/{userid}/{notice}",method = RequestMethod.GET)
	public JsonResult getMsg(@RequestParam(name="offset",defaultValue="0") Integer offset,@PathVariable(name="userid") String userid,
    		@PathVariable(name="notice") int notice){
		Integer page = offset/(new PageInfo().getPageSize()) +1;
    	Message message = new Message();
    	message.setUserid(userid);
    	message.setNotice(notice);
		Map<String, Object> map = messageService.getMessage(message, page);
		return new JsonResult(true, map);
	}
	/**
	 * 个人信息里的需求
     * @param offset 分页的偏移量
     * @param userid 用户的id
	 * @return
	 */
	@RequestMapping(value = "/getDemand/{userid}",method = RequestMethod.GET)
	public JsonResult getDemand(@RequestParam(name="offset",defaultValue="0") Integer offset,@PathVariable(name="userid") String userid){
		Integer page = offset/(new PageInfo().getPageSize()) +1;
		Map<String, Object> map = myMessageService.getMyDemand(userid, page);
		return new JsonResult(true, map);
	}
	/**
	 * 个人信息展示
	 * @param userid 用户的id
	 * @return
	 */
	@RequestMapping(value = "/getPerson/{userid}",method = RequestMethod.GET)
	public JsonResult getPerson(@PathVariable(name="userid") String userid){
		Map<String, Object> map =new HashMap<>();
		User user = userService.getUser(userid);
		map.put("user", user);
		MsgCount msgCount = msgCountDao.showMsgCount(userid);
		map.put("label", msgCount.getLabel());
		return new JsonResult(true, map);
	}
	/**
	 * 更新用户标签
	 * @param msgCount
	 * @return
	 */
	@RequestMapping(value = "/updatePerson",method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updatePerson(@RequestBody MsgCount msgCount){
		int result = msgCountDao.updateLabel(msgCount);
		if(result>0){
			return new JsonResult(true, msgCount);
		}	
		return new JsonResult(false, "更新用户标签失败");
	}
	
}
