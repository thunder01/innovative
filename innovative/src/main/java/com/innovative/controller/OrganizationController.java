package com.innovative.controller;

import com.innovative.bean.Organization;
import com.innovative.service.OrganizationService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {


    @Autowired
    OrganizationService organizationService;

    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    @RequestMapping(value = "/getOrganization/{id}", method = RequestMethod.GET)
    public JsonResult getOrganization(@PathVariable(name = "id") Integer id){

        Organization organization = organizationService.getOrganization(id);
        if(organization != null){
            return new JsonResult(true, organization);
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
    public JsonResult getOrganizationList(@RequestParam(name="offset" ,defaultValue="0") Integer offset){

    	Integer page = offset/(new PageInfo().getPageSize()) +1;
        return new JsonResult(true, organizationService.getOrganizationList(page));
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
        return new JsonResult(true, "修改成功！");
    }












}
