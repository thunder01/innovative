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
	private String  id;
	
	
	public ExpertCoperation(String name, String value, String status, String id) {
		this.name = name;
		this.value = value;
		this.status = status;
		this.id = id;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ExpertCoperation [name=" + name + ", value=" + value + ", status=" + status + ", id=" + id + "]";
	}
	
	
}
