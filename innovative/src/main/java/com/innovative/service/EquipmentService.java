package com.innovative.service;


import com.github.pagehelper.StringUtil;
import com.innovative.bean.Equipment;
import com.innovative.bean.Solution;
import com.innovative.dao.EquipmentDao;
import com.innovative.dao.SolutionDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentDao equipmentDao;


    /**
     * 根据id获取设备信息
     *
     * @param id 设备id
     * @return
     */
    public Equipment getEquipmentById(Integer id) {

        return equipmentDao.getEquipmentById(id);

    }

    /**
     * 分页条件查询设备list
     *
     * @param sectors 关键字
     * @param pageNum  页码
     * @return
     */
    public Map<String, Object> getEquipmentListByCondition(String sectors,  int pageNum) {


        if (!StringUtil.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Equipment> items = equipmentDao.getEquipmentListByCondition(sectors, offset, limit);
        int totalCount = equipmentDao.getCountByCondition(sectors);

        //数据组装
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("offset", offset);
        map.put("limit", limit);
        map.put("count", items.size());

        return map;

    }

    /**
     * 新增设备
     *
     * @param params 参数集合
     * @return
     */
    public boolean insertEquipment(Map<String, Object> params) {

        int result = equipmentDao.insertEquipment(params);

        return result > 0 ? true : false;
    }

    /**
     * 修改设备信息
     *
     * @param params 参数集合
     * @return
     */
    public boolean updateEquipment(Map<String, Object> params) {

        int result = equipmentDao.updateEquipment(params);

        return result > 0 ? true : false;
    }
}
