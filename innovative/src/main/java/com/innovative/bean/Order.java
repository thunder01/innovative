package com.innovative.bean;

import java.io.Serializable;

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
	private String create_date;//需求工程师的接单时间 
	private String create_byId;//需求工程师(用户表主键)
	private String late_date;//寻源工程师的接单时间
	private String late_byId;//寻源工程师
	private Demand demand;
	public Order() {
		super();
	}
	
	public Order(Integer id,Integer disassembleId) {
		super();
		this.id=id;
		this.disassembleId=disassembleId;
	}
	
	public Order(Integer id, Integer demandId, Integer reportId, Integer approvalId, Integer disassembleId,
			Integer status, String create_date, String create_byId, String late_date, String late_byId) {
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
	public String getCreate_date() {
		if (create_date!=null) {
			return create_date.substring(0, 16);
		}else {
			return create_date;
		}
		
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_byId() {
		return create_byId;
	}
	public void setCreate_byId(String create_byId) {
		this.create_byId = create_byId;
	}
	public String getLate_date() {
		if (late_date!=null) {
			return late_date.substring(0, 16);
		}else {
			return late_date;
		}
		
	}
	public void setLate_date(String late_date) {
		this.late_date = late_date;
	}
	public String getLate_byId() {
		return late_byId;
	}
	public void setLate_byId(String late_byId) {
		this.late_byId = late_byId;
	}
	
	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", demandId=" + demandId + ", reportId=" + reportId + ", approvalId=" + approvalId
				+ ", disassembleId=" + disassembleId + ", status=" + status + ", create_date=" + create_date
				+ ", create_byId=" + create_byId + ", late_date=" + late_date + ", late_byId=" + late_byId + ", demand="
				+ demand + "]";
	}
}
