package com.innovative.controller;


import com.innovative.utils.JsonResult;
import com.innovative.bean.TechnicalReport;
import com.innovative.service.TechnicalReportService;
import com.innovative.utils.FileUpload;
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
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public JsonResult getTechnicalReportById(@RequestParam(name = "id", required = true) Integer id) {

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
    public JsonResult getTechnicalReportListByCondition(@RequestParam(name = "sectors", required = false) String sectors,
                                                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {

        if (pageNum < 1) {
            return new JsonResult(false, "参数不合法！");
        }

        return new JsonResult(true, technicalReportService.getTechnicalReportListByCondition(sectors, pageNum));
    }

    /**
     * 新增技术报告
     *
     * @param name     标题
     * @param summary  摘要
     * @param content  正文内容
     * @param files    上传文件
     * @param images   上传图片
     * @param sectors  行业领域
     * @param tags     标签
     * @param rank
     * @param isActive 是否显示
     * @param id       id
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResult insertTechnicalReport(@RequestParam(name = "name", required = true) String name,
                                            @RequestParam(name = "summary", required = false) String summary,
                                            @RequestParam(name = "content", required = false) String content,
                                            @RequestParam(name = "files", required = false) MultipartFile[] files,
                                            @RequestParam(name = "images", required = false) MultipartFile[] images,
                                            @RequestParam(name = "sectors", required = false) String sectors,
                                            @RequestParam(name = "tags", required = false) String tags,
                                            @RequestParam(name = "rank", required = true) Integer rank,
                                            @RequestParam(name = "isActive", required = true) boolean isActive,
                                            @RequestParam(name = "id", required = true) Integer id) {

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("summary", summary);
        params.put("content", content);
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
        params.put("fileUrls", "{" + FileUpload.getUrls(files, "technicalReport") + "}");
        params.put("imageUrls", "{" + FileUpload.getUrls(images, "technicalReport") + "}");

        //新增
        boolean result = technicalReportService.insertTechnicalReport(params);

        if (!result) {
            return new JsonResult(false, "新增技术报告失败，请重试！");
        }

        return new JsonResult(true, "新增技术报告成功！");
    }

    /**
     * 修改技术报告信息
     *
     * @param id       id
     * @param name     标题
     * @param summary  摘要
     * @param content  内容正文
     * @param sectors  行业领域
     * @param tags     标签
     * @param rank
     * @param isActive 是否显示
     * @param newId    新id
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JsonResult updateTechnicalReport(@RequestParam(name = "id", required = true) Integer id,
                                            @RequestParam(name = "name", required = true) String name,
                                            @RequestParam(name = "summary", required = false) String summary,
                                            @RequestParam(name = "content", required = false) String content,
                                            @RequestParam(name = "sectors", required = false) String sectors,
                                            @RequestParam(name = "tags", required = false) String tags,
                                            @RequestParam(name = "rank", required = true) Integer rank,
                                            @RequestParam(name = "isActive", required = true) boolean isActive,
                                            @RequestParam(name = "newId", required = false) Integer newId) {

        //判断有无此技术报告
        TechnicalReport t = technicalReportService.getTechnicalReportById(id);
        if (t == null) {
            return new JsonResult(false, "无此技术报告，请重试！");
        }

        //map集合存放请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("summary", summary);
        params.put("content", content);
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
        boolean result = technicalReportService.updateTechnicalReport(params);

        if (!result) {
            return new JsonResult(false, "修改技术报告失败，请重试！");
        }

        return new JsonResult(true, "修改技术报告成功！");
    }

}
