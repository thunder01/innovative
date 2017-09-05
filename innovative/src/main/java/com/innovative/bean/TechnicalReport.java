package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 技术报告
 */
public class TechnicalReport implements Serializable {

    private int id;
    private String content;//正文
    private Timestamp createdAt;//创建时间
    private String createdBy;//创建人
    private boolean deleted;//是否删除
    private Timestamp deletedAt;//删除时间
    private String deletedBy;//删除操作人
    private boolean isActive;//显示或隐藏
    private String name;//标题名称
    private String[] pictures;//图片
    private int rank;
    private int rowVersion;
    private String[] sectors;//行业领域
    private String summary;//摘要
    private String[] tags;//标签
    private Timestamp updatedAt;//更新时间
    private String updatedBy;//更新人
    private String file; //文件

    public TechnicalReport() {
    }


    public TechnicalReport(int id, String content, Timestamp createdAt, String createdBy, boolean deleted,
			Timestamp deletedAt, String deletedBy, boolean isActive, String name, String[] pictures, int rank,
			int rowVersion, String[] sectors, String summary, String[] tags, Timestamp updatedAt, String updatedBy,
			String file) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.isActive = isActive;
		this.name = name;
		this.pictures = pictures;
		this.rank = rank;
		this.rowVersion = rowVersion;
		this.sectors = sectors;
		this.summary = summary;
		this.tags = tags;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.file = file;
	}


	public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String[] getPictures() {
		return pictures;
	}


	public void setPictures(String[] pictures) {
		this.pictures = pictures;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    @Override
    public String toString() {
        return "TechnicalReport{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", deleted=" + deleted +
                ", deletedAt=" + deletedAt +
                ", deletedBy='" + deletedBy + '\'' +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", pictures='" + pictures + '\'' +
                ", rank=" + rank +
                ", rowVersion=" + rowVersion +
                ", sectors='" + sectors + '\'' +
                ", summary='" + summary + '\'' +
                ", tags='" + tags + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
