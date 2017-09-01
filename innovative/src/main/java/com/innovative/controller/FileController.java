package com.innovative.controller;

import com.innovative.bean.FileBean;
import com.innovative.utils.BaseController;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController extends BaseController {



    /**
     * 根据id获取行业协会详情
     * @param id 协会id
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getAssociation(@RequestBody FileBean filebean ) {

         if (filebean != null && filebean.getFile().length > 0 && filebean.getModname() != null &&  filebean.getModname().length()>0) {
             try {
                 for (int i = 0; i < filebean.getFile().length; i++) {

                      FileUpload.copyInputStreamToFile((filebean.getFile())[i], filebean.getModname());
                 }
             } catch (IOException e) {
                 e.printStackTrace();
                 return new JsonResult(false, "上传失败!");
             }
         }else{
        	 return new JsonResult(false,"参数不合法！");
         }
         
         
        return new JsonResult(true, "上传成功!");
    }





  










}
