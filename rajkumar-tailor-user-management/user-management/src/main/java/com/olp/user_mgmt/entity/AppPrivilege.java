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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.olp.user_mgmt.role.entity.AppRolePrivilegeMapping;


@Entity
@Table(name = "app_privilege", uniqueConstraints = @UniqueConstraint(columnNames = "privilege_desc"))
public class AppPrivilege implements java.io.Serializable {

	private Integer privilegeId;
	private AppAction appAction;
	private String privilegeCode;
	private String privilegeDesc;
	private byte status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	private Set<AppRolePrivilegeMapping> appRolePrivilegeMappings = new HashSet<AppRolePrivilegeMapping>(0);

	public AppPrivilege() {
	}

	public AppPrivilege(AppAction appAction, String privilegeCode, String privilegeDesc, byte status, int createdBy,
			Date createdDate) {
		this.appAction = appAction;
		this.privilegeCode = privilegeCode;
		this.privilegeDesc = privilegeDesc;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppPrivilege(AppAction appAction, String privilegeCode, String privilegeDesc, byte status, int createdBy,
			Date createdDate, Integer modifiedBy, Date modifiedDate,
			Set<AppRolePrivilegeMapping> appRolePrivilegeMappings) {
		this.appAction = appAction;
		this.privilegeCode = privilegeCode;
		this.privilegeDesc = privilegeDesc;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.appRolePrivilegeMappings = appRolePrivilegeMappings;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "privilege_id", unique = true, nullable = false)
	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", nullable = false)
	public AppAction getAppAction() {
		return this.appAction;
	}

	public void setAppAction(AppAction appAction) {
		this.appAction = appAction;
	}

	@Column(name = "privilege_code", nullable = false, length = 32)
	public String getPrivilegeCode() {
		return this.privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	@Column(name = "privilege_desc", unique = true, nullable = false, length = 64)
	public String getPrivilegeDesc() {
		return this.privilegeDesc;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appPrivilege")
	public Set<AppRolePrivilegeMapping> getAppRolePrivilegeMappings() {
		return this.appRolePrivilegeMappings;
	}

	public void setAppRolePrivilegeMappings(Set<AppRolePrivilegeMapping> appRolePrivilegeMappings) {
		this.appRolePrivilegeMappings = appRolePrivilegeMappings;
	}

}

