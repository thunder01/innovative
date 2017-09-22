package com.innovative.bean;

import java.io.Serializable;
import java.util.Arrays;
/**
 * 立项表单
 */
import java.util.List;
public class ProjectApproval implements Serializable{
	private Integer id;//主键id
	private Integer order_id;//订单id
	private String source_id;//寻源工程师id
	private Integer source_status;//接单状态，此立项是否已有寻源工程师接单，0未接单，1已接单
	private String source_time;//接单时间
	private Integer post_status;//发布状态，0未发布，1已发布
	private String proname;//立项名称、项目名称
	private String pronum;//项目编号
	private String eoms;//需求对接工程师
	private String demander;//需求方
	private String contact;//联系方式
	private String demand_bg;//需求背景
	private String demand_core;//需求核心
	private Integer demand_count;//需求数量
	private String[] keyword;//关键词
	private String coopmodel;//合作模式
	private String require;//合作要求
	private String cost;//成本要求
	private String completion;//项目完成时间
	private String remark;//备注
	private String create_by;//立项表单的创建人
	private String create_date;//创建时间
	private String late_by;//修改人
	private String late_date;//最后修改时间
	private List<Report> reportList;//报告列表
	private String userName;//接单人的名字
	private String message;
	private String confirmName;//确认人
	
	public ProjectApproval() {
		super();
	}

	public ProjectApproval(Integer id, Integer order_id, String source_id, Integer source_status, String source_time,
			Integer post_status, String proname, String pronum, String eoms, String demander, String contact,
			String demand_bg, String demand_core, Integer demand_count, String[] keyword, String coopmodel,
			String require, String cost, String completion, String remark, String create_by, String create_date,
			String late_by, String late_date, List<Report> reportList) {
		super();
		this.id = id;
		this.order_id = order_id;
		this.source_id = source_id;
		this.source_status = source_status;
		this.source_time = source_time;
		this.post_status = post_status;
		this.proname = proname;
		this.pronum = pronum;
		this.eoms = eoms;
		this.demander = demander;
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
		this.create_by = create_by;
		this.create_date = create_date;
		this.late_by = late_by;
		this.late_date = late_date;
		this.reportList = reportList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public Integer getSource_status() {
		return source_status;
	}

	public void setSource_status(Integer source_status) {
		this.source_status = source_status;
	}

	public String getSource_time() {
		return source_time;
	}

	public void setSource_time(String source_time) {
		this.source_time = source_time;
	}

	public Integer getPost_status() {
		return post_status;
	}

	public void setPost_status(Integer post_status) {
		this.post_status = post_status;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getPronum() {
		return pronum;
	}

	public void setPronum(String pronum) {
		this.pronum = pronum;
	}

	public String getEoms() {
		return eoms;
	}

	public void setEoms(String eoms) {
		this.eoms = eoms;
	}

	public String getDemander() {
		return demander;
	}

	public void setDemander(String demander) {
		this.demander = demander;
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

	public Integer getDemand_count() {
		return demand_count;
	}

	public void setDemand_count(Integer demand_count) {
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

	public String getLate_by() {
		return late_by;
	}

	public void setLate_by(String late_by) {
		this.late_by = late_by;
	}

	public String getLate_date() {
		return late_date;
	}

	public void setLate_date(String late_date) {
		this.late_date = late_date;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConfirmName() {
		return confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	@Override
	public String toString() {
		return "ProjectApproval [id=" + id + ", order_id=" + order_id + ", source_id=" + source_id + ", source_status="
				+ source_status + ", source_time=" + source_time + ", post_status=" + post_status + ", proname="
				+ proname + ", pronum=" + pronum + ", eoms=" + eoms + ", demander=" + demander + ", contact=" + contact
				+ ", demand_bg=" + demand_bg + ", demand_core=" + demand_core + ", demand_count=" + demand_count
				+ ", keyword=" + Arrays.toString(keyword) + ", coopmodel=" + coopmodel + ", require=" + require
				+ ", cost=" + cost + ", completion=" + completion + ", remark=" + remark + ", create_by=" + create_by
				+ ", create_date=" + create_date + ", late_by=" + late_by + ", late_date=" + late_date + ", reportList="
				+ reportList + "]";
	}
}
