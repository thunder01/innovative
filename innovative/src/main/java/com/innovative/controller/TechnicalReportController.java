package com.innovative.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.innovative.bean.TechnicalReport;
import com.innovative.service.TechnicalReportService;
import com.innovative.utils.CookiesUtil;
import com.innovative.utils.JsonResult;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/technicalReport")
public class TechnicalReportController {


    @Autowired
    private TechnicalReportService technicalReportService;


    /**
     * 根据id获取技术报告
     *
     * @param id 技术报告id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public JsonResult getTechnicalReportById(@PathVariable(name = "id", required = true) Integer id) {

        TechnicalReport technicalReport = technicalReportService.getTechnicalReportById(id);
        if (technicalReport == null) {
            return new JsonResult(false, "无此技术报告信息");
        }

        return new JsonResult(true, technicalReport);

    }

    /**
     * 分页条件查询技术报告list
     *
     * @param sectors 关键字
     * @param pageNum  页码
     * @return
     */
    @RequestMapping(value = "/getListByCondition", method = RequestMethod.GET)
    public JsonResult getTechnicalReportListByCondition(@RequestParam(name="page" , defaultValue="1") Integer page) {

        if (page <= 0) {
            return new JsonResult(false, "参数不合法！");
        }

        return new JsonResult(true, technicalReportService.getTechnicalReportListByCondition( page));
    }

    /**
     * 新增技术报告
     *
     * @param technicalReport       技术报告bean
     * @return
     */
    @RequestMapping(value = "/insertTechnicalReport", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult insertTechnicalReport(@RequestBody TechnicalReport technicalReport,HttpServletRequest req) {
    	
    	technicalReport.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        //新增
        boolean result = technicalReportService.insertTechnicalReport(technicalReport);

        if (!result) {
            return new JsonResult(false, "新增技术报告失败，请重试！");
        }

        return new JsonResult(true, "新增技术报告成功！");
    }

    /**
     * 修改技术报告信息
     *
     * @param technicalReport    技术报告bean
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateTechnicalReport(@RequestBody TechnicalReport technicalReport,HttpServletRequest req) {
    	
    	technicalReport.setCreatedBy(CookiesUtil.getCookieValue(req,"user_name"));
        //判断有无此技术报告
        TechnicalReport t = technicalReportService.getTechnicalReportById(technicalReport.getId());
        if (t == null) {
            return new JsonResult(false, "无此技术报告，请重试！");
        }


        //修改
        boolean result = technicalReportService.updateTechnicalReport(technicalReport);

        if (!result) {
            return new JsonResult(false, "修改技术报告失败，请重试！");
        }

        return new JsonResult(true, "修改技术报告成功！");
    }

}
