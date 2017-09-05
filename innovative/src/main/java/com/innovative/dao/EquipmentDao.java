package com.innovative.dao;

import com.innovative.bean.Equipment;
import com.innovative.bean.Solution;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EquipmentDao {

    /**
     * 根据id获取设备信息
     *
     * @param id 方案id
     * @return
     */
    Equipment getEquipmentById(@Param("id") Integer id);

    /**
     * 分页条件查询
     *
     * @param sectors   关键字
     * @param startIndex 开始条数
     * @param pageSize   展示条数
     * @return
     */
    List<Equipment> getEquipmentListByCondition(@Param("sectors") String sectors, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 查询满足条件的总条数
     *
     * @param sectors 关键字
     * @return
     */
    int getCountByCondition(@Param("sectors") String sectors);

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
}
