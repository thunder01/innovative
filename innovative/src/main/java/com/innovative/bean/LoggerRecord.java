package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;
  
public class LoggerRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userid;
	private String method;
	private String url;
	private String ip;
	private String args;
	private String class_method;
	private String status;
	private Timestamp currend_time;
	
	public LoggerRecord() {
		super();
	}
	public LoggerRecord(Integer id, String userid, String method, String url, String ip, String args,
			String class_method, String status, Timestamp currend_time) {
		super();
		this.id = id;
		this.userid = userid;
		this.method = method;
		this.url = url;
		this.ip = ip;
		this.args = args;
		this.class_method = class_method;
		this.status = status;
		this.currend_time = currend_time;
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getClass_method() {
		return class_method;
	}
	public void setClass_method(String class_method) {
		this.class_method = class_method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCurrent_time() {
		return currend_time;
	}
	public void setCurrent_time(Timestamp currend_time) {
		this.currend_time = currend_time;
	}
	@Override
	public String toString() {
		return "LoggerRecord [id=" + id + ", userid=" + userid + ", method=" + method + ", url=" + url + ", ip=" + ip
				+ ", args=" + args + ", class_method=" + class_method + ", status=" + status + ", currend_time="
				+ currend_time + "]";
	}
	
}
