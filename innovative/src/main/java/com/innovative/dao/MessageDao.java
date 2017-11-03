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
    
    
    /*       分界线    **************************************************************            */
    
    /**
     * 添加消息
     * @param message
     * @return
     */
    public int saveMessage(Message message);
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
    /**
     * 待办变成已办
     * @param id
     * @return
     */
    public int updateNotice(@Param("id")int id,@Param("userid")String userid);
    /**
     * 通过消息的类型和资源的id来找消息
     * @param type 消息的类型
     * @param proid 资源id
     * @return
     */
    public Message getMessageByTypeAndProid(@Param("type")String type,@Param("proid")String proid);
}
