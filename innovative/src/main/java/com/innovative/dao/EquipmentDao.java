package com.innovative.dao;

import com.innovative.bean.Equipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentDao {

    /**
     * 根据id获取设备信息
     *
     * @param id 方案id
     * @return
     */
    Equipment getEquipmentById(@Param("id") String id);

    /**
     * 分页条件查询
     *
     * @param startIndex 开始条数
     * @param pageSize   展示条数
     * @param sectors 
     * @param key2 
     * @param key1 
     * @return
     */
    List<Equipment> getEquipmentListByCondition(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,  @Param("sectors")String sectors, @Param("key1")String key1, @Param("key2")String key2);

    /**
     * 查询满足条件的总条数
     * @param sectors 
     * @param key2 
     * @param key1 
     *
     * @return
     */
    int getCountByCondition(@Param("sectors")String sectors, @Param("key1")String key1,@Param("key2")String key2);


    /**
     * 新增设备
     *
     * @param equipment 参数集合
     * @return
     */
    int insertEquipment(Equipment equipment);

    /**
     * 修改设备信息
     *
     * @param equipment 参数集合
     * @return
     */
    int updateEquipment(Equipment equipment);
    /**
     * 删除仪器设备
     * @param id
     * @return
     */
	boolean deleteEquipment(String id);
}
