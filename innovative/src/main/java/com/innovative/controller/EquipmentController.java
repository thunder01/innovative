package com.innovative.controller;


import com.innovative.bean.Equipment;
import com.innovative.bean.Solution;
import com.innovative.service.EquipmentService;
import com.innovative.service.SolutionService;
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
@RequestMapping("/equipment")
public class EquipmentController {


    @Autowired
    private EquipmentService equipmentService;


    /**
     * 根据id获取设备
     *
     * @param id 设备id
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public JsonResult getEquipmentById(@RequestParam(name = "id", required = true) Integer id) {

        Equipment equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            return new JsonResult(false, "无此设备信息");
        }

        return new JsonResult(true, equipment);

    }

    /**
     * 分页条件查询设备list
     *
     * @param sectors 领域
     * @param pageNum  页码
     * @return
     */
    @RequestMapping(value = "/getListByCondition", method = RequestMethod.GET)
    public JsonResult getEquipmentListByCondition(@RequestParam(name = "sectors", required = false) String sectors,
                                                  @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {

        if (pageNum == null || pageNum < 1) {
            return new JsonResult(false, "参数不合法！");
        }

        return new JsonResult(true, equipmentService.getEquipmentListByCondition(sectors, pageNum));
    }

    /**
     * 新增设备
     *
     * @param name         标题
     * @param sharing      分享内容
     * @param unit         单位
     * @param state        状态
     * @param manufacturer 制造商
     * @param purchasedAt  购买时间
     * @param introduction 介绍
     * @param images       图片
     * @param contact      联系人
     * @param files        文件
     * @param sectors      行业领域
     * @param tags         标签
     * @param rank
     * @param isActive     是否显示
     * @param id           设备id
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResult insertEquipment(@RequestParam(name = "name", required = true) String name,
                                      @RequestParam(name = "sharing", required = false) String sharing,
                                      @RequestParam(name = "unit", required = false) String unit,
                                      @RequestParam(name = "state", required = false) String state,
                                      @RequestParam(name = "manufacturer", required = false) String manufacturer,
                                      @RequestParam(name = "purchasedAt", required = false) Long purchasedAt,
                                      @RequestParam(name = "introduction", required = false) String introduction,
                                      @RequestParam(name = "images", required = false) MultipartFile[] images,
                                      @RequestParam(name = "contact", required = false) String contact,
                                      @RequestParam(name = "files", required = false) MultipartFile[] files,
                                      @RequestParam(name = "sectors", required = false) String sectors,
                                      @RequestParam(name = "tags", required = false) String tags,
                                      @RequestParam(name = "rank", required = true) Integer rank,
                                      @RequestParam(name = "isActive", required = true) boolean isActive,
                                      @RequestParam(name = "id", required = true) Integer id) {

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("sharing", sharing);
        params.put("unit", unit);
        params.put("state", state);
        params.put("manufacturer", manufacturer);
        params.put("purchasedAt", purchasedAt);
        params.put("introduction", introduction);
        params.put("contact", contact);
        if (!StringUtils.isEmpty(sectors)) {
            params.put("sectors", "{" + sectors + "}");
        } else {
            params.put("sectors", "{}");
        }
        if (!StringUtils.isEmpty(tags)) {
            params.put("tags", "{" + tags + "}");
        } else {
            params.put("tags", "{}");
        }
        params.put("rank", rank);
        params.put("isActive", isActive);
        params.put("id", id);
        params.put("createdAt", new Timestamp(System.currentTimeMillis()));

        //获取上传文件和图片后返回的url地址
        params.put("file", "{" + FileUpload.getUrls(files, "equipment") + "}");
        params.put("picture", "{" + FileUpload.getUrls(images, "equipment") + "}");

        //新增
        boolean result = equipmentService.insertEquipment(params);

        if (!result) {
            return new JsonResult(false, "新增设备失败，请重试！");
        }

        return new JsonResult(true, "新增设备成功！");
    }

    /**
     * 修改设备
     *
     * @param name         标题
     * @param sharing      分享内容
     * @param unit         单位
     * @param state        状态
     * @param manufacturer 制造商
     * @param purchasedAt  购买时间
     * @param introduction 介绍
     * @param contact      联系人
     * @param sectors      行业领域
     * @param tags         标签
     * @param rank
     * @param isActive     是否显示
     * @param id           设备id
     * @param newId        设备新id
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JsonResult updateEquipment(@RequestParam(name = "id", required = true) Integer id,
                                      @RequestParam(name = "name", required = true) String name,
                                      @RequestParam(name = "sharing", required = false) String sharing,
                                      @RequestParam(name = "unit", required = false) String unit,
                                      @RequestParam(name = "state", required = false) String state,
                                      @RequestParam(name = "manufacturer", required = false) String manufacturer,
                                      @RequestParam(name = "purchasedAt", required = false) Long purchasedAt,
                                      @RequestParam(name = "introduction", required = false) String introduction,
                                      @RequestParam(name = "contact", required = false) String contact,
                                      @RequestParam(name = "sectors", required = false) String sectors,
                                      @RequestParam(name = "tags", required = false) String tags,
                                      @RequestParam(name = "rank", required = true) Integer rank,
                                      @RequestParam(name = "isActive", required = true) boolean isActive,
                                      @RequestParam(name = "newId", required = false) Integer newId) {

        //判断有无此方案
        Equipment t = equipmentService.getEquipmentById(id);
        if (t == null) {
            return new JsonResult(false, "无此设备，请重试！");
        }

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("sharing", sharing);
        params.put("unit", unit);
        params.put("state", state);
        params.put("manufacturer", manufacturer);
        params.put("purchasedAt", purchasedAt);
        params.put("introduction", introduction);
        params.put("contact", contact);
        if (!StringUtils.isEmpty(sectors)) {
            params.put("sectors", "{" + sectors + "}");
        }
        if (!StringUtils.isEmpty(tags)) {
            params.put("tags", "{" + tags + "}");
        }
        params.put("rank", rank);
        params.put("isActive", isActive);
        params.put("newId", newId);
        params.put("updatedAt", new Timestamp(System.currentTimeMillis()));

        //修改
        boolean result = equipmentService.updateEquipment(params);

        if (!result) {
            return new JsonResult(false, "修改设备信息失败，请重试！");
        }

        return new JsonResult(true, "修改设备信息成功！");
    }

}
