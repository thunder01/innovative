package com.innovative.controller;


import com.innovative.bean.Carousel;
import com.innovative.service.CarouselService;
import com.innovative.utils.FileUpload;
import com.innovative.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carousel")
public class CarouselController {


    @Autowired
    private CarouselService carouselService;

    /**
     * 将图片保存到服务器，将记录保存到数据库
     *
     * @param images 需要上传的图片
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResult insertImg(@RequestParam(name = "FileData", required = true) MultipartFile[] FileData) {

        //用于存储上传后的图片地址
        List<String> list = new ArrayList();

        //上传图片操作
        if (FileData != null && FileData.length > 0) {
            try {
                String url = null;
                for (int i = 0; i < FileData.length; i++) {

                    url = FileUpload.copyInputStreamToFile(FileData[i], "carousel");
                    list.add(url);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (list == null || list.size() < 1) {
            return new JsonResult(false, "上传图片失败！");
        }

        //向数据库插入图片信息
        boolean result = carouselService.insertImg(list);
        if (!result) {
            return new JsonResult(false, "上传图片失败！");
        }
        return new JsonResult(result, "上传图片成功！");

    }

    /**
     * 删除图片，实际是将图片状态更新为已删除
     *
     * @param id 需要删除的图片id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResult deleteImg(@RequestParam(name = "id", required = true) Integer id) {

        if (id == null) {
            return new JsonResult(false, "参数不合法，请重新操作！");
        }

        //根据id判断有无此图片
        Carousel carousel = carouselService.getCarouselById(id);
        if (carousel == null) {
            return new JsonResult(false, "无此图片！");
        }

        //将图片状态改为已删除
        boolean result = carouselService.deleteImg(id);
        if (!result) {
            return new JsonResult(false, "删除操作失败，请重试！");
        }
        return new JsonResult(true, "删除图片成功！");

    }

    /**
     * 获取图片列表，根据时间排序，取3张
     *
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public JsonResult getCarouselList() {

        return new JsonResult(true, carouselService.getCarouselList());

    }


}
