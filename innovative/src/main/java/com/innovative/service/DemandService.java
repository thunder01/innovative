package com.innovative.service;

import com.innovative.bean.Demand;
import com.innovative.bean.LoggerUser;
import com.innovative.dao.DemandDao;
import com.innovative.dao.LoggerUserDao;
import com.innovative.dao.OrderDao;
import com.innovative.utils.PageInfo;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandService {
    @Autowired
    DemandDao demandDao;
    @Autowired
    OrderDao  orderDao;
    @Autowired
    LoggerUserDao loggerUserDao;


    /**
     * 根据ID查找内容
     *
     */
    public  Demand getDemand(int id){
        Demand demand=demandDao.getDemand(id);
        if (demand!=null) {
            Integer orderId = orderDao.getOrderIdByDemandId(id);
            demand.setOrderid(orderId);
        }
        return demand;
    }
    /**
     * 根据ID修改状态
     *
     */
    public  boolean updateDemand(Demand demand){
        return (demandDao.updateDemand(demand)>0 ? true:false);
    }
    /**
     * 添加内容
     */
    public  boolean addDemand(Demand demand){
    	int result = demandDao.addDemand(demand);
    	if(result>0){
    		LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","需求下单",demand.getId()+"",demand.getName());
            loggerUserDao.addLog(loggerUser);
    	}
        return (result>0 ?true:false);
    }

    /**
     * 行列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getDemandList(Integer pageNum,String userName){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        userName = "{" + userName + "}".trim();
        List<Demand> demands = demandDao.getDemandList(pageInfo.getStartIndex(), pageInfo.getPageSize(),userName);
        for (Demand d :demands){
            Integer orderId = orderDao.getOrderIdByDemandId(d.getId());
            if(orderId!=null){
            	d.setOrderid(orderId);
            }
        }
        int totalCount = demandDao.getTotalCount(userName);

        Map<String, Object> map = new HashMap<>();
        map.put("items", demands);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }
    /**
     * 行列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getQueryList(Integer pageNum,String userName){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Demand> demands = demandDao.getQueryList(pageInfo.getStartIndex(), pageInfo.getPageSize(),userName);
        int totalCount = demandDao.getTotalCounts(userName);
        Map<String, Object> map = new HashMap<>();
        map.put("items", demands);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return map;
    }
    }
