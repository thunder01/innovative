package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 订单信息
 * @author fzy
 * @version 2.0
 * */
public class Order implements Serializable{
	private Integer id;
	private Integer demandId;//需求id
	private Integer reportId;//报告id
	private Integer approvalId;//立项报告的id
	private Integer disassembleId;//拆解报告的id
	private Integer status;//订单的状态，暂定，默认为1
	private Timestamp create_date;//需求工程师的接单时间 
	private Integer create_byId;//需求工程师(用户表主键)
	private Timestamp late_date;//寻源工程师的接单时间
	private Integer late_byId;//寻源工程师
	
	public Order() {
		super();
	}

	public Order(Integer id,Integer disassembleId) {
		this.id = id;
		this.disassembleId = disassembleId;
	}
	
	public Order(Integer id, Integer demandId, Integer reportId, Integer approvalId, Integer disassembleId,
			Integer status, Timestamp create_date, Integer create_byId, Timestamp late_date, Integer late_byId) {
		super();
		this.id = id;
		this.demandId = demandId;
		this.reportId = reportId;
		this.approvalId = approvalId;
		this.disassembleId = disassembleId;
		this.status = status;
		this.create_date = create_date;
		this.create_byId = create_byId;
		this.late_date = late_date;
		this.late_byId = late_byId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getDisassembleId() {
		return disassembleId;
	}

	public void setDisassembleId(Integer disassembleId) {
		this.disassembleId = disassembleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Integer getCreate_byId() {
		return create_byId;
	}

	public void setCreate_byId(Integer create_byId) {
		this.create_byId = create_byId;
	}

	public Timestamp getLate_date() {
		return late_date;
	}

	public void setLate_date(Timestamp late_date) {
		this.late_date = late_date;
	}

	public Integer getLate_byId() {
		return late_byId;
	}

	public void setLate_byId(Integer late_byId) {
		this.late_byId = late_byId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", demandId=" + demandId + ", reportId=" + reportId + ", approvalId=" + approvalId
				+ ", disassembleId=" + disassembleId + ", status=" + status + ", create_date=" + create_date
				+ ", create_byId=" + create_byId + ", late_date=" + late_date + ", late_byId=" + late_byId + "]";
	}

}
