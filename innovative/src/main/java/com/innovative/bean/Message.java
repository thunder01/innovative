package com.innovative.bean;

import java.io.Serializable;

public class Message implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int  id; //主键
    private int  projectId;//对应的项目ID
    private String type;//类型
    private String createDate;//创建时间
    private String status;//状态(0.未审核,1.已审核，2.不通过)
    private Integer notice;//1通知2已办3待办
    private String last_by;//最后修改时间
    private String last_date;//最后修改人
    private String userid;//发信息给这个用户
    private int project_id;//对应的项目ID
    private String create_date;//创建时间
    private String proid;//项目id
    
    private Object object;
    
	public Message() {}

	public Message(int id, int projectId, String type, String createDate, String status, Integer notice, String last_by,
			String last_date, String userid, int project_id, String create_date) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.type = type;
		this.createDate = createDate;
		this.status = status;
		this.notice = notice;
		this.last_by = last_by;
		this.last_date = last_date;
		this.userid = userid;
		this.project_id = project_id;
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNotice() {
		return notice;
	}

	public void setNotice(Integer notice) {
		this.notice = notice;
	}

	public String getLast_by() {
		return last_by;
	}

	public void setLast_by(String last_by) {
		this.last_by = last_by;
	}

	public String getLast_date() {
		return last_date;
	}

	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getProid() {
		return proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", projectId=" + projectId + ", type=" + type + ", createDate=" + createDate
				+ ", status=" + status + ", notice=" + notice + ", last_by=" + last_by + ", last_date=" + last_date
				+ ", userid=" + userid + ", project_id=" + project_id + ", create_date=" + create_date + ", proid="
				+ proid + ", object=" + object + "]";
	}
}
