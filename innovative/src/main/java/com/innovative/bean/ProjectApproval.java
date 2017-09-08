package com.innovative.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 立项表单
 * @author huang
 *
 */
public class ProjectApproval implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//id
	private String proname;//项目名称
	private String pronum;//项目编号
	private String eoms;//需求工程师
	private String demander;//需求方
	private String contact;//联系方式
	private String demand_bg;//需求背景
	private String demand_core;//需求核心
	private Integer demand_count;//需求量
	private String keyword;//关键词
	private String coopmodel;//合作模式
	private String require;//对对方合作要求
	private String cost;//成本要求	
	private Date completion;//完成时间
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_by;//创建人
	private Date late_date;//最后修改时间
	private String late_by;//最后修改人
	public ProjectApproval() {
		super();
	}
	public ProjectApproval(Integer id, String proname, String pronum, String eoms, String demander, String contact,
			String demand_bg, String demand_core, Integer demand_count, String keyword, String coopmodel,
			String require, String cost, Date completion, String remark, Date create_date, String create_by,
			Date late_date, String late_by) {
		super();
		this.id = id;
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
		this.create_date = create_date;
		this.create_by = create_by;
		this.late_date = late_date;
		this.late_by = late_by;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
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
	public Date getCompletion() {
		return completion;
	}
	public void setCompletion(Date completion) {
		this.completion = completion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getLate_date() {
		return late_date;
	}
	public void setLate_date(Date late_date) {
		this.late_date = late_date;
	}
	public String getLate_by() {
		return late_by;
	}
	public void setLate_by(String late_by) {
		this.late_by = late_by;
	}
	@Override
	public String toString() {
		return "ProjectApproval [id=" + id + ", proname=" + proname + ", pronum=" + pronum + ", eoms=" + eoms
				+ ", demander=" + demander + ", contact=" + contact + ", demand_bg=" + demand_bg + ", demand_core="
				+ demand_core + ", demand_count=" + demand_count + ", keyword=" + keyword + ", coopmodel=" + coopmodel
				+ ", require=" + require + ", cost=" + cost + ", completion=" + completion + ", remark=" + remark
				+ ", create_date=" + create_date + ", create_by=" + create_by + ", late_date=" + late_date
				+ ", late_by=" + late_by + "]";
	}
	
}
