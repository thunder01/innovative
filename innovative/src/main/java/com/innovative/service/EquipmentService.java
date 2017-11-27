package com.innovative.service;


import com.innovative.bean.Equipment;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.User;
import com.innovative.dao.EquipmentDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.LoggerUserDao;
import com.innovative.utils.CodeItemUtil;
import com.innovative.utils.PageInfo;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.druid.util.StringUtils;

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
    @Autowired
    IntegralService integralService;
    @Autowired
    LoggerUserDao loggerUserDao;
    @Autowired
    UserService userService;

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
 		  User user = userService.getUser(equipment.getCreatedBy());
 		  if(user!=null){
  			integralService.managerIntegral(5, equipment.getCreatedBy(), equipment.getId());
 		  }
		}
		return equipment;

    }
    /**
     * 分页条件查询设备list
     *
     * @param pageNum  页码
     * @param sectors 
     * @param keyword 
     * @return
     */
    public Map<String, Object> getEquipmentListByCondition(int pageNum, String sectors, String keyword) {


        if (!StringUtils.isEmpty(sectors)) {
            sectors = "{" + sectors + "}";
        }
        String key1 = "%" + keyword + "%".trim();
	    String key2 = "{" + keyword + "}".trim();
        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Equipment> items = equipmentDao.getEquipmentListByCondition( offset, limit,sectors,key1,key2);
        for(Equipment e: items){
        	if(null==e || "".equals(e.getId()))
        		continue;
        	List<String> urllist = fileDao.getPhotoByMOdAndId(e.getId(), "equipmentPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			  e.setPicture( urllist.get(0));
        }
        int totalCount = equipmentDao.getCountByCondition(sectors,key1,key2);

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
         int result = equipmentDao.insertEquipment(equipment);
         if(result>0){
        	 integralService.managerIntegral(12, equipment.getCreatedBy(), equipment.getId());
        	 LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","仪器设备",equipment.getId(),equipment.getName());
        	 loggerUserDao.addLog(loggerUser);
         }
		 return fileDao.updateFile(equipment.getId());

    }

    /**
     * 修改设备信息
     *
     * @param equipment 参数bean
     * @return
     */
    @Transactional
    public boolean updateEquipment(Equipment equipment) {
    	fileDao.updateFile(equipment.getId());
        int result = equipmentDao.updateEquipment(equipment);
        if (result>0){
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","仪器设备",equipment.getId(),equipment.getName());
            loggerUserDao.addLog(loggerUser);
        }
        return result > 0 ;
    }

    @Transactional
	public boolean deleteEquipment(String id) {
		// TODO Auto-generated method stub
				if(null == id || "".equals(id)){
					return false;
				}
		//删除上传的附件
		fileDao.deleteFile(id);
        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","仪器设备",id,equipmentDao.getEquipmentById(id).getName());
        loggerUserDao.addLog(loggerUser);
		return equipmentDao.deleteEquipment(id);
	}
}
