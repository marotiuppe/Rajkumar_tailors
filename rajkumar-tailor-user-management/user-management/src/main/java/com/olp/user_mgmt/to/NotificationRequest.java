package com.olp.user_mgmt.to;

import com.olp.user_mgmt.entity.AppUser;

public class NotificationRequest {

	private AppUserTO appuser;
	private String emailToAddress;
	private String emailCCAddress;
	private String message;
	private String resetToken;
	private String contextPath;
	public AppUserTO getAppuser() {
		return appuser;
	}
	public void setAppuser(AppUserTO appuser) {
		this.appuser = appuser;
	}
	public String getEmailToAddress() {
		return emailToAddress;
	}
	public void setEmailToAddress(String emailToAddress) {
		this.emailToAddress = emailToAddress;
	}
	public String getEmailCCAddress() {
		return emailCCAddress;
	}
	public void setEmailCCAddress(String emailCCAddress) {
		this.emailCCAddress = emailCCAddress;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	

}
