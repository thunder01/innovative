package com.innovative.dao;

import com.innovative.bean.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeDao {



    /**
     * 公告上传
     * @param params 封装参数
     * @return
     */
    int addNotice(Map<String, Object> params);




    /**
     * 公告列表
     * @return
     */
    List<Notice> getNotices();





    /**
     * 公告逻辑删除
     * @param id id
     * @return
     */
    int delNotice(@Param("id") String id);




	boolean updateNotice(@Param("title")String title, @Param("id")String id);


}
