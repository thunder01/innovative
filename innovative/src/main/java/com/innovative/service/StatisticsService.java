package com.innovative.service;

import com.innovative.bean.*;
import com.innovative.dao.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    StatisticsDao statisticsDao;
    /**
     * 资源数
     */
    public  int queryAssociations(){
        return  statisticsDao.queryAssociations();
    }
    public  int queryExperts(){
        return  statisticsDao.queryExperts();
    }
    public  int queryOrganizations(){
        return statisticsDao.queryOrganizations();
    }
    public  int queryTechnicalReports(){
        return  statisticsDao.queryTechnicalReports();

    }
    public  int querySolutions(){
        return  statisticsDao.querySolutions();
    }
    public  int queryEquipments(){
        return  statisticsDao.queryEquipments();
    }
    /**
     * 需求数量
     */
    public  int queryDemand(){
        return  statisticsDao.queryDemand();
    }
    /**
     * 方案数
     */
    public  int queryReport(){
        return  statisticsDao.queryReport();
    }

    /**
     * 情报数量
     */
    public  int queryIntelligence(){
        return  statisticsDao.queryIntelligence();
    }
    /**
     * 需求下单数量
     */
    public  int queryDemandNumber(String date){
        return  statisticsDao.queryDemandNumber(date);
    }

    /**
     * 需求下单已完成
     */
    public  int queryOderV2(){
        return  statisticsDao.queryOderV2();
    }
    /**
     * 需求寻源中
     */
    public int queryProject(){
        return  statisticsDao.queryProject();
    }
    /**
     * 需求审核中
     */
    public int queryCheckDemand(){
        return statisticsDao.queryCheckDemand();
    }
    /**
     * 情报完成中
     */
    public  int queryInforScore(){
        return statisticsDao.queryInforScore();
    }
    /**
     * 情报寻源中
     */
    public  int queryIntellxun(){
        return  statisticsDao.queryIntellxun();
    }
    /**
     * 情报审批中
     */
    public  int queryIntellsp(){
        return  statisticsDao.queryIntellsp();
    }
    /**
     * 情报下单
     */
    public  int queryIntell(){
        return statisticsDao.queryIntell();
    }
    /**
     * 情报分时间下单数量
     */
    public  int queryIntellNumber(String date){
        return statisticsDao.queryIntellNumber(date);
    }
    /**
     * 用户活跃数
     */
    public  int queryGetUserNumber(String date){
        return  statisticsDao.queryGetUserNumber(date);
    }
    /**
     * 标签分类
     */
    public List<Association> selectAssociations(){
        return statisticsDao.selectAssociations();
    };
    public List<Expert> selectExperts(){
        return statisticsDao.selectExperts();
    };
    public List<Organization> selectOrganizations(){
        return statisticsDao.selectOrganizations();
    };
    public List<TechnicalReport> selectTechnicalReports(){
        return statisticsDao.selectTechnicalReports();
    };
    public List<Solution> selectSolutions(){
        return statisticsDao.selectSolutions();
    };
    public List<Equipment> selectEquipments(){
        return statisticsDao.selectEquipments();
    };
}
