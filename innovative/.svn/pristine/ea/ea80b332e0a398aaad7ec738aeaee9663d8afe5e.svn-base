package com.innovative.controller;

import com.innovative.bean.Organization;
import com.innovative.service.OrganizationService;
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
@RequestMapping("/organization")
public class OrganizationController extends BaseController {


    @Autowired
    OrganizationService organizationService;

    /**
     * 根据id获取机构详情
     * @param id 机构id
     * @return
     */
    @RequestMapping(value = "/getOrganization", method = RequestMethod.GET)
    public JsonResult getOrganization(@RequestParam(name = "id") Integer id){

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
    public JsonResult getOrganizationList(@RequestParam(name = "sectors", required = false) String sectors,
                                          @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum){

        if (pageNum < 1) {
            return new JsonResult(false, "参数不合法！");
        }
        return new JsonResult(true, organizationService.getOrganizationList(sectors, pageNum));
    }







    /**
     * 添加机构信息
     * @param name 机构名称
     * @param logo 机构徽标
     * @param introduction 机构简介
     * @param nature 机构性质
     * @param achievements 已合作项目及成果
     * @param cooperationStatus 合作状态
     * @param contact 联系方式
     * @param website 网站链接
     * @param sectors 行业领域
     * @param tags 标签
     * @param rank 等级
     * @param isActive 是否在线，活跃
     * @param id id
     * @param file 文件
     * @return
     */
    @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
    public JsonResult addOrganization(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "logo", required = false) MultipartFile[] logo,
                                      @RequestParam(name = "introduction", required = false, defaultValue = "") String introduction,
                                      @RequestParam(name = "nature", required = false, defaultValue = "") String nature,
                                      @RequestParam(name = "achievements", required = false, defaultValue = "") String achievements,
                                      @RequestParam(name = "cooperationStatus") Integer cooperationStatus,
                                      @RequestParam(name = "contact", required = false, defaultValue = "") String contact,
                                      @RequestParam(name = "website", required = false, defaultValue = "") String website,
                                      @RequestParam(name = "sectors") String sectors,
                                      @RequestParam(name = "tags", required = false, defaultValue = "") String tags,
                                      @RequestParam(name = "rank") Integer rank,
                                      @RequestParam(name = "isActive") boolean isActive,
                                      @RequestParam(name = "id") Integer id,
                                      @RequestParam(name = "file", required = false) MultipartFile[] file){

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("introduction", introduction);
        params.put("nature", nature);
        params.put("achievements", achievements);
        params.put("cooperationStatus", cooperationStatus);
        params.put("contact", contact);
        params.put("website", website);
        params.put("sectors", sectors);//需要处理
        params.put("tags", tags);//需要处理
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
        params.put("logo", "{" + FileUpload.getUrls(logo, "Organizations") + "}");
        params.put("file", "{" + FileUpload.getUrls(file, "Organizations") + "}");

        if(!organizationService.addOrganization(params)){
            return new JsonResult(false, "新增失败，请重试！");
        }
        return new JsonResult(true, "新增成功！");
    }





    /**
     * 修改机构信息
     *
     * @param name 机构名称
     * @param introduction 机构简介
     * @param nature 机构性质
     * @param achievements 已合作项目及成果
     * @param cooperationStatus 合作状态
     * @param contact 联系方式
     * @param website 网站链接
     * @param sectors 行业领域
     * @param tags 标签
     * @param rank 等级
     * @param isActive 是否在线，活跃
     * @param id id
     * @param updateId          修改后的id
     * @return
     */
    @RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
    public JsonResult updateOrganization(@RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "introduction", required = false) String introduction,
                                @RequestParam(name = "nature", required = false) String nature,
                                @RequestParam(name = "achievements", required = false) String achievements,
                                @RequestParam(name = "cooperationStatus", required = false) Integer cooperationStatus,
                                @RequestParam(name = "contact", required = false) String contact,
                                @RequestParam(name = "website", required = false) String website,
                                @RequestParam(name = "sectors", required = false) String sectors,
                                @RequestParam(name = "tags", required = false) String tags,
                                @RequestParam(name = "rank", required = false) Integer rank,
                                @RequestParam(name = "isActive", required = false) boolean isActive,
                                @RequestParam(name = "id") Integer id,
                                @RequestParam(name = "updateId", required = false) Integer updateId) {


        if(organizationService.getOrganization(id) == null){
            return new JsonResult(false, "此记录不存在");
        }

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();

        params.put("name", name);
        params.put("introduction", introduction);
        params.put("nature", nature);
        params.put("achievements", achievements);
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

        if (!organizationService.updateOrganization(params)) {
            return new JsonResult(false, "修改失败，请重试！");
        }
        return new JsonResult(true, "修改成功！");
    }












}
