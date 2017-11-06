package com.innovative.service;

import com.innovative.bean.FeedBack;
import com.innovative.bean.Intelligence;
import com.innovative.bean.Order;
import com.innovative.dao.IntelligenceDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntelligenceService {
    @Autowired
    private IntelligenceDao dao;

    /**
     * 情报池列表页
     */
    public Map<String,Object> getList(Integer pageNum,String userName){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        userName = "{" + userName + "}".trim();
        List<Intelligence>Intelligence=dao.getList(pageInfo.getStartIndex(),pageInfo.getPageSize(),userName);
        int totalCount=dao.getTotalCounts();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("items", Intelligence);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return  map;
    }

    /**
     * 根据ID查询内容
     * @param id
     * @return
     */
    public  Intelligence getIntelligence(int id){
        Intelligence intelligence=dao.getIntelligence(id);
        return  intelligence;
    }

    /**
     * 添加内容
     * @param intelligence
     * @return
     */
    public  boolean addIntelligence(Intelligence intelligence){
        return (dao.addIntelligence(intelligence)>0 ?true:false);
    }
    /**
     * 审核内容
     */
    public  boolean checkIntelligence(Intelligence intelligence){
        return (dao.checkIntelligence(intelligence)>0 ?true:false);
    }
    /**
     * 下单内容
     * @param intelligence
     * @return
     */
    public  boolean downOrder(Intelligence intelligence){
        return  (dao.downOrder(intelligence)>0 ?true:false);
    }

    /**
     * 添加情报反馈
     * @param feedBack
     * @return
     */
    public  boolean addfeedback(FeedBack feedBack){
        return  (dao.addfeedback(feedBack)>0 ?true:false);
    }

    /**
     * 情报信息列表
     * @param pageNum
     * @param userid
     * @return
     */
    public  Map<String ,Object> getFeedBack(Integer pageNum,String userid){
        PageInfo pageInfo=new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<FeedBack> feedBacks=dao.getFeedBack(pageInfo.getStartIndex(),pageInfo.getPageSize(),userid);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("items", feedBacks);
        map.put("totalCount",feedBacks.size());
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return  map;
    }

    /**
     * 查询附表内容
     * @param id
     * @return
     */
    public  List<FeedBack> getfeedBacks(int id){
        return  dao.getfeedBacks(id);
    }
}
