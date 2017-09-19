package com.innovative.service;

import com.innovative.bean.Demand;
import com.innovative.bean.Message;
import com.innovative.dao.DemandDao;
import com.innovative.dao.MessageDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;



    /**
     * 添加容
     */
    public  boolean addMessage(Message message){
        return (messageDao.addMessage(message)>0 ?true:false);
    }

    /**
     * 行列表页
     * @param pageNum 页数（默认为1）
     * @return
     */
    public Map<String, Object> getMessageList(Integer pageNum){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Message> demands = messageDao.getMessageList(pageInfo.getStartIndex(), pageInfo.getPageSize());
        int totalCount = messageDao.getTotalCount();
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
