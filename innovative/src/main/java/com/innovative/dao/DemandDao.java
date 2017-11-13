package com.innovative.dao;

import com.innovative.bean.Association;
import com.innovative.bean.Demand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DemandDao {

    /**
     * 根据ID查询
     */
     Demand getDemand(@Param("id") int id);


    /**
     * 添加内容
     */
    int addDemand(Demand demand);



    /**
     * 根据ID修改文件状态
     */
    int updateDemand(Demand demand);


    /**
     * 列表页
     * @return
     */
    List<Demand> getDemandList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userName") String userName);
    /**
     * 列表页
     * @return
     */
    List<Demand> getQueryList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userName") String userName);

    /**
     * 查询相关行业领域的记录总条数
     * @return
     */
    int getTotalCount(@Param("userName") String userName);
    /**
     * 查询相关行业领域的记录总条数
     * @return
     */
    int getTotalCounts(@Param("userName") String userName);
    
    /**
     * 获取用户下过的并且被接过需求订单
     * @param startIndex
     * @param pageSize
     * @param userid
     * @return
     */
    List<Demand> getMyDemand(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userid") String userid);
    
    int getMyDemandTotal(String userid);
    /**
     * 我的需求（不管通过没通过）
     * @param startIndex
     * @param pageSize
     * @param userid
     * @return
     */
    List<Demand> getMyselfDemand(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userid") String userid);
    /**
     *我的需求总数
     * @param userid
     * @return
     */
    int getMyselfDemandTotal(String userid);
    
    
    /**
     * 需求工程师订单集合
     * @param userid
     * @return
     */
    List<Demand> getAllMyDemand(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("userid") String userid);
    
    int getAllMyDemandCount(String userid);
    
    /**
     * 当前登入用户的所有的已通过的需求
     * @param userid
     * @return
     */
    List<Demand> findAllDemand(String userid);
    
    Demand getDemandByOrderid(Integer orderid);
}
