package com.innovative.controller;


import com.innovative.service.PoiService;
import com.innovative.utils.JsonResult;
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
    public JsonResult importExcel(@RequestParam(name = "file") MultipartFile[] file) {
            if(!poiService.importExcel(file)){
                return new JsonResult(false, "导入失败");
            }
            return new JsonResult(true, "导入成功");
    }






}
