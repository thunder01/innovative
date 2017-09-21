package com.innovative.bean;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 416755758759740194L;
	private String roleId ;//角色id
	private String roleName ;//角色名字
	private String p_RoleId ;//父角色
	private String description ;//角色描述
	private String createAt ;//创建时间
	private String createBy;//创建人
	private String updateAt;//更新时间
	private String updateBy;//更新人
	private List<String> rightIds;//权限id 方便增加角色权限时用
	
	public Role() {
	}
	
	public Role(String roleId, String roleName, String p_RoleId, String description, String createAt, String createBy,
			String updateAt, String updateBy, List<String> rightIds) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.p_RoleId = p_RoleId;
		this.description = description;
		this.createAt = createAt;
		this.createBy = createBy;
		this.updateAt = updateAt;
		this.updateBy = updateBy;
		this.rightIds = rightIds;
	}

	
	public List<String> getRightIds() {
		return rightIds;
	}

	public void setRightId(List<String> rightIds) {
		this.rightIds = rightIds;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getP_RoleId() {
		return p_RoleId;
	}
	public void setP_RoleId(String p_RoleId) {
		this.p_RoleId = p_RoleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
	
	
}
