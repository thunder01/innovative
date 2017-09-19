package com.innovative.service;


import com.innovative.bean.Equipment;
import com.innovative.dao.EquipmentDao;
import com.innovative.dao.FileDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    CodeItemUtil codeItemUtil;

    /**
     * 根据id获取设备信息
     *
     * @param id 设备id
     * @return
     */
    public Equipment getEquipmentById(String id) {

    	Equipment equipment = equipmentDao.getEquipmentById(id);
        if(equipment!=null){
			List<String> urllist = fileDao.getPhotoByMOdAndId(id, "equipmentPhoto");
 		   if(urllist != null && urllist.size() > 0 )
 			  equipment.setPicture( urllist.get(0));
		}
		return equipment;

    }

    /**
     * 分页条件查询设备list
     *
     * @param pageNum  页码
     * @return
     */
    public Map<String, Object> getEquipmentListByCondition(int pageNum) {


       /* if (!StringUtil.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }*/

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Equipment> items = equipmentDao.getEquipmentListByCondition( offset, limit);
        int totalCount = equipmentDao.getCountByCondition();

        //数据组装
        Map<String, Object> map = new HashMap<>();
        
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        

        return map;

    }

    /**
     * 新增设备
     *
     * @param equipment 参数bean
     * @return
     */
    @Transactional
    public boolean insertEquipment(Equipment equipment) {

        //增加成功
         equipmentDao.insertEquipment(equipment);
		 return fileDao.updateFile(equipment.getId());

    }

    /**
     * 修改设备信息
     *
     * @param equipment 参数bean
     * @return
     */
    public boolean updateEquipment(Equipment equipment) {
    	fileDao.updateFile(equipment.getId());
        int result = equipmentDao.updateEquipment(equipment);

        return result > 0 ;
    }
}
