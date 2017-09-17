package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 拆解报告
 * @author fzy
 * @version 1.0
 * */
public class DisassembleReport implements Serializable{
	private Integer id;//
	private String title;//标题
	private String file;//拆解报告，文件路径
	/*private String content;//内容信息
	private String sector;//行业领域
	private String[] lable;//标签信息
	private String abstracts;//摘要信息	
*/	private String create_date;//创建时间 
	private String create_by;//创建人
	private String late_date;//创建时间
	private String late_by;//最后修改者
	private Integer orderid;
	
	public DisassembleReport() {
		super();
	}

	public DisassembleReport(Integer id, String title, String file, String create_date, String create_by,
			String late_date, String late_by, Integer orderid) {
		super();
		this.id = id;
		this.title = title;
		this.file = file;
		this.create_date = create_date;
		this.create_by = create_by;
		this.late_date = late_date;
		this.late_by = late_by;
		this.orderid = orderid;
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

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getLate_date() {
		return late_date;
	}

	public void setLate_date(String late_date) {
		this.late_date = late_date;
	}

	public String getLate_by() {
		return late_by;
	}

	public void setLate_by(String late_by) {
		this.late_by = late_by;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Override
	public String toString() {
		return "DisassembleReport [id=" + id + ", title=" + title + ", file=" + file + ", create_date=" + create_date
				+ ", create_by=" + create_by + ", late_date=" + late_date + ", late_by=" + late_by + ", orderid="
				+ orderid + "]";
	}

	
}
