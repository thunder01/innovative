package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

//机构表
public class Organization implements Serializable{

	private static final long serialVersionUID = -934356283867468542L;

	private String id;//主键
	private String achievements;//已合作项目及成果
	private String contact;//联系方式
	private int cooperationStatus;//合作状态
	private Timestamp createdAt;//创建时间
	private String createdBy;//创建来源
	private boolean deleted;//是否删除
	private Timestamp deletedAt;//删除时间
	private String deletedBy;//删除来源
	private String introduction;//机构简介
	private boolean isActive;//是否在线，活跃
	private String logo;//机构徽标
	private String name;//机构名称
	private String nature;//机构性质
	private int rank;   //等级
	private int rowVersion;  //版本
	private String[] sectors;//行业领域
	private String[] tags;//标签
	private Timestamp updatedAt;//修改时间
	private String updatedBy;//修改来源
	private String website;//网站链接
	private String file;  //文件
	private Map cooperationStatusMap;
    List<FileBean> filelist ;
    private int fileSize;//


	public Organization() {
	}


	public Organization(String id, String achievements, String contact, int cooperationStatus, Timestamp createdAt,
			String createdBy, boolean deleted, Timestamp deletedAt, String deletedBy, String introduction,
			boolean isActive, String logo, String name, String nature, int rank, int rowVersion, String[] sectors,
			String[] tags, Timestamp updatedAt, String updatedBy, String website, String file, Map cooperationStatusMap,
			List<FileBean> filelist) {
		super();
		this.id = id;
		this.achievements = achievements;
		this.contact = contact;
		this.cooperationStatus = cooperationStatus;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
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
		this.cooperationStatusMap = cooperationStatusMap;
		this.filelist = filelist;
	}
 

	public int getFileSize() {
		return filelist==null|| filelist.size()<=0 ? 0 : filelist.size();
	}


	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	public List<FileBean> getFilelist() {
		return filelist;
	}


	public void setFilelist(List<FileBean> filelist) {
		this.filelist = filelist;
	}


	public Map getCooperationStatusMap() {
		return cooperationStatusMap;
	}


	public void setCooperationStatusMap(Map cooperationStatusMap) {
		this.cooperationStatusMap = cooperationStatusMap;
	}




	public static long getSerialVersionUID() {
		return serialVersionUID;
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAchievements() {
		return achievements;
	}

	public void setAchievements(String achievements) {
		this.achievements = achievements;
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
		return "Organization{" +
				"id=" + id +
				", achievements='" + achievements + '\'' +
				", contact='" + contact + '\'' +
				", cooperationStatus=" + cooperationStatus +
				", createdAt=" + createdAt +
				", createdBy='" + createdBy + '\'' +
				", deleted=" + deleted +
				", deletedAt=" + deletedAt +
				", deletedBy='" + deletedBy + '\'' +
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
