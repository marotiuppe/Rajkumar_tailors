package com.olp.user_mgmt.role.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.olp.user_mgmt.entity.AppPrivilege;


@Entity
@Table(name = "app_role_privilege_mapping", uniqueConstraints = @UniqueConstraint(columnNames = {
		"role_id", "privilege_id" }))
public class AppRolePrivilegeMapping implements java.io.Serializable {

	private Integer rolePrivilegeMappingId;
	private AppPrivilege appPrivilege;
	private AppRole appRole;
	private byte status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	public AppRolePrivilegeMapping() {
	}

	public AppRolePrivilegeMapping(AppPrivilege appPrivilege, AppRole appRole, byte status, int createdBy,
			Date createdDate) {
		this.appPrivilege = appPrivilege;
		this.appRole = appRole;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppRolePrivilegeMapping(AppPrivilege appPrivilege, AppRole appRole, byte status, int createdBy,
			Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.appPrivilege = appPrivilege;
		this.appRole = appRole;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "role_privilege_mapping_id", unique = true, nullable = false)
	public Integer getRolePrivilegeMappingId() {
		return this.rolePrivilegeMappingId;
	}

	public void setRolePrivilegeMappingId(Integer rolePrivilegeMappingId) {
		this.rolePrivilegeMappingId = rolePrivilegeMappingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "privilege_id", nullable = false)
	public AppPrivilege getAppPrivilege() {
		return this.appPrivilege;
	}

	public void setAppPrivilege(AppPrivilege appPrivilege) {
		this.appPrivilege = appPrivilege;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
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

}

