package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 科技资讯
 */
public class Information implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1013904529112097483L;
	private String id;
	private String resource;//来源
	private String title;//主题
	private String time;//时间
	private String resume;//摘要
	private String[] sectors;//领域
	private String[] tags;//标签
	private String cotent;//内容
	private String createAt;//创建时间
	private String createBy;//创建人
	private String updateAt;//更新时间
	private String updateBy;//更新人
	private String updateByC;//更新人
	private String state;//科技资讯的审批状态 0 未通过 1 通过  2 待审批
	private String approuverNum; //点赞数量
	private int todayIsApprouver; //今天是否点赞
	private int isCollection; //则个科技资讯是否已经收藏
	private String imgid;//图片id
	private int count;//阅读量
	private String[] imgUrl;//图片的URL
	
    public Information() {
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


	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getApprouverNum() {
		return approuverNum;
	}

	public void setApprouverNum(String approuverNum) {
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

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	public String[] getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}
	

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	@Override
	public String toString() {
		return "Information [id=" + id + ", resource=" + resource + ", title=" + title + ", time=" + time + ", resume="
				+ resume + ", sectors=" + Arrays.toString(sectors) + ", tags=" + Arrays.toString(tags) + ", cotent="
				+ cotent + ", createAt=" + createAt + ", createBy=" + createBy + ", updateAt=" + updateAt
				+ ", updateBy=" + updateBy + ", updateByC=" + updateByC + ", state=" + state + ", approuverNum="
				+ approuverNum + ", todayIsApprouver=" + todayIsApprouver + ", isCollection=" + isCollection
				+ ", imgid=" + imgid + ", count=" + count + ", imgUrl=" + Arrays.toString(imgUrl) + "]";
	}

	
}
