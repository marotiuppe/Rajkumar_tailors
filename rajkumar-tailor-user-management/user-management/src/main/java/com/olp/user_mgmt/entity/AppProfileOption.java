package com.olp.user_mgmt.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "app_profile_option", uniqueConstraints = @UniqueConstraint(columnNames = "option_name"))
public class AppProfileOption implements java.io.Serializable {

	private Integer optionId;
	private AppConfigModule appConfigModule;
	private Integer moduleId;
	private String optionName;
	private String optionDesc;
	private String optionDataType;
	private Character allowUserOverride;
	private String currentValue;
	private String defaultValue;
	private Boolean applyToDevices;
	private int status;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	public AppProfileOption() {
	}

	public AppProfileOption(String optionName, String currentValue, String defaultValue, int status, int createdBy,
			Date createdDate) {
		this.optionName = optionName;
		this.currentValue = currentValue;
		this.defaultValue = defaultValue;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppProfileOption(AppConfigModule appConfigModule, Integer moduleId, String optionName, String optionDesc,
			String optionDataType, Character allowUserOverride, String currentValue, String defaultValue,
			Boolean applyToDevices, int status, int createdBy, Date createdDate, Integer modifiedBy,
			Date modifiedDate) {
		this.appConfigModule = appConfigModule;
		this.moduleId = moduleId;
		this.optionName = optionName;
		this.optionDesc = optionDesc;
		this.optionDataType = optionDataType;
		this.allowUserOverride = allowUserOverride;
		this.currentValue = currentValue;
		this.defaultValue = defaultValue;
		this.applyToDevices = applyToDevices;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "option_id", unique = true, nullable = false)
	public Integer getOptionId() {
		return this.optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_config_module_id")
	public AppConfigModule getAppConfigModule() {
		return this.appConfigModule;
	}

	public void setAppConfigModule(AppConfigModule appConfigModule) {
		this.appConfigModule = appConfigModule;
	}

	@Column(name = "module_id")
	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "option_name", unique = true, nullable = false, length = 100)
	public String getOptionName() {
		return this.optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	@Column(name = "option_desc", length = 100)
	public String getOptionDesc() {
		return this.optionDesc;
	}

	public void setOptionDesc(String optionDesc) {
		this.optionDesc = optionDesc;
	}

	@Column(name = "option_data_type", length = 10)
	public String getOptionDataType() {
		return this.optionDataType;
	}

	public void setOptionDataType(String optionDataType) {
		this.optionDataType = optionDataType;
	}

	@Column(name = "allow_user_override", length = 1)
	public Character getAllowUserOverride() {
		return this.allowUserOverride;
	}

	public void setAllowUserOverride(Character allowUserOverride) {
		this.allowUserOverride = allowUserOverride;
	}

	@Column(name = "current_value", nullable = false, length = 500)
	public String getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	@Column(name = "default_value", nullable = false, length = 500)
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Column(name = "apply_to_devices")
	public Boolean getApplyToDevices() {
		return this.applyToDevices;
	}

	public void setApplyToDevices(Boolean applyToDevices) {
		this.applyToDevices = applyToDevices;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
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

