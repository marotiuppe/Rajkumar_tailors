package com.olp.user_mgmt.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.olp.user_mgmt.role.entity.AppUserRoleMapping;

@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class AppUser implements java.io.Serializable {

	private Integer userId;
	private String userName;
	private String displayName;
	private String userPassword;
	private String apiKey;
	private String sessionId;
	private String emailId;
	private String mobileNo;
	private byte status;
	private String remarks;
	private String resetToken;
	private Date resetTokenExpiryTime;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	private Set<AppUserRoleMapping> appUserRoleMappings = new HashSet<AppUserRoleMapping>(0);

	public AppUser() {
	}

	public AppUser(String userName, String apiKey, byte status, byte isSuperuser, int createdBy, Date createdDate) {
		this.userName = userName;
		this.apiKey = apiKey;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppUser(String userName, String displayName, String userPassword, String apiKey, String saltedHashPwd,
			String sessionId, String emailId, String mobileNo, byte status, byte isSuperuser, String remarks,
			String resetToken, Date resetTokenExpiryTime, int createdBy, Date createdDate, Integer modifiedBy,
			Date modifiedDate,Set<AppUserRoleMapping> appUserRoleMappings) {
		this.userName = userName;
		this.displayName = displayName;
		this.userPassword = userPassword;
		this.apiKey = apiKey;
		this.sessionId = sessionId;
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.status = status;
		this.remarks = remarks;
		this.resetToken = resetToken;
		this.resetTokenExpiryTime = resetTokenExpiryTime;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.appUserRoleMappings = appUserRoleMappings;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", unique = true, nullable = false, length = 64)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "display_name", length = 64)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "user_password", length = 128)
	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "api_key", nullable = false, length = 128)
	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "session_id", length = 128)
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "email_id", length = 64)
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "mobile_no", length = 16)
	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Column(name = "remarks", length = 512)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "reset_token", length = 128)
	public String getResetToken() {
		return this.resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reset_token_expiry_time", length = 19)
	public Date getResetTokenExpiryTime() {
		return this.resetTokenExpiryTime;
	}

	public void setResetTokenExpiryTime(Date resetTokenExpiryTime) {
		this.resetTokenExpiryTime = resetTokenExpiryTime;
	}

	@Column(name = "created_by", nullable = false)
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
	public Set<AppUserRoleMapping> getAppUserRoleMappings() {
		return this.appUserRoleMappings;
	}

	public void setAppUserRoleMappings(Set<AppUserRoleMapping> appUserRoleMappings) {
		this.appUserRoleMappings = appUserRoleMappings;
	}

}

