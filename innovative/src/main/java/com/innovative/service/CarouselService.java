package com.innovative.service;


import com.innovative.bean.Carousel;
import com.innovative.bean.Expert;
import com.innovative.dao.CarouselDao;
import com.innovative.dao.FileDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarouselService {

    @Autowired
    private CarouselDao carouselDao;
    @Autowired
    FileDao fileDao;

    /**
     * 将图片保存到服务器，将记录保存到数据库
     *
     * @param lists 图片地址，批量新增
     * @return
     */
    public boolean insertImg(List<Map<String, String>> lists) {
    	for(int i = 0 ;i<lists.size();i++){
    		//此处可优化成更新成一个批量更新（有时间再弄吧）
    		fileDao.updateFile(lists.get(i).get("id"));
    	}
        int result = carouselDao.insertImg(lists);

        return result > 0 ? true : false;
    }

    /**
     * 删除图片，实际是将图片状态更新为已删除
     *
     * @param id 需要删除的图片id
     * @return
     */
    public boolean deleteImg(String id) {
    	fileDao.deleteFile(id);
        int result = carouselDao.deleteImg(id);

        return result > 0 ? true : false;

    }

    /**
     * 获取图片列表，根据时间排序，取3张
     *
     * @return
     */
    public List<Carousel> getCarouselList() {
    	List<Carousel> catousels = carouselDao.getCarouselList();
    	 for(Carousel e: catousels){
         	if(e==null || "".equals(e.getId()))
         		continue;
         	List<String> url = fileDao.getPhotoByMOdAndId(e.getId(), "expertPhoto");
   		   if(url != null && url.size() > 0 )
   			  	e.setLink( url.get(0));
         }
        return catousels;

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
