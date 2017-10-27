package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class CollectionPush implements Serializable {
	
	/**
	 * 收藏记录
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String comentId; //推特信息id
	private String collectBy; //收藏人
	private Timestamp collectAt; //收藏时间
	
	public CollectionPush(){
		
	}

	public CollectionPush(String id, String comentId, String collectBy, Timestamp collectAt) {
		this.id = id;
		this.comentId = comentId;
		this.collectBy = collectBy;
		this.collectAt = collectAt;
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
	
	
	

}
