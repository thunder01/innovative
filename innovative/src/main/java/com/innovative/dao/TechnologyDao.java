package com.innovative.dao;

import com.innovative.bean.Technology;
import com.innovative.bean.TechnologyDz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TechnologyDao {

    /**
     * 添加内容
     */
    int addTechnology(Technology technology);

    /**
     * 科技资讯查询
     */
    List<Technology> getinformation(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 科技专栏查询  getSpecial
     */
    List<Technology> getSpecial(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     *添加点赞表
     */
    int  adddz(TechnologyDz technologyDz);

    /**
     * 查询点赞的次数
     */
    int getTotalCounts(@Param("id") int id);

    /**
     * 科技资讯详情页面
     */
    Technology getTechnologyzx(@Param("id") int id);

    /**
     * 科技专栏详情页面
     */
    Technology getTechnologyzl(@Param("id") int id);

}
