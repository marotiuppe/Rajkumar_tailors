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

import com.olp.user_mgmt.entity.AppUser;


@Entity
@Table(name = "app_user_role_mapping")
public class AppUserRoleMapping implements java.io.Serializable {

	private Integer userRoleMappingId;
	private AppRole appRole;
	private AppUser appUser;
	private byte status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	public AppUserRoleMapping() {
	}

	public AppUserRoleMapping(AppRole appRole, AppUser appUser, byte status, int createdBy, Date createdDate) {
		this.appRole = appRole;
		this.appUser = appUser;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppUserRoleMapping(AppRole appRole, AppUser appUser, byte status, int createdBy, Date createdDate,
			Integer modifiedBy, Date modifiedDate) {
		this.appRole = appRole;
		this.appUser = appUser;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_role_mapping_id", unique = true, nullable = false)
	public Integer getUserRoleMappingId() {
		return this.userRoleMappingId;
	}

	public void setUserRoleMappingId(Integer userRoleMappingId) {
		this.userRoleMappingId = userRoleMappingId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
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

