package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 创新资源详情页进入次数
 * @author huang
 *
 */
public class IntegralResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id ;//主键
	private String type ;//类型，判断是哪个资源的
	private String resource_id;//资源id
	private int count;//暂时无用
	private String userid;//查看资源的用户id
	private Timestamp create_date;//查看时间
	
	public IntegralResource() {}

	public IntegralResource(Integer id, String type, String resource_id, int count, String userid,
			Timestamp create_date) {
		super();
		this.id = id;
		this.type = type;
		this.resource_id = resource_id;
		this.count = count;
		this.userid = userid;
		this.create_date = create_date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResource_id() {
		return resource_id;
	}

	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	@Override
	public String toString() {
		return "IntegralResource [id=" + id + ", type=" + type + ", resource_id=" + resource_id + ", count=" + count
				+ ", userid=" + userid + ", create_date=" + create_date + "]";
	}
	
}
