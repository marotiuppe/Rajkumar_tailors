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


@Entity
@Table(name = "app_action", uniqueConstraints = @UniqueConstraint(columnNames = "action_code"))
public class AppAction implements java.io.Serializable {

	private Integer actionId;
	private String actionCode;
	private String actionDesc;
	private String actionClass;
	private String helpFilePath;
	private byte status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	private Set<AppPrivilege> appPrivileges = new HashSet<AppPrivilege>(0);
	private Set<AppMenu> appMenus = new HashSet<AppMenu>(0);

	public AppAction() {
	}

	public AppAction(String actionCode, String actionDesc, byte status, int createdBy, Date createdDate) {
		this.actionCode = actionCode;
		this.actionDesc = actionDesc;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppAction(String actionCode, String actionDesc, String actionClass, String helpFilePath, byte status,
			int createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate, Set<AppPrivilege> appPrivileges,
			Set<AppMenu> appMenus) {
		this.actionCode = actionCode;
		this.actionDesc = actionDesc;
		this.actionClass = actionClass;
		this.helpFilePath = helpFilePath;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.appPrivileges = appPrivileges;
		this.appMenus = appMenus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "action_id", unique = true, nullable = false)
	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@Column(name = "action_code", unique = true, nullable = false, length = 64)
	public String getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Column(name = "action_desc", nullable = false, length = 64)
	public String getActionDesc() {
		return this.actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	@Column(name = "action_class", length = 256)
	public String getActionClass() {
		return this.actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	@Column(name = "help_file_path", length = 512)
	public String getHelpFilePath() {
		return this.helpFilePath;
	}

	public void setHelpFilePath(String helpFilePath) {
		this.helpFilePath = helpFilePath;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appAction")
	public Set<AppPrivilege> getAppPrivileges() {
		return this.appPrivileges;
	}

	public void setAppPrivileges(Set<AppPrivilege> appPrivileges) {
		this.appPrivileges = appPrivileges;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appAction")
	public Set<AppMenu> getAppMenus() {
		return this.appMenus;
	}

	public void setAppMenus(Set<AppMenu> appMenus) {
		this.appMenus = appMenus;
	}

}

