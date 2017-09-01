package com.innovative.controller;


import com.innovative.service.NoticeService;
import com.innovative.utils.BaseController;
import com.innovative.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {



    @Autowired
    NoticeService noticeService;


    /**
     * 公告上传
     * @param title 标题
     * @param file 文件（多个）
     * @return
     */
    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    public JsonResult addNotice(@RequestParam(name = "title") String title,
                                @RequestParam(name = "file") MultipartFile[] file) {

        if (file == null || file.length <= 0) {
            return new JsonResult(false, "请选择文件");
        }
        //文件后缀维护  可以自己添加或者减少
        String[] fileSufixs = {"pdf", "doc", "excel", "word", "docx"};
        //判断文件后缀
        for (int i = 0; i < file.length; i++) {
            String file_name = file[i].getOriginalFilename();
            String fileSufix = file_name.split("\\.")[1];
            if(fileSufix != null){
                if (!isExsitSufix(fileSufixs, fileSufix)){
                    return new JsonResult(false, "文件类型错误");
                }
            }
        }
        if(!noticeService.addNotice(title, file)){
            return new JsonResult(false, "发布失败");
        }
        return new JsonResult(true, "发布成功");
    }




    public boolean isExsitSufix(String[] strs, String sufix) {
        //flag为false不存在 true存在
        boolean flag = false;
        for (String str : strs) {
            if (str.equals(sufix)) {
                flag = true;
            }
        }
        return flag;
    }




    /**
     * 公告列表
     * @return
     */
    @RequestMapping(value = "/getNotices", method = RequestMethod.GET)
    public JsonResult getNotices() {
        return new JsonResult(true, noticeService.getNotices());
    }




    /**
     * 公告逻辑删除
     * @param id id
     * @return
     */
    @RequestMapping(value = "/delNotice", method = RequestMethod.POST)
    public JsonResult delNotice(@RequestParam(name = "id") Integer id) {

        if(!noticeService.delNotice(id)){
            return new JsonResult(false, "删除失败");
        }
        return new JsonResult(true, "删除成功");
    }





}
