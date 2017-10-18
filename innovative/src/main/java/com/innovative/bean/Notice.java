package com.innovative.bean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 公告
 */
public class Notice {

    private String id;
    private String content;//内容
    private Timestamp createdAt;//创建时间
    private String createdAts;//创建时间
    private String createdBy;//创建人
    private boolean deleted;//是否删除
    private Timestamp deletedAt;//删除时间
    private String deletedBy;//删除人
    private String fileName;//文件名称
    private boolean isActive;//是否显示
    private int rank;  //等级
    private int rowVersion;  //行版本
    private String summary;//摘要
    private String tags;//标签
    private String title;//标题
    private Timestamp updatedAt;//更新时间
    private String updatedBy;//更新人
    private List<FileBean> filelist;  //文件路径

    public Notice() {
    }

	public Notice(String id, String content, Timestamp createdAt, String createdBy, boolean deleted,
			Timestamp deletedAt, String deletedBy, String fileName, boolean isActive, int rank, int rowVersion,
			String summary, String tags, String title, Timestamp updatedAt, String updatedBy, List<FileBean> filelist) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.fileName = fileName;
		this.isActive = isActive;
		this.rank = rank;
		this.rowVersion = rowVersion;
		this.summary = summary;
		this.tags = tags;
		this.title = title;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.filelist = filelist;
	}
	

	public String getCreatedAts() {
		 DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(createdAt);
	}

	public void setCreatedAts(String createdAts) {
		this.createdAts = createdAts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public List<FileBean> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<FileBean> filelist) {
		this.filelist = filelist;
	}


}
