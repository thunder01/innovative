package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 拆解报告
 * @author fzy
 * @version 1.0
 * */
public class DisassembleReport implements Serializable{
	private Integer id;//
	private String title;//标题
	private String file;//拆解报告，文件路径
	private Timestamp create_date;//创建时间 
	private String create_by;//创建人
	private Timestamp late_date;//创建时间
	private String late_by;//最后修改者
	
	public DisassembleReport() {
		super();
	}

	public DisassembleReport(Integer id, String title, String file, Timestamp create_date, String create_by,
			Timestamp late_date, String late_by) {
		super();
		this.id = id;
		this.title = title;
		this.file = file;
		this.create_date = create_date;
		this.create_by = create_by;
		this.late_date = late_date;
		this.late_by = late_by;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		return "DisassembleReport [id=" + id + ", title=" + title + ", file=" + file + ", create_date=" + create_date
				+ ", create_by=" + create_by + ", late_date=" + late_date + ", late_by=" + late_by + "]";
	}
	
	
}
