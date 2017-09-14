package com.innovative.dao;

import com.innovative.bean.ModRight;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModRightDao {


	List<ModRight> getModRightByType(@Param("rightType") String type);







}
