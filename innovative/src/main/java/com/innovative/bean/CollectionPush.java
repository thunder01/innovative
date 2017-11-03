package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 推特信息收藏
 * @author cj
 *
 */
public class CollectionPush implements Serializable {
	
	/**
	 * 推特信息收藏记录
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String comentId; //推特信息id
	private String collectBy; //收藏人
	private String collectByC; //收藏人
	private Timestamp collectAt; //收藏时间
	
	public CollectionPush(){
		
	}

	public CollectionPush(String id, String comentId, String collectBy, String collectByC, Timestamp collectAt) {
		this.id = id;
		this.comentId = comentId;
		this.collectBy = collectBy;
		this.collectByC = collectByC;
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

	public String getCollectByC() {
		return collectByC;
	}

	public void setCollectByC(String collectByC) {
		this.collectByC = collectByC;
	}

	public Timestamp getCollectAt() {
		return collectAt;
	}

	public void setCollectAt(Timestamp collectAt) {
		this.collectAt = collectAt;
	}

	

	

}
