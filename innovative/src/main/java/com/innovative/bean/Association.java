package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;


//协会表
public class Association implements Serializable {


	private static final long serialVersionUID = 7867013951701463970L;
	private int id;//主键
	private String availableResources; //协会可用资源
	private String contact;   //联系方式
	private int cooperationStatus;   //合作状态
	private Timestamp createdAt;    //创建时间
	private String createdBy;      //创建来源
	private boolean deleted;    //是否删除
	private Timestamp deletedAt;  //删除时间
	private String deletedBy;    //删除来源
	private String duration;   //协会合作期限
	private String introduction; //协会简介
	private boolean isActive;    //是否活跃
	private String logo;    //协会徽标
	private String name;    //协会名称
	private String nature;  //协会性质
	private int rank;  //等级
	private int rowVersion;  //版本
	private String[] sectors;  //行业领域
	private String[] tags;   //标签
	private Timestamp updatedAt;  //修改时间
	private String updatedBy;   //修改来源
	private String website;   //网站链接
	private String file;  //文件


	public Association() {
	}



	public Association(int id, String availableResources, String contact, int cooperationStatus, Timestamp createdAt,
			String createdBy, boolean deleted, Timestamp deletedAt, String deletedBy, String duration,
			String introduction, boolean isActive, String logo, String name, String nature, int rank, int rowVersion,
			String[] sectors, String[] tags, Timestamp updatedAt, String updatedBy, String website, String file) {
		super();
		this.id = id;
		this.availableResources = availableResources;
		this.contact = contact;
		this.cooperationStatus = cooperationStatus;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.duration = duration;
		this.introduction = introduction;
		this.isActive = isActive;
		this.logo = logo;
		this.name = name;
		this.nature = nature;
		this.rank = rank;
		this.rowVersion = rowVersion;
		this.sectors = sectors;
		this.tags = tags;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.website = website;
		this.file = file;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvailableResources() {
		return availableResources;
	}

	public void setAvailableResources(String availableResources) {
		this.availableResources = availableResources;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getCooperationStatus() {
		return cooperationStatus;
	}

	public void setCooperationStatus(int cooperationStatus) {
		this.cooperationStatus = cooperationStatus;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}


	@Override
	public String toString() {
		return "Association{" +
				"id=" + id +
				", availableResources='" + availableResources + '\'' +
				", contact='" + contact + '\'' +
				", cooperationStatus=" + cooperationStatus +
				", createdAt=" + createdAt +
				", createdBy='" + createdBy + '\'' +
				", deleted=" + deleted +
				", deletedAt=" + deletedAt +
				", deletedBy='" + deletedBy + '\'' +
				", duration='" + duration + '\'' +
				", introduction='" + introduction + '\'' +
				", isActive=" + isActive +
				", logo='" + logo + '\'' +
				", name='" + name + '\'' +
				", nature='" + nature + '\'' +
				", rank=" + rank +
				", rowVersion=" + rowVersion +
				", sectors='" + sectors + '\'' +
				", tags='" + tags + '\'' +
				", updatedAt=" + updatedAt +
				", updatedBy='" + updatedBy + '\'' +
				", website='" + website + '\'' +
				", file='" + file + '\'' +
				'}';
	}
}
