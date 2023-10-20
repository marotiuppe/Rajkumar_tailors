package com.olp.user_mgmt.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.olp.user_mgmt.common.to.ResponseStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserViewResponseTO {
	public ResponseStatus responseStatus;
	public int totalsize;
	public String recordsPerPage;
	public String currentPage;
	public String userName;
	public String displayName;
	public String roleId;
	public List<AppUserTO> appUserSearchDetail;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<AppUserTO> getAppUserSearchDetail() {
		return appUserSearchDetail;
	}
	public void setAppUserSearchDetail(List<AppUserTO> appUserSearchDetail) {
		this.appUserSearchDetail = appUserSearchDetail;
	}
	
	
}
