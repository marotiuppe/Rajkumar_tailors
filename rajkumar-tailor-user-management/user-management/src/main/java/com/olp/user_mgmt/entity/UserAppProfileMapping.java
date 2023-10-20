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
@Table(name = "user_app_profile_mapping", uniqueConstraints = @UniqueConstraint(columnNames = {
		"user_id", "app_profile_option_id" }))
public class UserAppProfileMapping implements java.io.Serializable {

	private Integer userAppProfileMappingId;
	private int appProfileOptionId;
	private long userId;
	private String appProfileOptionValue;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	public UserAppProfileMapping() {
	}

	public UserAppProfileMapping(int appProfileOptionId, long userId, String appProfileOptionValue,
			int createdBy, Date createdDate) {
		this.appProfileOptionId = appProfileOptionId;
		this.userId = userId;
		this.appProfileOptionValue = appProfileOptionValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public UserAppProfileMapping(int appProfileOptionId, long userId, String appProfileOptionValue,
			int createdBy, Date createdDate, Integer modifiedBy, Date modifiedDate) {
		this.appProfileOptionId = appProfileOptionId;
		this.userId = userId;
		this.appProfileOptionValue = appProfileOptionValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "user_app_profile_mapping_id", unique = true, nullable = false)
	public Integer getUserAppProfileMappingId() {
		return this.userAppProfileMappingId;
	}

	public void setUserAppProfileMappingId(Integer userAppProfileMappingId) {
		this.userAppProfileMappingId = userAppProfileMappingId;
	}

	@Column(name = "app_profile_option_id", nullable = false)
	public int getAppProfileOptionId() {
		return this.appProfileOptionId;
	}

	public void setAppProfileOptionId(int appProfileOptionId) {
		this.appProfileOptionId = appProfileOptionId;
	}

	@Column(name = "user_id", nullable = false)
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "app_profile_option_value", nullable = false, length = 500)
	public String getAppProfileOptionValue() {
		return this.appProfileOptionValue;
	}

	public void setAppProfileOptionValue(String appProfileOptionValue) {
		this.appProfileOptionValue = appProfileOptionValue;
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

