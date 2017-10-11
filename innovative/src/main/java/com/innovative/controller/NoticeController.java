package com.innovative.controller;


import com.innovative.bean.Expert;
import com.innovative.bean.Notice;
import com.innovative.service.NoticeService;
import com.innovative.utils.BaseController;
import com.innovative.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * @param file 文件（多个）(根据需要我们的上传公告分两步（主要是前台插件的原因）第一增加上传文件，第二部给文件输入文件title)
     * @return
     */
    @RequestMapping(value = "/addNoticeFile", method = RequestMethod.POST)
    public JsonResult addNoticeFile(@RequestParam(name = "userid") String userid,
                                @RequestParam(name = "FileData") MultipartFile[] FileData) {

        if (FileData == null || FileData.length <= 0) {
            return new JsonResult(false, "请选择文件");
        }
        //文件后缀维护  可以自己添加或者减少
        String[] fileSufixs = {"pdf", "doc", "excel", "word", "docx"};
        //判断文件后缀
        for (int i = 0; i < FileData.length; i++) {
            String file_name = FileData[i].getOriginalFilename();
            String fileSufix = file_name.split("\\.")[1];
            if(fileSufix != null){
                if (!isExsitSufix(fileSufixs, fileSufix)){
                    return new JsonResult(false, "文件类型错误");
                }
            }
        }
        /*if(!noticeService.addNotice(userid, FileData)){
            return new JsonResult(false, "上传失败");
        }*/
        return new JsonResult(true, "上传成功");
    }

    /**
     * 公告上传
     * @param title 标题
     * @param file 文件（多个）
     * @return
     */
    @RequestMapping(value = "/addNotice", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addNotice(@RequestBody Notice notice) {

        if(!noticeService.addNotice(notice)){
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
    public JsonResult delNotice(@RequestParam(name = "id") String id) {

        if(!noticeService.delNotice(id)){
            return new JsonResult(false, "删除失败");
        }
        return new JsonResult(true, "删除成功");
    }





}
