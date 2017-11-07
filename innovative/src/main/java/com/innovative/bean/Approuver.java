package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 信息推特点赞记录
 * @author cj
 *
 */
public class Approuver implements Serializable {
	
	/**
	 * 主要记录用户点赞记录
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String comentId; //推特信息id
	private String approuverBy; //谁赞的
	private String approuverByC; //谁赞的(姓名)
	private Timestamp approuverAt; //赞的时间
	
	private Informationpush informationpush;
	
	public Approuver(){
		
	}
	public Approuver(String id, String comentId, String approuverBy, String approuverByC, Timestamp approuverAt) {
		this.id = id;
		this.comentId = comentId;
		this.approuverBy = approuverBy;
		this.approuverByC = approuverByC;
		this.approuverAt = approuverAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComentId() {
		return comentId;
	}
	public void setComentId(String comentId) {
		this.comentId = comentId;
	}
	public String getApprouverBy() {
		return approuverBy;
	}
	public void setApprouverBy(String approuverBy) {
		this.approuverBy = approuverBy;
	}
	public String getApprouverByC() {
		return approuverByC;
	}
	public void setApprouverByC(String approuverByC) {
		this.approuverByC = approuverByC;
	}
	public Timestamp getApprouverAt() {
		return approuverAt;
	}
	public void setApprouverAt(Timestamp approuverAt) {
		this.approuverAt = approuverAt;
	}
	public Informationpush getInformationpush() {
		return informationpush;
	}
	public void setInformationpush(Informationpush informationpush) {
		this.informationpush = informationpush;
	}
	
	

}
