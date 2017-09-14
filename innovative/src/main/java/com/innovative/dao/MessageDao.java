package com.innovative.dao;
import com.innovative.bean.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {
    /**
     * 添加消息表
     */
    int addMessage(Message Message);


    /**
     * 列表页
     * @return
     */
    List<Message> getMessageList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询相关行业领域的记录总条数
     * @return
     */
    int getTotalCount();
}
