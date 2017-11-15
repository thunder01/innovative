package com.innovative.dao;

import com.innovative.bean.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Array;
import java.util.List;

public interface StatisticsDao {
    int queryAssociations();
    int queryExperts();
    int queryOrganizations();
    int queryTechnicalReports();
    int querySolutions();
    int queryEquipments();
    int queryDemand();
    int queryReport();
    int queryIntelligence();
    int queryDemandNumber(@Param("date") String date);
    int queryOderV2();
    int queryProject();
    int queryCheckDemand();
    int queryInforScore();
    int queryIntellxun();
    int queryIntellsp();
    int queryIntell();
    int queryIntellNumber(@Param("date") String date);
    int queryGetUserNumber(@Param("date") String date);
    List<Association> selectAssociations();
    List<Expert> selectExperts();
    List<Organization> selectOrganizations();
    List<TechnicalReport> selectTechnicalReports();
    List<Solution> selectSolutions();
    List<Equipment> selectEquipments();

}
