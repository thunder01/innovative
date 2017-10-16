package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 轮播图
 */
public class Carousel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1013904529112097483L;
	private String id;
	private String link;
    private Timestamp activationDate;//生效时间
    private String background;//背景
    private Timestamp createdAt;//创建时间
    private String createdBy;//创建人
    private boolean deleted;//是否删除
    private Timestamp deletedAt;//删除时间
    private String deletedBy;//删除人
    private int duration;//持续时间
    private Timestamp expirationDate;//截至日期
    private boolean isActive;//是否显示
    private String picture;//图片地址
    private String placement;//图片位置
    private int publishStatus;//发布状态
    private Timestamp publishTime;//发布时间
    private int rank;
    private int rowVersion;
    private String summary;//摘要
    private String tags;//标签
    private String thumbnail;//略缩图
    private String title;//标题
    private Timestamp updatedAt;//更新时间
    private String updatedBy;//更新人
    private String url;//地址
    private String ids[];//地址
    private String links[];//地址
    private String titles[];//标题

    public Carousel() {
    }


	


	public Carousel(String id, String link, Timestamp activationDate, String background, Timestamp createdAt,
			String createdBy, boolean deleted, Timestamp deletedAt, String deletedBy, int duration,
			Timestamp expirationDate, boolean isActive, String picture, String placement, int publishStatus,
			Timestamp publishTime, int rank, int rowVersion, String summary, String tags, String thumbnail,
			String title, Timestamp updatedAt, String updatedBy, String url, String[] ids, String[] links,
			String[] titles) {
		super();
		this.id = id;
		this.link = link;
		this.activationDate = activationDate;
		this.background = background;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.duration = duration;
		this.expirationDate = expirationDate;
		this.isActive = isActive;
		this.picture = picture;
		this.placement = placement;
		this.publishStatus = publishStatus;
		this.publishTime = publishTime;
		this.rank = rank;
		this.rowVersion = rowVersion;
		this.summary = summary;
		this.tags = tags;
		this.thumbnail = thumbnail;
		this.title = title;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.url = url;
		this.ids = ids;
		this.links = links;
		this.titles = titles;
	}





	public String[] getTitles() {
		return titles;
	}





	public void setTitles(String[] titles) {
		this.titles = titles;
	}





	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Timestamp activationDate) {
		this.activationDate = activationDate;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public int getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(int publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRowVersion() {
		return rowVersion;
	}

	public void setRowVersion(int rowVersion) {
		this.rowVersion = rowVersion;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getLinks() {
		return links;
	}

	public void setLinks(String[] links) {
		this.links = links;
	}
	

}
