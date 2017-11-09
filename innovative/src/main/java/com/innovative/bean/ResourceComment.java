package com.innovative.bean;

import java.sql.Timestamp;

/**
 * 创新资源评论
 * @author huang
 *
 */
public class ResourceComment {
	
	private Integer id;//创新资源评论主键
	private Integer type;//类型1专家、2合作机构、3行业协会、4技术报告、5方案、6一起设备
	private String resource_id;//资源id
	private String comment;//评论内容
	private String comment_date;//评论时间
	private String comment_by;//评论人的id
	private String comment_username;//评论人的姓名
	private Integer status;//是否打赏，默认为0未打赏。1是已打赏
	private String enjoy_date;//打赏的时间
	private String enjoy_by;//打赏人的id
	private String enjoy_username;//打赏人的姓名
	private Integer deleted;
	private String delete_by;
	private String delete_date;
	
	private Object object;
	
	public ResourceComment() {
		super();
	}

	public ResourceComment(Integer id, Integer type, String resource_id, String comment, String comment_date,
			String comment_by, String comment_username, Integer status, String enjoy_date, String enjoy_by,
			String enjoy_username, Integer deleted, String delete_by, String delete_date, Object object) {
		super();
		this.id = id;
		this.type = type;
		this.resource_id = resource_id;
		this.comment = comment;
		this.comment_date = comment_date;
		this.comment_by = comment_by;
		this.comment_username = comment_username;
		this.status = status;
		this.enjoy_date = enjoy_date;
		this.enjoy_by = enjoy_by;
		this.enjoy_username = enjoy_username;
		this.deleted = deleted;
		this.delete_by = delete_by;
		this.delete_date = delete_date;
		this.object = object;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment_date() {
		if(comment_date==null){
			return comment_date;
		}
		return comment_date.substring(0, 16);
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public String getComment_by() {
		return comment_by;
	}

	public void setComment_by(String comment_by) {
		this.comment_by = comment_by;
	}

	public String getComment_username() {
		return comment_username;
	}

	public void setComment_username(String comment_username) {
		this.comment_username = comment_username;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEnjoy_date() {
		return enjoy_date;
	}

	public void setEnjoy_date(String enjoy_date) {
		this.enjoy_date = enjoy_date;
	}

	public String getEnjoy_by() {
		return enjoy_by;
	}

	public void setEnjoy_by(String enjoy_by) {
		this.enjoy_by = enjoy_by;
	}

	public String getEnjoy_username() {
		return enjoy_username;
	}

	public void setEnjoy_username(String enjoy_username) {
		this.enjoy_username = enjoy_username;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ResourceComment [id=" + id + ", type=" + type + ", resource_id=" + resource_id + ", comment=" + comment
				+ ", comment_date=" + comment_date + ", comment_by=" + comment_by + ", comment_username="
				+ comment_username + ", status=" + status + ", enjoy_date=" + enjoy_date + ", enjoy_by=" + enjoy_by
				+ ", enjoy_username=" + enjoy_username + ", deleted=" + deleted + ", delete_by=" + delete_by
				+ ", delete_date=" + delete_date + ", object=" + object + "]";
	}
	
}
