package com.innovative.controller;

import com.innovative.bean.Association;
import com.innovative.bean.User;
import com.innovative.service.AssociationService;
import com.innovative.service.ResourceCommentService;
import com.innovative.service.UserService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/association")
public class AssociationController extends BaseController {


    @Autowired
    AssociationService associationService;
    @Autowired
    ResourceCommentService resourceCommentService;
    @Autowired
    UserService userService;
    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/getAssociation/{id}", method = RequestMethod.GET)
    public JsonResult getAssociation(@PathVariable(name = "id") String id) {

        Association association = associationService.getAssociation(id);
        if (association != null) {
            return new JsonResult(true, association);
        }
        return new JsonResult(false, "参数不合法");
    }
    
    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/deleteAssociation", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteAssociation(@RequestBody Association association) {
    	String sectors = null;
    	if(association.getSectors() != null &&association.getSectors().length>0 )
    		 sectors = association.getSectors()[0];
       boolean flag = associationService.deleteAssociation(association.getId());
        if (flag) {
            return new JsonResult(true, associationService.getAssociationList( 1,sectors,""));
        }
        return new JsonResult(false, "参数不合法");
    }





    /**
     * 行业协会列表页
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getAssociationList", method = RequestMethod.GET)
    public JsonResult getAssociationList(@RequestParam(name="offset",defaultValue="0") Integer offset,@RequestParam(name="sectors",required=false) String sectors,@RequestParam(name="keyword",required=false) String keyword) {
    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, associationService.getAssociationList( page,sectors,keyword));
    }




    /**
     * 添加行业协会
     * @param association 行业协会bean
     * @return
     */
    @RequestMapping(value = "/addAssociation", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addAssociation(@RequestBody Association association,HttpServletRequest req) {

    	//获取当前登录用户 
    	association.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        if (!associationService.addAssociation(association)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }






 /**
  * 修改协会
  * @param association  协会bean
  * @param req
  * @return
  */
    @RequestMapping(value = "/updateAssociation", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateAssociation(@RequestBody Association association,HttpServletRequest req) {


        if(associationService.getAssociation(association.getId()) == null){
            return new JsonResult(false, "此记录不存在");
        }
        //获取当前登录用户 并修改登录人
        association.setUpdatedBy(CookiesUtil.getCookieValue(req,"user_name"));

        if (!associationService.updateAssociation(association)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        Association associati = associationService.getAssociation(association.getId());
        return associati != null ? new JsonResult(true, associati) : new JsonResult(false, "获取协会失败");
    }







    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public JsonResult getAssociationByid(@PathVariable(name = "id") String id) {

        Association association = associationService.getAssociation(id);
        if (association != null) {
        	Map<String, Object> map = resourceCommentService.getResourceComment(association.getId(), 3);
            map.put("association", association);
            User user = userService.getUser(association.getCreatedBy());
            if(user!=null){
            	map.put("user", user);
            }
            return new JsonResult(true, map);
        }
        return new JsonResult(false, "参数不合法");
    }



}
