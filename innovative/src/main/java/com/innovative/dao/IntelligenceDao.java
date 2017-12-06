package com.innovative.dao;

import com.innovative.bean.FeedBack;
import com.innovative.bean.Intelligence;
import com.innovative.bean.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntelligenceDao {
    /**
     * 列表页
     */
    List<Intelligence> getList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userName") String userName);

    /**
     * 根据ID查找信息
     */
    Intelligence getIntelligence(@Param("id") int id);

    /**
     * 添加内容
     */
    int addIntelligence(Intelligence intelligence);

    /**
     * 审核内容
     */
    int checkIntelligence(Intelligence intelligence);

    /**
     * 接单
     */
    int downOrder(Intelligence intelligence);

    /**
     * 情报反馈
     */
    int addfeedback(FeedBack feedBack);

    /**
     * 情报信息列表
     */
    List<FeedBack> getFeedBack(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userid") String userid);

    /**
     * 查询附表内容
     */
    List<FeedBack> getfeedBacks(@Param("id") int id);

    /**
     * 客户确认
     */
    int updateFack(FeedBack feedBack);
    /**
     * 总数
     */
    int getTotalCounts();
    /**
     * 客户评分
     */
    int addScore (Score score);
    /**
     * 我的情报
     * @param
     * @return
     */
    List<Intelligence> getMyIntelligence(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("createBy") String createBy);
    
    int getCountMyIntelligence(String createBy);
    
    List<Intelligence> getJieDan(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userid") String userid);
    
    int getCountJieDan(String userid);
}
