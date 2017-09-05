package com.innovative.controller;

import com.innovative.bean.Association;
import com.innovative.service.AssociationService;
import com.innovative.utils.BaseController;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
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
@RequestMapping("/association")
public class AssociationController extends BaseController {


    @Autowired
    AssociationService associationService;

    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/getAssociation/{id}", method = RequestMethod.GET)
    public JsonResult getAssociation(@PathVariable(name = "id") Integer id) {

        Association association = associationService.getAssociation(id);
        if (association != null) {
            return new JsonResult(true, association);
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
    public JsonResult getAssociationList(@RequestParam(name="page",defaultValue="1") Integer page) {

        if (page <= 0) {
            return new JsonResult(false, "参数不合法！");
        }
        return new JsonResult(true, associationService.getAssociationList( page));
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
        return new JsonResult(true, "修改成功！");
    }











}
