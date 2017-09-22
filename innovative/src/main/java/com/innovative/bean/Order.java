package com.innovative.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 * @author fzy
 * @version 3.0
 * */
public class Order implements Serializable{
	private Integer id;//主键id
	private Integer demandId;//需求的id
	private String create_byId;//接单人，需求工程师
	private String create_date;//接单时间
	private Double workpoint;//项目工分
	private Integer eoms_service_score;//需求服务评分
	private String eoms_service_appraise;//需求服务评价
	private Integer source_service_score;//寻源服务评分
	private String source_service_appraise;//寻源服务评价
	private Integer confirm_status;//订单确认状态，拆解报告是否已经被确认，0未确认，1已确认
	private String pass_date;//拆解报告确认时间
	private String pass_by;//拆解报告确认人
	private Integer pass_status;//拆解报告确认状态
	private Demand demand;//需求信息
	private List<ProjectApproval> approvalList;//立项表单集合
	
	public Order() {
		super();
	}

	public Order(Integer id, Integer demandId, String create_byId, String create_date, Double workpoint,
			Integer eoms_service_score, String eoms_service_appraise, Integer source_service_score,
			String source_service_appraise, Integer confirm_status, String pass_date, String pass_by,
			Integer pass_status, Demand demand, List<ProjectApproval> approvalList) {
		super();
		this.id = id;
		this.demandId = demandId;
		this.create_byId = create_byId;
		this.create_date = create_date;
		this.workpoint = workpoint;
		this.eoms_service_score = eoms_service_score;
		this.eoms_service_appraise = eoms_service_appraise;
		this.source_service_score = source_service_score;
		this.source_service_appraise = source_service_appraise;
		this.confirm_status = confirm_status;
		this.pass_date = pass_date;
		this.pass_by = pass_by;
		this.pass_status = pass_status;
		this.demand = demand;
		this.approvalList = approvalList;
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

	public String getCreate_byId() {
		return create_byId;
	}

	public void setCreate_byId(String create_byId) {
		this.create_byId = create_byId;
	}

	public String getCreate_date() {
		if (create_date!=null) {
			return create_date.substring(0, 16);
		}
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public Double getWorkpoint() {
		return workpoint;
	}

	public void setWorkpoint(Double workpoint) {
		this.workpoint = workpoint;
	}

	public Integer getEoms_service_score() {
		return eoms_service_score;
	}

	public void setEoms_service_score(Integer eoms_service_score) {
		this.eoms_service_score = eoms_service_score;
	}

	public String getEoms_service_appraise() {
		return eoms_service_appraise;
	}

	public void setEoms_service_appraise(String eoms_service_appraise) {
		this.eoms_service_appraise = eoms_service_appraise;
	}

	public Integer getSource_service_score() {
		return source_service_score;
	}

	public void setSource_service_score(Integer source_service_score) {
		this.source_service_score = source_service_score;
	}

	public String getSource_service_appraise() {
		return source_service_appraise;
	}

	public void setSource_service_appraise(String source_service_appraise) {
		this.source_service_appraise = source_service_appraise;
	}

	public Integer getConfirm_status() {
		return confirm_status;
	}

	public void setConfirm_status(Integer confirm_status) {
		this.confirm_status = confirm_status;
	}

	public String getPass_date() {
		return pass_date;
	}

	public void setPass_date(String pass_date) {
		this.pass_date = pass_date;
	}

	public String getPass_by() {
		return pass_by;
	}

	public void setPass_by(String pass_by) {
		this.pass_by = pass_by;
	}

	public Integer getPass_status() {
		return pass_status;
	}

	public void setPass_status(Integer pass_status) {
		this.pass_status = pass_status;
	}

	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	public List<ProjectApproval> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ProjectApproval> approvalList) {
		this.approvalList = approvalList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", demandId=" + demandId + ", create_byId=" + create_byId + ", create_date="
				+ create_date + ", workpoint=" + workpoint + ", eoms_service_score=" + eoms_service_score
				+ ", eoms_service_appraise=" + eoms_service_appraise + ", source_service_score=" + source_service_score
				+ ", source_service_appraise=" + source_service_appraise + ", confirm_status=" + confirm_status
				+ ", pass_date=" + pass_date + ", pass_by=" + pass_by + ", pass_status=" + pass_status + ", demand="
				+ demand + ", approvalList=" + approvalList + "]";
	}
	
}
