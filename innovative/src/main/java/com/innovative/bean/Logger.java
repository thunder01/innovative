package com.innovative.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */
public class Logger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8725184669544134872L;
	/**
	 * 
	 */
	private String id;
	private String clientip;
	private String uri;
	private String type;
	private String method;
	private String param;
	private String sessionid;
	private Timestamp time;
	private Timestamp returntime;
	private Timestamp returndata;
	private String httpstatuscode;
	private String timeconsuming;
	public Logger() {
			
	}
	
	public Logger(String id, String clientip, String uri, String type, String method, String param, String sessionid,
			Timestamp time, Timestamp returntime, Timestamp returndata, String httpstatuscode, String timeconsuming) {
		this.id = id;
		this.clientip = clientip;
		this.uri = uri;
		this.type = type;
		this.method = method;
		this.param = param;
		this.sessionid = sessionid;
		this.time = time;
		this.returntime = returntime;
		this.returndata = returndata;
		this.httpstatuscode = httpstatuscode;
		this.timeconsuming = timeconsuming;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Timestamp getReturntime() {
		return returntime;
	}
	public void setReturntime(Timestamp returntime) {
		this.returntime = returntime;
	}
	public Timestamp getReturndata() {
		return returndata;
	}
	public void setReturndata(Timestamp returndata) {
		this.returndata = returndata;
	}
	public String getHttpstatuscode() {
		return httpstatuscode;
	}
	public void setHttpstatuscode(String httpstatuscode) {
		this.httpstatuscode = httpstatuscode;
	}
	public String getTimeconsuming() {
		return timeconsuming;
	}
	public void setTimeconsuming(String timeconsuming) {
		this.timeconsuming = timeconsuming;
	}
	
	
	
   
  
}
