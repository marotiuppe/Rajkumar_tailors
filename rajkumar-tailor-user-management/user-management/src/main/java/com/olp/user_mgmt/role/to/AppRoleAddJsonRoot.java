package com.olp.user_mgmt.role.to;

import java.util.List;

import com.olp.user_mgmt.common.to.ResponseStatus;


public class AppRoleAddJsonRoot {

	private ResponseStatus responseStatus;
	private List<AppActionPrivilegeBean> privilegesLst;
	private List<AppActionPrivilegeBean> customPrivilegesLst;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
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
