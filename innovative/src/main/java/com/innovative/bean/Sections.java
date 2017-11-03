package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 科技专栏
 */
public class Sections implements Serializable {
	private static final long serialVersionUID = -1013904529112097483L;
	private String id;
	private String resource;//来源
	private String title;//主题
	private String time;//时间
	private String resume;//摘要
	private String[] sectors;//领域
	private String[] tags;//标签
	private String cotent;//内容
	private Timestamp createAt;//创建时间
	private String createBy;//创建人
	private Timestamp updateAt;//更新时间
	private String updateBy;//更新人
	private String updateByC;//更新人
	private String state;//审批状态，0 未审批 1 审批完成 2审核不通过
	private Integer approuverNum;//今天是否点赞
	private int todayIsApprouver;//今天是否点赞
	private int isCollection;//是否收藏
	
    public Sections() {
    }

	public Sections(String id, String resource, String title, String time, String resume, String[] sectors,
			String[] tags, String cotent, Timestamp createAt, String createBy, Timestamp updateAt, String updateBy,
			String updateByC, String state, Integer approuverNum, int todayIsApprouver, int isCollection) {
		this.id = id;
		this.resource = resource;
		this.title = title;
		this.time = time;
		this.resume = resume;
		this.sectors = sectors;
		this.tags = tags;
		this.cotent = cotent;
		this.createAt = createAt;
		this.createBy = createBy;
		this.updateAt = updateAt;
		this.updateBy = updateBy;
		this.updateByC = updateByC;
		this.state = state;
		this.approuverNum = approuverNum;
		this.todayIsApprouver = todayIsApprouver;
		this.isCollection = isCollection;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String[] getSectors() {
		return sectors;
	}

	public void setSectors(String[] sectors) {
		this.sectors = sectors;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getCotent() {
		return cotent;
	}

	public void setCotent(String cotent) {
		this.cotent = cotent;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateByC() {
		return updateByC;
	}

	public void setUpdateByC(String updateByC) {
		this.updateByC = updateByC;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getApprouverNum() {
		return approuverNum;
	}

	public void setApprouverNum(Integer approuverNum) {
		this.approuverNum = approuverNum;
	}

	public int getTodayIsApprouver() {
		return todayIsApprouver;
	}

	public void setTodayIsApprouver(int todayIsApprouver) {
		this.todayIsApprouver = todayIsApprouver;
	}

	public int getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(int isCollection) {
		this.isCollection = isCollection;
	}

	
    
	

}
