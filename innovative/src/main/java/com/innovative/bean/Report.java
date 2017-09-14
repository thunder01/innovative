package com.innovative.bean;

import java.sql.Timestamp;

/**
 * 报告，方案线索、寻源报告、会议记录、出差报告、问题记录、项目总结
 * @author fzy
 * @version 1.0
 * */
public class Report {
	private String id;//生成的主键id
	private String title;//标题
	private String content;//内容信息
	private String sector;//行业领域
	private String lable;//标签信息
	private String abstracts;//摘要信息
	private String type;//报告的类型，方案线索、寻源报告、会议记录、出差报告、问题记录、项目总结
    private String file;//文件存储路径
    private Timestamp create_date;//创建时间 
	private String create_by;//创建人
	private Timestamp late_date;//创建时间
	private String late_by;//最后修改者
	
	public Report() {
		super();
	}

	public Report(String id, String title, String content, String sector, String lable, String abstracts,
			String type, String file, Timestamp create_date, String create_by, Timestamp late_date, String late_by) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.sector = sector;
		this.lable = lable;
		this.abstracts = abstracts;
		this.type = type;
		this.file = file;
		this.create_date = create_date;
		this.create_by = create_by;
		this.late_date = late_date;
		this.late_by = late_by;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Timestamp getLate_date() {
		return late_date;
	}

	public void setLate_date(Timestamp late_date) {
		this.late_date = late_date;
	}

	public String getLate_by() {
		return late_by;
	}

	public void setLate_by(String late_by) {
		this.late_by = late_by;
	}

	@Override
	public String toString() {
		return "XacxReport [id=" + id + ", title=" + title + ", content=" + content + ", sector=" + sector + ", lable="
				+ lable + ", abstracts=" + abstracts + ", type=" + type + ", file=" + file + ", create_date="
				+ create_date + ", create_by=" + create_by + ", late_date=" + late_date + ", late_by=" + late_by + "]";
	}
	
	
}
