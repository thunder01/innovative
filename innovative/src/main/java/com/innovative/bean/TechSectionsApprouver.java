package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 科技专栏点赞记录
 * @author cj
 *
 */
public class TechSectionsApprouver implements Serializable {
	
	/**
	 * 主要记录科技资讯用户点赞记录
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String sectionId; //科技专栏id
	private String approuverBy; //谁赞的
	private Timestamp approuverAt; //赞的时间
	public TechSectionsApprouver(){
		
	}
	
	public TechSectionsApprouver(String id, String sectionId, String approuverBy, Timestamp approuverAt) {
		this.id = id;
		this.sectionId = sectionId;
		this.approuverBy = approuverBy;
		this.approuverAt = approuverAt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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

	@Override
	public String toString() {
		return "TechSectionsApprouver [id=" + id + ", sectionId=" + sectionId + ", approuverBy=" + approuverBy
				+ ", approuverAt=" + approuverAt + "]";
	}
	
	

}
