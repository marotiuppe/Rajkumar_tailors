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

@Entity
@Table(name = "app_config_module")
public class AppConfigModule implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private int createdBy;
	private Date createdDate;
	private Set<AppProfileOption> appProfileOptions = new HashSet<AppProfileOption>(0);

	public AppConfigModule() {
	}

	public AppConfigModule(String code, String name, int createdBy, Date createdDate) {
		this.code = code;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppConfigModule(String code, String name, int createdBy, Date createdDate,
			Set<AppProfileOption> appProfileOptions) {
		this.code = code;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.appProfileOptions = appProfileOptions;
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

	@Column(name = "code", nullable = false, length = 64)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appConfigModule")
	public Set<AppProfileOption> getAppProfileOptions() {
		return this.appProfileOptions;
	}

	public void setAppProfileOptions(Set<AppProfileOption> appProfileOptions) {
		this.appProfileOptions = appProfileOptions;
	}

}

