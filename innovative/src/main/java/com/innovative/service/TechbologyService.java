package com.innovative.service;

import com.innovative.bean.Technology;
import com.innovative.bean.TechnologyDz;
import com.innovative.dao.TechnologyDao;
import com.innovative.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TechbologyService {

    @Autowired
     private  TechnologyDao dao;

    /**
     * 添加内容
     * @param technology
     * @return
     */
    public  boolean addTechnology(Technology technology){
        return (dao.addTechnology(technology)>0 ?true:false);
    }

    /**
     * 科技资讯查询
     * @param pageNum
     * @return
     */
    public Map<String,Object> getinformation(Integer pageNum){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Technology> technologyList=dao.getinformation(pageInfo.getStartIndex(),pageInfo.getPageSize());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("items", technologyList);
        map.put("totalCount", technologyList.size());
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return  map;
    }

    /**
     * 科技专栏查询
     * @param pageNum
     * @return
     */
    public Map<String,Object> getSpecial(Integer pageNum){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        List<Technology> technologyList=dao.getSpecial(pageInfo.getStartIndex(),pageInfo.getPageSize());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("items", technologyList);
        map.put("totalCount", technologyList.size());
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        return  map;
    }

    /**
     * 添加点赞
     * @param technologyDz
     * @return
     */
    public  boolean adddz(TechnologyDz technologyDz){
        return (dao.adddz(technologyDz)>0 ?true:false);
    }

    /**
     * 点赞次数
     * @return
     */
    public  int getTotalCounts(int id){
        return  dao.getTotalCounts(id);
    }
    /**
     * 科技资讯详情页面
     */
    public  Technology getTechnologyzx(int id){
        return dao.getTechnologyzx(id);
    }
    /**
     * 科技专栏详情页面
     */
    public  Technology getTechnologyzl(int id){
        return dao.getTechnologyzl(id);
    }
}
