package com.olp.user_mgmt.role.to;

import java.util.List;

import com.olp.user_mgmt.common.to.ResponseStatus;

public class AppRoleEditResponseTO {

	private ResponseStatus responseStatus;
	private Integer roleId;
	private String roleCode;
	private String roleDesc;
	private byte statusId;
	private List<AppActionPrivilegeBean> privilegesLst;
	private List<AppActionPrivilegeBean> customPrivilegesLst;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

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

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
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
