package com.innovative.service;

import com.innovative.bean.Association;
import com.innovative.bean.Demand;
import com.innovative.dao.DemandDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandService {
    @Autowired
    DemandDao demandDao;


    /**
     * 根据ID查找内容
     *
     */
    public  Demand getDemand(int id){
        Demand demand=demandDao.getDemand(id);
        return demand;
    }
    /**
     * 根据ID修改状态
     *
     */
    public  boolean updateDemand(int id){
        return (demandDao.updateDemand(id)>0 ? true:false);
    }
    /**
     * 添加内容
     */
    public  boolean addDemand(Demand demand){
        return (demandDao.addDemand(demand)>0 ?true:false);
    }

    /**
     * 行列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getDemandList(Integer pageNum){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Demand> demands = demandDao.getDemandList(pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = demandDao.getTotalCount();

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
