package com.olp.user_mgmt.role.to;

import java.util.ArrayList;

import com.olp.user_mgmt.common.to.ResponseStatus;

public class AppRoleJsonRoot {

	public ResponseStatus responseStatus;
	public ArrayList<AppRoleJson> appRoleLst;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public ArrayList<AppRoleJson> getAppRoleLst() {
		return appRoleLst;
	}

	public void setAppRoleLst(ArrayList<AppRoleJson> appRoleLst) {
		this.appRoleLst = appRoleLst;
	}

	public AppRoleJsonRoot() {
		super();
	}

	public AppRoleJsonRoot(ResponseStatus responseStatus, ArrayList<AppRoleJson> appRoleLst) {
		super();
		this.responseStatus = responseStatus;
		this.appRoleLst = appRoleLst;
	}

}
