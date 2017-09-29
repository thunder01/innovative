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
     * @return
     */
    List<Equipment> getEquipmentListByCondition(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的总条数
     *
     * @return
     */
    int getCountByCondition();

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
