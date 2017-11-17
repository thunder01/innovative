package com.innovative.dao;

import com.innovative.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FullTextQueryDao {

    int getExpertList(@Param("key1") String key1, @Param("key2") String key2);

    int getAssociationList(@Param("key1") String key1, @Param("key2") String key2);

    int getOrganizationList(@Param("key1") String key1, @Param("key2") String key2);

    int getTechnicalReportList(@Param("key1") String key1, @Param("key2") String key2);

    int getSolutionList(@Param("key1") String key1, @Param("key2") String key2);

    int getEquipmentList(@Param("key1") String key1, @Param("key2") String key2);
    int sumTages(@Param("key2") String key2);
}
