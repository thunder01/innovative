package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 积分模块
 * 
 * @author huang
 *
 */

public class Integral implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userid;// 用户id
	private Integer integral;// 积分
	private Integer type;//积分类型
	private Timestamp create_date;//创建时间
	private String content;//积分内容
	private String resource_id;//触发积分的动作id

	public Integral() {}

	public Integral(Integer id, String userid, Integer integral, Integer type, Timestamp create_date, String content,
			String resource_id) {
		super();
		this.id = id;
		this.userid = userid;
		this.integral = integral;
		this.type = type;
		this.create_date = create_date;
		this.content = content;
		this.resource_id = resource_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	@Override
	public String toString() {
		return "Integral [id=" + id + ", userid=" + userid + ", integral=" + integral + ", type=" + type
				+ ", create_date=" + create_date + ", content=" + content + ", resource_id=" + resource_id + "]";
	}

	
}
