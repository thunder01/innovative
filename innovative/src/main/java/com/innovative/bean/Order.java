package com.innovative.bean;

import java.sql.Timestamp;

/**
 * 订单信息
 * @author fzy
 * @version 1.0
 * */
public class Order {
	private Integer id;
	private Integer demandId;//需求id
	private Integer reportId;//报告id
	private Integer approvalId;//立项报告的id
	private Integer disassembleid;//拆解报告的id
	private Integer status;//订单的状态，暂定，默认为1
	private Timestamp create_date;//创建时间 
	private String create_by;//创建人
	private Timestamp late_date;//创建时间
	private String late_by;//最后修改者
	
	public Order() {
		super();
	}

	public Order(Integer id, Integer demandId, Integer reportId, Integer approvalId, Integer disassembleid,
			Integer status, Timestamp create_date, String create_by, Timestamp late_date, String late_by) {
		super();
		this.id = id;
		this.demandId = demandId;
		this.reportId = reportId;
		this.approvalId = approvalId;
		this.disassembleid = disassembleid;
		this.status = status;
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

	public Integer getDisassembleid() {
		return disassembleid;
	}

	public void setDisassembleid(Integer disassembleid) {
		this.disassembleid = disassembleid;
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

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Timestamp getLate_date() {
		return late_date;
	}

	public void setLate_date(Timestamp late_date) {
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
		return "Order [id=" + id + ", demandId=" + demandId + ", reportId=" + reportId + ", approvalId=" + approvalId
				+ ", disassembleid=" + disassembleid + ", status=" + status + ", create_date=" + create_date
				+ ", create_by=" + create_by + ", late_date=" + late_date + ", late_by=" + late_by + "]";
	}
	
	
}
