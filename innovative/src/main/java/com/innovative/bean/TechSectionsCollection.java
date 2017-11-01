package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 科技专栏收藏实体
 * @author cj
 *
 */
public class TechSectionsCollection implements Serializable {
	
	/**
	 * 科技专栏实体
	 */
	private static final long serialVersionUID = 855095553561895179L;
	private String id;//主键
	private String sectionId; //科技专栏id
	private String collectBy; //收藏人
	private Timestamp collectAt; //收藏时间
	
	public TechSectionsCollection(){
		
	}

	public TechSectionsCollection(String id, String sectionId, String collectBy, Timestamp collectAt) {
		this.id = id;
		this.sectionId = sectionId;
		this.collectBy = collectBy;
		this.collectAt = collectAt;
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
