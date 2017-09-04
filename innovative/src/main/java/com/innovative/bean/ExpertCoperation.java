package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import com.innovative.mybatisHandler.ArrayTypeHandler;

import tk.mybatis.mapper.annotation.ColumnType;

//专家表
public class ExpertCoperation implements Serializable{

	private static final long serialVersionUID = -5311404303222383742L;
	
	private String  name;
	private String  value;
	private String  status;
	private Integer  rank;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
}
