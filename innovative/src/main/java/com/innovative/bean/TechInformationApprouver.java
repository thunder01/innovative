package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class TechInformationApprouver implements Serializable {
	
	/**
	 * 主要记录科技资讯用户点赞记录
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String informationId; //推特信息id
	private String approuverBy; //谁赞的
	private Timestamp approuverAt; //赞的时间
	public TechInformationApprouver(){
		
	}
	
	public TechInformationApprouver(String id, String informationId, String approuverBy, Timestamp approuverAt) {
		this.id = id;
		this.informationId = informationId;
		this.approuverBy = approuverBy;
		this.approuverAt = approuverAt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInformationId() {
		return informationId;
	}
	public void setInformationId(String informationId) {
		this.informationId = informationId;
	}
	public String getApprouverBy() {
		return approuverBy;
	}
	public void setApprouverBy(String approuverBy) {
		this.approuverBy = approuverBy;
	}
	public Timestamp getApprouverAt() {
		return approuverAt;
	}
	public void setApprouverAt(Timestamp approuverAt) {
		this.approuverAt = approuverAt;
	}
	

}
