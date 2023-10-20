package com.olp.user_mgmt.to;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AppUserSaveTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String userName;
	private String displayName;
	private String userPassword;
	private String emailId;
	private String mobileNo;
	private byte statusId;
	private String remarks;
	private Integer partnerId;
	private Set<Integer> roles = new HashSet<>(0);

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Set<Integer> getRoles() {
		return roles;
	}

	public void setRoles(Set<Integer> roles) {
		this.roles = roles;
	}

}
