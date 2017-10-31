package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class InformationPushPartager implements Serializable {
	
	/**
	 * 推特信息分享
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String pushId; //推特信息id
	private String partagerBy; //分享人
	private Timestamp partagerAt; //分享时间
	private List<Informationpush> informationpushs; //分享时间一个用户分享的推特信息
	List<FileBean> filelist ;//文件
	public InformationPushPartager(){
		
	}
	public InformationPushPartager(String id, String pushId, String partagerBy, Timestamp partagerAt,
			List<Informationpush> informationpushs, List<FileBean> filelist) {
		this.id = id;
		this.pushId = pushId;
		this.partagerBy = partagerBy;
		this.partagerAt = partagerAt;
		this.informationpushs = informationpushs;
		this.filelist = filelist;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	public String getPartagerBy() {
		return partagerBy;
	}
	public void setPartagerBy(String partagerBy) {
		this.partagerBy = partagerBy;
	}
	public Timestamp getPartagerAt() {
		return partagerAt;
	}
	public void setPartagerAt(Timestamp partagerAt) {
		this.partagerAt = partagerAt;
	}
	public List<Informationpush> getInformationpushs() {
		return informationpushs;
	}
	public void setInformationpushs(List<Informationpush> informationpushs) {
		this.informationpushs = informationpushs;
	}
	public List<FileBean> getFilelist() {
		return filelist;
	}
	public void setFilelist(List<FileBean> filelist) {
		this.filelist = filelist;
	}
	


	
	
	

}
