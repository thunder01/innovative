package com.innovative.controller;

import com.innovative.bean.Organization;
import com.innovative.bean.User;
import com.innovative.service.OrganizationService;
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
@RequestMapping("/organization")
public class OrganizationController extends BaseController {


    @Autowired
    OrganizationService organizationService;
    @Autowired
    ResourceCommentService resourceCommentService;
    @Autowired
    UserService userService;

    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    @RequestMapping(value = "/getOrganization/{id}", method = RequestMethod.GET)
    public JsonResult getOrganization(@PathVariable(name = "id") String id){

        Organization organization = organizationService.getOrganization(id);
        if(organization != null){
            return new JsonResult(true, organization);
        }
        return new JsonResult(false, "参数不合法");
    }
    
    /**
     * 根据删除组织机构
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteOrganization(@RequestBody Organization organization) {
    	String sectors = null;
    	if(organization.getSectors() != null &&organization.getSectors().length>0 )
    		 sectors = organization.getSectors()[0];
       boolean flag = organizationService.deleteOrganization(organization.getId());
        if (flag) {
            return new JsonResult(true, organizationService.getOrganizationList(1,sectors,""));
        }
        return new JsonResult(false, "参数不合法");
    }




    /**
     * 创新资源列表页（根据行业领域查询对应机构的列表页）
     * @param sectors 行业领域（多个用逗号隔开）
     * @param pageNum 页数（默认为1）
     * @return
     */
    @RequestMapping(value = "/getOrganizationList", method = RequestMethod.GET)
    public JsonResult getOrganizationList(@RequestParam(name="offset" ,defaultValue="0") Integer offset,@RequestParam(name="sectors",required=false) String sectors,@RequestParam(name="keyword",required=false) String keyword){

    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, organizationService.getOrganizationList(page,sectors,keyword));
    }





    /**
     * 添加机构信息
     * @param organization 机构参数bean
     * @return
     */
    @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addOrganization(@RequestBody Organization organization,HttpServletRequest req ){

    	organization.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        if(!organizationService.addOrganization(organization)){
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }





    /**
     * 修改机构信息
     * @param organization 机构参数bean
     * @return
     */
    @RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateOrganization(@RequestBody Organization organization,HttpServletRequest req) {

    	organization.setUpdatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        if(organizationService.getOrganization(organization.getId()) == null){
            return new JsonResult(false, "此记录不存在");
        }

        if (!organizationService.updateOrganization(organization)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        Organization organ = organizationService.getOrganization(organization.getId());
        return organ != null ? new JsonResult(true, organ) : new JsonResult(false, "获取对象失败！");
    }







    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public JsonResult getOrganizationByid(@PathVariable(name = "id") String id){

        Organization organization = organizationService.getOrganization(id);
        if(organization != null){
        	Map<String, Object> map = resourceCommentService.getResourceComment(organization.getId(), 2);
            map.put("organization", organization);
            User user = userService.getUser(organization.getCreatedBy());
            if(user!=null){
            	map.put("user", user);
            }
            return new JsonResult(true, map);
        }
        return new JsonResult(false, "参数不合法");
    }




}
