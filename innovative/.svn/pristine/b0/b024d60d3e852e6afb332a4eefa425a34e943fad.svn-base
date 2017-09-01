package com.innovative.dao;

import com.innovative.bean.Association;
import com.innovative.bean.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarouselDao {

    /**
     * 将图片保存到服务器，将记录保存到数据库
     *
     * @param list 图片地址，批量新增
     * @return
     */
    int insertImg(List<String> list);

    /**
     * 删除图片，实际是将图片状态更新为已删除
     *
     * @param id 需要删除的图片id
     * @return
     */
    int deleteImg(@Param("id") int id);

    /**
     * 获取图片列表，根据时间排序，取3张
     *
     * @return
     */
    List<Carousel> getCarouselList();

    /**
     * 根据id判断有无此图片
     *
     * @param id 图片id
     * @return
     */
    Carousel getCarouselById(@Param("id") int id);

}
