package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//专家表
public class Expert implements Serializable{

	//private static final long serialVersionUID = -5311404303222383742L;
	private String id;//主键
	private String avatar;//头像
	private String contact;//联系方式
	private int cooperationStatus;//合作状态
	private String cooperName;//合作状态
	private Map cooperationStatusMap;
	private String title;//合作状态
	private Timestamp createdAt; //创建时间
	private String createdBy; //创建来源
	private boolean deleted;  //是否删除
	private Timestamp deletedAt; //删除时间
	private String deletedBy;  //删除来源
	private String education;//最高学历
	private int hFactor;//H因子
	private boolean isActive;//是否在线
	private String jobTitle; //职称
	private String name;  //专家名称
	private int rank;   //等级
	private String researchAchievement; //研发成果
	private String researchDirection;  //研发方向
	private String resume;  //个人简历
	private int rowVersion;  //版本
	private String[] sectors;  //部门，领域
	/*@ColumnType(typeHandler = com.innovative.mybatisHandler.ArrayTypeHandler.class)*/
	private String[] tags;   //标签
	private String unit;   //所在单位
	private Timestamp updatedAt; //修改时间
	private String updatedBy;   //修改来源
	private String file;  //文件
	
	List<FileBean> filelist ;
	private int fileSize;//
	
	public Expert(){
		
		
	}
	
	public Expert(String id, String avatar, String contact, int cooperationStatus, String cooperName,
			Map cooperationStatusMap, String title, Timestamp createdAt, String createdBy, boolean deleted,
			Timestamp deletedAt, String deletedBy, String education, int hFactor, boolean isActive, String jobTitle,
			String name, int rank, String researchAchievement, String researchDirection, String resume, int rowVersion,
			String[] sectors, String[] tags, String unit, Timestamp updatedAt, String updatedBy, String file,
			List<FileBean> filelist) {
		this.id = id;
		this.avatar = avatar;
		this.contact = contact;
		this.cooperationStatus = cooperationStatus;
		this.cooperName = cooperName;
		this.cooperationStatusMap = cooperationStatusMap;
		this.title = title;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
		this.deletedBy = deletedBy;
		this.education = education;
		this.hFactor = hFactor;
		this.isActive = isActive;
		this.jobTitle = jobTitle;
		this.name = name;
		this.rank = rank;
		this.researchAchievement = researchAchievement;
		this.researchDirection = researchDirection;
		this.resume = resume;
		this.rowVersion = rowVersion;
		this.sectors = sectors;
		this.tags = tags;
		this.unit = unit;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.file = file;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map getCooperationStatusMap() {
		return cooperationStatusMap;
	}
	public void setCooperationStatusMap(Map cooperationStatusMap) {
		this.cooperationStatusMap = cooperationStatusMap;
	}
	public String getCooperName() {
		return cooperName;
	}
	public void setCooperName(String cooperName) {
		this.cooperName = cooperName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public int gethFactor() {
		return hFactor;
	}
	public void sethFactor(int hFactor) {
		this.hFactor = hFactor;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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
	public String getResearchAchievement() {
		return researchAchievement;
	}
	public void setResearchAchievement(String researchAchievement) {
		this.researchAchievement = researchAchievement;
	}
	public String getResearchDirection() {
		return researchDirection;
	}
	public void setResearchDirection(String researchDirection) {
		this.researchDirection = researchDirection;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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

}
