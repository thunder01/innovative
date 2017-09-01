package com.innovative.service;


import com.innovative.bean.Carousel;
import com.innovative.dao.CarouselDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselService {

    @Autowired
    private CarouselDao carouselDao;

    /**
     * 将图片保存到服务器，将记录保存到数据库
     *
     * @param list 图片地址，批量新增
     * @return
     */
    public boolean insertImg(List<String> list) {

        int result = carouselDao.insertImg(list);

        return result > 0 ? true : false;
    }

    /**
     * 删除图片，实际是将图片状态更新为已删除
     *
     * @param id 需要删除的图片id
     * @return
     */
    public boolean deleteImg(int id) {

        int result = carouselDao.deleteImg(id);

        return result > 0 ? true : false;

    }

    /**
     * 获取图片列表，根据时间排序，取3张
     *
     * @return
     */
    public List<Carousel> getCarouselList() {

        return carouselDao.getCarouselList();

    }

    /**
     * 根据id判断有无此图片
     *
     * @param id 图片id
     * @return
     */
    public Carousel getCarouselById(int id) {

        return carouselDao.getCarouselById(id);

    }

}
