package com.olp.user_mgmt.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "app_user_login_failed_attempts", uniqueConstraints = @UniqueConstraint(columnNames = "app_user_id"))
public class AppUserLoginFailedAttempts implements java.io.Serializable {

	private Integer id;
	private int appUserId;
	private Integer failedAttempts;
	private String activeSessionId;
	private Date modifiedDate;
	private Date lastLoginTime;

	public AppUserLoginFailedAttempts() {
	}

	public AppUserLoginFailedAttempts(int appUserId) {
		this.appUserId = appUserId;
	}

	public AppUserLoginFailedAttempts(int appUserId, Integer failedAttempts, String activeSessionId, Date modifiedDate,
			Date lastLoginTime) {
		this.appUserId = appUserId;
		this.failedAttempts = failedAttempts;
		this.activeSessionId = activeSessionId;
		this.modifiedDate = modifiedDate;
		this.lastLoginTime = lastLoginTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "app_user_id", unique = true, nullable = false)
	public int getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(int appUserId) {
		this.appUserId = appUserId;
	}

	@Column(name = "failed_attempts")
	public Integer getFailedAttempts() {
		return this.failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	@Column(name = "active_session_id", length = 256)
	public String getActiveSessionId() {
		return this.activeSessionId;
	}

	public void setActiveSessionId(String activeSessionId) {
		this.activeSessionId = activeSessionId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time", length = 19)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}

