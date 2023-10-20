package com.olp.user_mgmt.role.to;

import java.util.List;

public class AppRoleSaveTO {

	private Integer roleId;
	private String roleCode;
	private String roleDesc;
	private Byte statusId;
	private List<AppActionPrivilegeBean> privilegesLst;
	private List<AppActionPrivilegeBean> customPrivilegesLst;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Byte getStatusId() {
		return statusId;
	}

	public void setStatusId(Byte statusId) {
		this.statusId = statusId;
	}

	public List<AppActionPrivilegeBean> getPrivilegesLst() {
		return privilegesLst;
	}

	public void setPrivilegesLst(List<AppActionPrivilegeBean> privilegesLst) {
		this.privilegesLst = privilegesLst;
	}

	public List<AppActionPrivilegeBean> getCustomPrivilegesLst() {
		return customPrivilegesLst;
	}

	public void setCustomPrivilegesLst(List<AppActionPrivilegeBean> customPrivilegesLst) {
		this.customPrivilegesLst = customPrivilegesLst;
	}

}
