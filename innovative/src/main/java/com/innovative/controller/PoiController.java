package com.innovative.controller;


import com.innovative.service.PoiService;
import com.innovative.utils.JsonResult;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/poi")
public class PoiController {



    @Autowired
    PoiService poiService;



    /**
     * 批量导入专家excel到数据库
     * @param file 专家文件（多个）
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public JsonResult importExcel(@RequestParam(name = "FileData") MultipartFile[] FileData , HttpServletRequest request) {
            if(!poiService.importExcel(FileData,request)){
                return new JsonResult(false, "导入失败");
            }
            return new JsonResult(true, "导入成功");
    }
    /**
     * 上传协会
     * @param FileData
     * @return
     */
    @RequestMapping(value = "/importAssociation", method = RequestMethod.POST)
    public JsonResult importAssociation(@RequestParam(name = "FileData") MultipartFile FileData,HttpServletRequest request) {
            if(!poiService.importAssociation(FileData,request)){
                return new JsonResult(false, "导入失败,请检查数据!");
            }
            return new JsonResult(true, "导入成功");
    }
    /**
     * 上传仪器设备
     * @param FileData
     * @return
     */
    @RequestMapping(value = "/importEquipment", method = RequestMethod.POST)
    public JsonResult importEquipment(@RequestParam(name = "FileData") MultipartFile FileData,HttpServletRequest request) {
            if(!poiService.importEquipment(FileData,request)){
                return new JsonResult(false, "导入失败,请检查数据!");
            }
            return new JsonResult(true, "导入成功");
    }
    
    /**
     * 上传组织机构
     * @param FileData
     * @return
     */
    @RequestMapping(value = "/importOrganization", method = RequestMethod.POST)
    public JsonResult importOrganization(@RequestParam(name = "FileData") MultipartFile FileData,HttpServletRequest request) {
            if(!poiService.importOrganizations(FileData,request)){
                return new JsonResult(false, "导入失败,请检查数据!");
            }
            return new JsonResult(true, "导入成功");
    }
    
    /**
     * 上传解决方案
     * @param FileData
     * @return
     */
    @RequestMapping(value = "/importSolution", method = RequestMethod.POST)
    public JsonResult importSolution(@RequestParam(name = "FileData") MultipartFile FileData,HttpServletRequest request) {
            if(!poiService.importSolution(FileData,request)){
                return new JsonResult(false, "导入失败,请检查数据!");
            }
            return new JsonResult(true, "导入成功");
    }
    
    /**
     * 上传技术报告
     * @param FileData
     * @return
     */
    @RequestMapping(value = "/importTechnicalReports", method = RequestMethod.POST)
    public JsonResult importTechnicalReports(@RequestParam(name = "FileData") MultipartFile FileData,HttpServletRequest request) {
            if(!poiService.importTechnicalReports(FileData,request)){
                return new JsonResult(false, "导入失败,请检查数据!");
            }
            return new JsonResult(true, "导入成功");
    }


    




}
