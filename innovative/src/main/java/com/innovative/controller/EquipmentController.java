package com.innovative.controller;


import com.innovative.bean.Equipment;
import com.innovative.service.EquipmentService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public JsonResult getEquipmentById(@PathVariable Integer id) {

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
    public JsonResult getEquipmentListByCondition(@RequestParam(name="offset" ,defaultValue="0") Integer offset) {

    	Integer page = offset/(new PageInfo().getPageSize()) +1;

        return new JsonResult(true, equipmentService.getEquipmentListByCondition( page));
    }

    /**
     * 新增设备
     *
     * @param Equipment       设备bean
     * @return
     */
    @RequestMapping(value = "/insertEquipment", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertEquipment(@RequestBody Equipment equipment,HttpServletRequest req) {

    	equipment.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        //新增
        boolean result = equipmentService.insertEquipment(equipment);

        if (!result) {
            return new JsonResult(false, "新增设备失败，请重试！");
        }

        return new JsonResult(true, "新增设备成功！");
    }

    /**
     * 修改设备
     *
     * @param equipment        设备新bean
     * @return
     */
    @RequestMapping(value = "updateEquipment", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateEquipment(@RequestBody Equipment equipment,HttpServletRequest req) {

        //判断有无此方案
        Equipment t = equipmentService.getEquipmentById(equipment.getId());
        if (t == null) {
            return new JsonResult(false, "无此设备，请重试！");
        }

        equipment.setUpdatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        //修改
        boolean result = equipmentService.updateEquipment(equipment);

        if (!result) {
            return new JsonResult(false, "修改设备信息失败，请重试！");
        }

        return new JsonResult(true, "修改设备信息成功！");
    }

}
