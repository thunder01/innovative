package com.innovative.bean;

import java.io.Serializable;

public class ModRight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4467205559888547461L;
	private String id;//权限id
	private String Pid;//父权限id
	private String RightName;//权限名字
	private String Description;//描述
	private String roleType;//类型  就是区分模块的
	
	public ModRight() {
	}
	
	public ModRight(String id, String pid, String rightName, String description, String roleType) {
		this.id = id;
		Pid = pid;
		RightName = rightName;
		Description = description;
		this.roleType = roleType;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return Pid;
	}
	public void setPid(String pid) {
		Pid = pid;
	}
	public String getRightName() {
		return RightName;
	}
	public void setRightName(String rightName) {
		RightName = rightName;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
}
