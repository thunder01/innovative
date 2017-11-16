package com.innovative.bean;

import java.io.Serializable;


/**
 * userId 用户id
 * userName 用户姓名
 * userIntegration 用户积分 （这个参数为以后的用户积分做准备）
 * userPost 用户职位
 * userSex 性别 
 * userAge 年龄
 *  * jobNumber 工号
 */
import java.sql.Timestamp;

public class Right implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4148405984845574540L;
	private String id ;//用户id
	private String pid ;//姓名
	private String rightName ;//积分
	private String description ;//用户职位
	private String rightType ;//创建时间
	private String createBy ;//性别
	private Timestamp createAt ;//年龄
	private String mode ;//模块
	private String depth ;//模块
	public Right() {
			
		}
	public Right(String id, String pid, String rightName, String description, String rightType, String createBy,
			Timestamp createAt, String mode, String depth) {
		this.id = id;
		this.pid = pid;
		this.rightName = rightName;
		this.description = description;
		this.rightType = rightType;
		this.createBy = createBy;
		this.createAt = createAt;
		this.mode = mode;
		this.depth = depth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	

	

	
	
	
}
