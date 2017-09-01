package com.innovative.controller;

import com.innovative.bean.Association;
import com.innovative.service.AssociationService;
import com.innovative.utils.BaseController;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


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
    @RequestMapping(value = "/getAssociation", method = RequestMethod.GET)
    public JsonResult getAssociation(@RequestParam(name = "id") Integer id) {

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
    public JsonResult getAssociationList(@RequestParam(name = "sectors", required = false) String sectors,
                                         @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {

        if (pageNum < 1) {
            return new JsonResult(false, "参数不合法！");
        }
        return new JsonResult(true, associationService.getAssociationList(sectors, pageNum));
    }





    /**
     * 添加行业协会
     * @param name              协会名称
     * @param logo              协会徽标
     * @param introduction         协会简介
     * @param nature          协会性质
     * @param duration           协会合作期限
     * @param availableResources 协会可用资源
     * @param cooperationStatus    合作状态
     * @param contact           联系方式
     * @param website           网站链接
     * @param sectors          部门，领域
     * @param tags                标签
     * @param rank              等级
     * @param isActive          是否在线
     * @param id                    id
     * @param file                  文件
     * @return
     */
    @RequestMapping(value = "/addAssociation", method = RequestMethod.POST)
    public JsonResult addAssociation(@RequestParam(name = "name") String name,
                                     @RequestParam(name = "logo", required = false) MultipartFile[] logo,
                                     @RequestParam(name = "introduction", required = false) String introduction,
                                     @RequestParam(name = "nature", required = false) String nature,
                                     @RequestParam(name = "duration", required = false) String duration,
                                     @RequestParam(name = "availableResources", required = false) String availableResources,
                                     @RequestParam(name = "cooperationStatus") Integer cooperationStatus,
                                     @RequestParam(name = "contact", required = false) String contact,
                                     @RequestParam(name = "website", required = false) String website,
                                     @RequestParam(name = "sectors") String sectors,
                                     @RequestParam(name = "tags", required = false, defaultValue = "") String tags,
                                     @RequestParam(name = "rank") Integer rank,
                                     @RequestParam(name = "isActive") boolean isActive,
                                     @RequestParam(name = "id") Integer id,
                                     @RequestParam(name = "file", required = false) MultipartFile[] file) {

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("introduction", introduction);
        params.put("nature", nature);
        params.put("duration", duration);
        params.put("availableResources", availableResources);
        params.put("cooperationStatus", cooperationStatus);
        params.put("contact", contact);
        params.put("website", website);
        params.put("sectors", "{" + sectors + "}");//需要处理
        params.put("tags", "{" + tags + "}");//需要处理
        params.put("rank", rank);
        params.put("isActive", isActive);
        params.put("id", id);
        params.put("createdBy", "");
        params.put("deleted", false);
        params.put("deletedBy", "");
        params.put("rowVersion", 0);
        params.put("updatedBy", "");
        params.put("createdAt", new Timestamp(System.currentTimeMillis()));

        //获取上传文件和图片后返回的url地址
        params.put("logo", "{" + FileUpload.getUrls(logo, "Associations") + "}");
        params.put("file", "{" + FileUpload.getUrls(file, "Associations") + "}");

        if (!associationService.addAssociation(params)) {
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }






    /**
     * 根据协会id修改协会信息
     * @param name              协会名称
     * @param introduction         协会简介
     * @param nature          协会性质
     * @param duration           协会合作期限
     * @param availableResources 协会可用资源
     * @param cooperationStatus    合作状态
     * @param contact           联系方式
     * @param website           网站链接
     * @param sectors          部门，领域
     * @param tags                标签
     * @param rank              等级
     * @param isActive          是否在线
     * @param id                    id
     * @param updateId          修改后的id
     * @return
     */
    @RequestMapping(value = "/updateAssociation", method = RequestMethod.POST)
    public JsonResult updateAssociation(@RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "introduction", required = false) String introduction,
                                        @RequestParam(name = "nature", required = false) String nature,
                                        @RequestParam(name = "duration", required = false) String duration,
                                        @RequestParam(name = "availableResources", required = false) String availableResources,
                                        @RequestParam(name = "cooperationStatus", required = false) Integer cooperationStatus,
                                        @RequestParam(name = "contact", required = false) String contact,
                                        @RequestParam(name = "website", required = false) String website,
                                        @RequestParam(name = "sectors", required = false) String sectors,
                                        @RequestParam(name = "tags", required = false) String tags,
                                        @RequestParam(name = "rank", required = false) Integer rank,
                                        @RequestParam(name = "isActive", required = false) boolean isActive,
                                        @RequestParam(name = "id") Integer id,
                                        @RequestParam(name = "updateId", required = false) Integer updateId) {


        if(associationService.getAssociation(id) == null){
            return new JsonResult(false, "此记录不存在");
        }

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("introduction", introduction);
        params.put("nature", nature);
        params.put("duration", duration);
        params.put("availableResources", availableResources);
        params.put("cooperationStatus", cooperationStatus);
        params.put("contact", contact);
        params.put("website", website);
        if(!StringUtils.isEmpty(sectors)){
            params.put("sectors", "{" + sectors + "}");//需要处理
        }
        if(!StringUtils.isEmpty(tags)){
            params.put("tags", "{" + tags + "}");//需要处理
        }
        params.put("rank", rank);
        params.put("isActive", isActive);
        params.put("id", id);
        params.put("updateId", updateId);
        params.put("updatedBy", "");
        params.put("updateTime", new Timestamp(System.currentTimeMillis()));

        if (!associationService.updateAssociation(params)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        return new JsonResult(true, "修改成功！");
    }











}
