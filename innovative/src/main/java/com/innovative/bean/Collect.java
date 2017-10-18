package com.innovative.bean;

import java.io.Serializable;
import java.util.Arrays;

public class Collect implements Serializable{
	/**
	 * 
	 */
	private Integer id;
	private String proname;
	private String source_name;
	private String contact;
	private String demand_bg;
	private String demand_core;
	private String demand_count;
	private String[] keyword;
	private String coopmodel;
	private String require;
	private String cost;
	private String completion;
	private String remark;
	private String[] collect_content;
	private String files;
	private String create_by;
	private String create_date;
	private String last_by;
	private String last_date;
	private String delete_by;
	private String delete_date;
	private Integer delete_status;
	public Collect() {
		super();
	}
	public Collect(Integer id, String proname, String source_name, String contact, String demand_bg,
			String demand_core, String demand_count, String[] keyword, String coopmodel, String require, String cost,
			String completion, String remark, String[] collect_content, String files, String create_by,
			String create_date, String last_by, String last_date, String delete_by, String delete_date,
			Integer delete_status) {
		super();
		this.id = id;
		this.proname = proname;
		this.source_name = source_name;
		this.contact = contact;
		this.demand_bg = demand_bg;
		this.demand_core = demand_core;
		this.demand_count = demand_count;
		this.keyword = keyword;
		this.coopmodel = coopmodel;
		this.require = require;
		this.cost = cost;
		this.completion = completion;
		this.remark = remark;
		this.collect_content = collect_content;
		this.files = files;
		this.create_by = create_by;
		this.create_date = create_date;
		this.last_by = last_by;
		this.last_date = last_date;
		this.delete_by = delete_by;
		this.delete_date = delete_date;
		this.delete_status = delete_status;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDemand_bg() {
		return demand_bg;
	}
	public void setDemand_bg(String demand_bg) {
		this.demand_bg = demand_bg;
	}
	public String getDemand_core() {
		return demand_core;
	}
	public void setDemand_core(String demand_core) {
		this.demand_core = demand_core;
	}
	public String getDemand_count() {
		return demand_count;
	}
	public void setDemand_count(String demand_count) {
		this.demand_count = demand_count;
	}
	public String[] getKeyword() {
		return keyword;
	}
	public void setKeyword(String[] keyword) {
		this.keyword = keyword;
	}
	public String getCoopmodel() {
		return coopmodel;
	}
	public void setCoopmodel(String coopmodel) {
		this.coopmodel = coopmodel;
	}
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getCompletion() {
		return completion;
	}
	public void setCompletion(String completion) {
		this.completion = completion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String[] getCollect_content() {
		return collect_content;
	}
	public void setCollect_content(String[] collect_content) {
		this.collect_content = collect_content;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getLast_by() {
		return last_by;
	}
	public void setLast_by(String last_by) {
		this.last_by = last_by;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String getDelete_by() {
		return delete_by;
	}
	public void setDelete_by(String delete_by) {
		this.delete_by = delete_by;
	}
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public Integer getDelete_status() {
		return delete_status;
	}
	public void setDelete_status(Integer delete_status) {
		this.delete_status = delete_status;
	}
	@Override
	public String toString() {
		return "Collection [id=" + id + ", proname=" + proname + ", source_name=" + source_name + ", contact=" + contact
				+ ", demand_bg=" + demand_bg + ", demand_core=" + demand_core + ", demand_count=" + demand_count
				+ ", keyword=" + Arrays.toString(keyword) + ", coopmodel=" + coopmodel + ", require=" + require
				+ ", cost=" + cost + ", completion=" + completion + ", remark=" + remark + ", collect_content="
				+ Arrays.toString(collect_content) + ", files=" + files + ", create_by=" + create_by + ", create_date="
				+ create_date + ", last_by=" + last_by + ", last_date=" + last_date + ", delete_by=" + delete_by
				+ ", delete_date=" + delete_date + ", delete_status=" + delete_status + "]";
	}
	
	
}
