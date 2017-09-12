package com.innovative.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */
public class FileBean  {

	/**
	 * 
	 */
	private String refId;
	private String refType;
	private String url;
	private String createAt;
	private String createBy;
	private String sign;
	private String deleteBy;
	
	public FileBean() {
			
	}
	
	public FileBean(String refId, String refType, String url, String createAt, String createBy, String sign,
			String deleteBy) {
		this.refId = refId;
		this.refType = refType;
		this.url = url;
		this.createAt = createAt;
		this.createBy = createBy;
		this.sign = sign;
		this.deleteBy = deleteBy;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	
	
   
  
}
