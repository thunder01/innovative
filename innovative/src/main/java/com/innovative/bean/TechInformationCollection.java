package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 科技资讯
 * @author cj
 *
 */
public class TechInformationCollection implements Serializable {
	
	/**
	 * 科技资讯
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String informationId; //科技资讯id
	private String collectBy; //收藏人
	private Timestamp collectAt; //收藏时间
	
	private Information information;
	private User user;
	
	public TechInformationCollection(){
		
	}

	public TechInformationCollection(String id, String informationId, String collectBy, Timestamp collectAt) {
		this.id = id;
		this.informationId = informationId;
		this.collectBy = collectBy;
		this.collectAt = collectAt;
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

	public String getCollectBy() {
		return collectBy;
	}

	public void setCollectBy(String collectBy) {
		this.collectBy = collectBy;
	}

	public Timestamp getCollectAt() {
		return collectAt;
	}

	public void setCollectAt(Timestamp collectAt) {
		this.collectAt = collectAt;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	

}
