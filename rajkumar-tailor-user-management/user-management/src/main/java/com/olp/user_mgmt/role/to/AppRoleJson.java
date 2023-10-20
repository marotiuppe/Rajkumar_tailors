package com.olp.user_mgmt.role.to;

public class AppRoleJson {

	private Integer roleId;
	private String roleDesc;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public AppRoleJson() {
		super();
	}

	public AppRoleJson(Integer roleId, String roleDesc) {
		super();
		this.roleId = roleId;
		this.roleDesc = roleDesc;
	}

}
