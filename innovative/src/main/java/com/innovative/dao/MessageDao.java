package com.innovative.dao;
import com.innovative.bean.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 消息列表
 * @author huang
 *
 */
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
    /**
     * 修改状态
     */
    int upStatus(int id);
    
    
    /*       分界线                */
    
    /**
     * 添加待办消息
     * @param message
     * @return
     */
    public int saveBacklogMessage(Message message);
    /**
     * 添加通知消息
     * @param message
     * @return
     */
    public int saveNoticeMessage(Message message);
    /**
     * 通知消息集合
     * @param userid
     * @return
     */
    public List<Message> showNoticeMessage(@Param("userid") String userid,@Param("notice") Integer notice,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
    /**
     * 消息通知数
     * @param userid
     * @return
     */
    public int totalNoticeMessage(@Param("userid") String userid,@Param("notice") Integer notice);
    
    
}
