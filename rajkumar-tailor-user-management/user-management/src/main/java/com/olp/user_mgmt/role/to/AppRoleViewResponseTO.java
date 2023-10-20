package com.olp.user_mgmt.role.to;

import java.util.List;

import com.olp.user_mgmt.common.to.ResponseStatus;

public class AppRoleViewResponseTO {

	public ResponseStatus responseStatus;
	public int totalsize;
	public String recordsPerPage;
	public String currentPage;
	public String roleCode;
	public String roleDesc;
	public int statusId;
	public List<AppRoleTO> appRoleSearchDetail;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public String getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(String recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public List<AppRoleTO> getAppRoleSearchDetail() {
		return appRoleSearchDetail;
	}

	public void setAppRoleSearchDetail(List<AppRoleTO> appRoleSearchDetail) {
		this.appRoleSearchDetail = appRoleSearchDetail;
	}

}
