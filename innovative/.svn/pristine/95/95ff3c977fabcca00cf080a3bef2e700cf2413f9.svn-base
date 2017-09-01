package com.innovative.service;

import com.innovative.bean.*;
import com.innovative.dao.FullTextQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FullTextQueryService {

    @Autowired
    private FullTextQueryDao fullTextQueryDao;

    public Map<String, Object> getFullTextQueryResults(String keyWords) {

        String key1 = "%" + keyWords + "%".trim();
        String key2 = "{" + keyWords + "}".trim();

        List<Association> associationList = fullTextQueryDao.getAssociationList(key1, key2);
        List<Equipment> equipmentList = fullTextQueryDao.getEquipmentList(key1, key2);
        List<Expert> expertList = fullTextQueryDao.getExpertList(key1, key2);
        List<Organization> organizationList = fullTextQueryDao.getOrganizationList(key1, key2);
        List<Solution> solutionList = fullTextQueryDao.getSolutionList(key1, key2);
        List<TechnicalReport> technicalReportList = fullTextQueryDao.getTechnicalReportList(key1, key2);

        Map<String, Object> map = new HashMap<>();
        map.put("association", equipmentList);
        map.put("expert", expertList);
        map.put("organization", organizationList);
        map.put("solution", solutionList);
        map.put("association", associationList);
        map.put("technicalReport", technicalReportList);

        return map;
    }
}
