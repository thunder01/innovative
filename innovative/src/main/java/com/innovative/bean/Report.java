package com.innovative.bean;

import java.io.Serializable;
/**
 * 报告，方案线索、寻源报告、会议记录、出差报告、问题记录、项目总结
 * @author huang
 *
 */

public class Report implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//生成的主键id
	private Integer approval_id;//立项表单的id
	private String demand_name;//需求名
	private String title;//标题
	private String content;//内容信息
	private String sector;//行业领域
	private String[] lable;//标签信息
	private String abstracts;//摘要信息
	private String type;//报告的类型，方案线索1、寻源报告2、会议记录3、出差报告4、问题记录5、项目总结6
    private String file;//文件存储路径
    private String create_date;//创建时间 
	private String create_by;//创建人
	private String late_date;//创建时间
	private String late_by;//最后修改者
	private Integer deleted;//删除的状态
	private String delete_date;//删除的状态的时间
	private String delete_by;//谁删除的
	
	private Integer order_id;
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Report(Integer id, Integer approval_id, String demand_name, String title, String content, String sector,
			String[] lable, String abstracts, String type, String file, String create_date, String create_by,
			String late_date, String late_by, Integer deleted, String delete_date, String delete_by) {
		super();
		this.id = id;
		this.approval_id = approval_id;
		this.demand_name = demand_name;
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
		this.deleted = deleted;
		this.delete_date = delete_date;
		this.delete_by = delete_by;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getApproval_id() {
		return approval_id;
	}
	public void setApproval_id(Integer approval_id) {
		this.approval_id = approval_id;
	}
	public String getDemand_name() {
		return demand_name;
	}
	public void setDemand_name(String demand_name) {
		this.demand_name = demand_name;
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
	public String[] getLable() {
		return lable;
	}
	public void setLable(String[] lable) {
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
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public String getDelete_by() {
		return delete_by;
	}
	public void setDelete_by(String delete_by) {
		this.delete_by = delete_by;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
}