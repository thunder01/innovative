package com.innovative.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 积分模块
 * @author huang
 *
 */

public class Integral implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private Integer integral;
	private String[] label;
	private Integer login_count;
	private Integer times;
	private Integer type;
	private Integer count;
	
	public Integral() {}

	public Integral(String userid, Integer integral, String[] label, Integer login_count, Integer times, Integer type,
			Integer count) {
		super();
		this.userid = userid;
		this.integral = integral;
		this.label = label;
		this.login_count = login_count;
		this.times = times;
		this.type = type;
		this.count = count;
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

	public String[] getlabel() {
		return label;
	}

	public void setlabel(String[] label) {
		this.label = label;
	}

	public Integer getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Integer login_count) {
		this.login_count = login_count;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Integral [userid=" + userid + ", integral=" + integral + ", label=" + Arrays.toString(label)
				+ ", login_count=" + login_count + ", times=" + times + ", type=" + type + ", count=" + count + "]";
	}

	
	
}
