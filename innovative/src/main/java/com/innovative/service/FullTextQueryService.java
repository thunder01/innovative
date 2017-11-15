package com.innovative.service;

import com.innovative.bean.*;
import com.innovative.dao.FullTextQueryDao;
import com.innovative.utils.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FullTextQueryService {

    @Autowired
    private FullTextQueryDao fullTextQueryDao;
    
    private int flag = 0;

//    public Map<String, Object> getFullTextQueryResults(String keyWords) {
//
////        String key1 = "%" + keyWords + "%".trim();
////        String key2 = "{" + keyWords + "}".trim();
//
////        List<Association> associationList = fullTextQueryDao.getAssociationList(key1, key2);
////        List<Equipment> equipmentList = fullTextQueryDao.getEquipmentList(key1, key2);
////        List<Expert> expertList = fullTextQueryDao.getExpertList(key1, key2);
////        List<Organization> organizationList = fullTextQueryDao.getOrganizationList(key1, key2);
////        List<Solution> solutionList = fullTextQueryDao.getSolutionList(key1, key2);
////        List<TechnicalReport> technicalReportList = fullTextQueryDao.getTechnicalReportList(key1, key2);
//
////        Map<String, Object> map = new HashMap<>();
////        map.put("association", equipmentList);
////        map.put("expert", expertList);
////        map.put("organization", organizationList);
////        map.put("solution", solutionList);
////        map.put("association", associationList);
////        map.put("technicalReport", technicalReportList);
//
//
////        map.put("items", experts);
////        map.put("totalCount", totalCount);
////        map.put("Count", pageInfo.getPageSize());
////        map.put("itemCount", pageInfo.getPageSize());
////        map.put("offset", pageInfo.getStartIndex());
////        map.put("limit", pageInfo.getPageSize());
//
////        return map;
//    }

	public Map<String, Object> getFullTextQueryResults(String keyWords, Integer page) {
        String key1 = "%" + keyWords + "%".trim();
        String key2 = "{" + keyWords + "}".trim();

        int associationList = fullTextQueryDao.getAssociationList(key1, key2);
        int equipmentList = fullTextQueryDao.getEquipmentList(key1, key2);
        int expertList = fullTextQueryDao.getExpertList(key1, key2);
        int organizationList = fullTextQueryDao.getOrganizationList(key1, key2);
        int solutionList = fullTextQueryDao.getSolutionList(key1, key2);
        int technicalReportList = fullTextQueryDao.getTechnicalReportList(key1, key2);

//        Map<String, Object> map = new HashMap<>();
//        map.put("association", equipmentList);
//        map.put("expert", expertList);
//        map.put("organization", organizationList);
//        map.put("solution", solutionList);
//        map.put("association", associationList);
//        map.put("technicalReport", technicalReportList);
        
        Map<String, Object> sjmap = new HashMap<>();
        int totalCount = associationList+equipmentList+expertList+organizationList+solutionList+technicalReportList;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(page);
       // sjmap.put("items", map);
        sjmap.put("totalCount", totalCount);
        sjmap.put("Count", pageInfo.getPageSize());
        sjmap.put("itemCount", pageInfo.getPageSize());
        sjmap.put("offset", pageInfo.getStartIndex());
        sjmap.put("limit", pageInfo.getPageSize());
          
        
        

        return sjmap;	
      }
}
