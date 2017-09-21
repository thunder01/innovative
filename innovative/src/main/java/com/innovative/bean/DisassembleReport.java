package com.innovative.bean;

import java.io.Serializable;

/**
 * 拆解报告
 * @author fzy
 * @version 1.0
 * */
public class DisassembleReport implements Serializable{
	private static final long serialVersionUID = 8955741163013441331L;
	private Integer id;//
	private String title;//标题
	private String file;//拆解报告，文件路径	
    private String create_date;//创建时间 
	private String create_by;//创建人
	private String late_date;//创建时间
	private String late_by;//最后修改者
	private String delete_by;//删除人
	private String delete_date;//删除时间
	private Integer delete_status;//删除状态，0未删除，1是已删除
	private Integer order_id;//订单id
	private String message;
	private String status;
	private String status2;
	private String pass_by;
	
	public DisassembleReport() {
		super();
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

	public String getDelete_by() {
		return delete_by;
	}

	public void setDelete_by(String delete_by) {
		this.delete_by = delete_by;
	}

	public String getDelete_date() {
		return delete_date;
	}

	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}

	public Integer getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(Integer delete_status) {
		this.delete_status = delete_status;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getPass_by() {
		return pass_by;
	}

	public void setPass_by(String pass_by) {
		this.pass_by = pass_by;
	}

	@Override
	public String toString() {
		return "DisassembleReport [id=" + id + ", title=" + title + ", file=" + file + ", create_date=" + create_date
				+ ", create_by=" + create_by + ", late_date=" + late_date + ", late_by=" + late_by + ", delete_by="
				+ delete_by + ", delete_date=" + delete_date + ", delete_status=" + delete_status + ", order_id="
				+ order_id + "]";
	}
}
