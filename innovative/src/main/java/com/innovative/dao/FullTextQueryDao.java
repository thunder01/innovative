package com.innovative.dao;

import com.innovative.bean.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FullTextQueryDao {

    List<Expert> getExpertList(@Param("key1") String key1, @Param("key2") String key2);

    List<Association> getAssociationList(@Param("key1") String key1, @Param("key2") String key2);

    List<Organization> getOrganizationList(@Param("key1") String key1, @Param("key2") String key2);

    List<TechnicalReport> getTechnicalReportList(@Param("key1") String key1, @Param("key2") String key2);

    List<Solution> getSolutionList(@Param("key1") String key1, @Param("key2") String key2);

    List<Equipment> getEquipmentList(@Param("key1") String key1, @Param("key2") String key2);
}
