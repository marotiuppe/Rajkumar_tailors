package com.olp.user_mgmt.role.entity;

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
@Table(name = "app_role", uniqueConstraints = @UniqueConstraint(columnNames = "role_code"))
public class AppRole implements java.io.Serializable {

	private Integer roleId;
	private String roleCode;
	private String roleDesc;
	private Byte status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;
	private Set<AppUserRoleMapping> appUserRoleMappings = new HashSet<AppUserRoleMapping>(0);
	private Set<AppRolePrivilegeMapping> appRolePrivilegeMappings = new HashSet<AppRolePrivilegeMapping>(0);

	public AppRole() {
	}

	public AppRole(String roleCode, Byte status, int createdBy, Date createdDate) {
		this.roleCode = roleCode;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppRole(String roleCode, String roleDesc, Byte status, int createdBy, Date createdDate, Integer modifiedBy,
			Date modifiedDate, Set<AppUserRoleMapping> appUserRoleMappings,
			Set<AppRolePrivilegeMapping> appRolePrivilegeMappings) {
		this.roleCode = roleCode;
		this.roleDesc = roleDesc;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.appUserRoleMappings = appUserRoleMappings;
		this.appRolePrivilegeMappings = appRolePrivilegeMappings;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "role_id", unique = true, nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_code", unique = true, nullable = false, length = 16)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "role_desc", length = 64)
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "status", nullable = false)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appRole")
	public Set<AppUserRoleMapping> getAppUserRoleMappings() {
		return this.appUserRoleMappings;
	}

	public void setAppUserRoleMappings(Set<AppUserRoleMapping> appUserRoleMappings) {
		this.appUserRoleMappings = appUserRoleMappings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appRole")
	public Set<AppRolePrivilegeMapping> getAppRolePrivilegeMappings() {
		return this.appRolePrivilegeMappings;
	}

	public void setAppRolePrivilegeMappings(Set<AppRolePrivilegeMapping> appRolePrivilegeMappings) {
		this.appRolePrivilegeMappings = appRolePrivilegeMappings;
	}

}
