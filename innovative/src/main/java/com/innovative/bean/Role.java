package com.innovative.bean;

import java.io.Serializable;

public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 416755758759740194L;
	private String roleId ;//角色id
	private String RoleName ;//角色名字
	private String P_RoleId ;//父角色
	private String Description ;//角色描述
	private String CreateAt ;//创建时间
	private String CreateBy;//创建人
	private String UpdateAt;//更新时间
	private String UpdateBy;//更新人
	
	public Role(String roleId, String roleName, String p_RoleId, String description, String createAt, String createBy,
			String updateAt, String updateBy) {
		this.roleId = roleId;
		RoleName = roleName;
		P_RoleId = p_RoleId;
		Description = description;
		CreateAt = createAt;
		CreateBy = createBy;
		UpdateAt = updateAt;
		UpdateBy = updateBy;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getP_RoleId() {
		return P_RoleId;
	}
	public void setP_RoleId(String p_RoleId) {
		P_RoleId = p_RoleId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCreateAt() {
		return CreateAt;
	}
	public void setCreateAt(String createAt) {
		CreateAt = createAt;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public String getUpdateAt() {
		return UpdateAt;
	}
	public void setUpdateAt(String updateAt) {
		UpdateAt = updateAt;
	}
	public String getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}
	
	
}
