package com.olp.user_mgmt.role.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include. NON_NULL)
public class AppActionPrivilegeBean {

	private Integer actionId;
	private String actionCode;
	private String actionDesc;
	private Integer viewPrivilege;
	private Integer addPrivilege;
	private Integer editPrivilege;
	private Integer deletePrivilege;
	private Integer otherPrivilege;
	private String privilegedesc;
	private Integer selectViewPrivilege;
	private Integer selectAddPrivilege;
	private Integer selectEditPrivilege;
	private Integer selectDeletePrivilege;
	private Integer selectOtherPrivilege;

	public Integer getOtherPrivilege() {
		return otherPrivilege;
	}

	public String getPrivilegedesc() {
		return privilegedesc;
	}

	public void setPrivilegedesc(String privilegedesc) {
		this.privilegedesc = privilegedesc;
	}

	public void setOtherPrivilege(Integer otherPrivilege) {
		this.otherPrivilege = otherPrivilege;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public Integer getViewPrivilege() {
		return viewPrivilege;
	}

	public void setViewPrivilege(Integer viewPrivilege) {
		this.viewPrivilege = viewPrivilege;
	}

	public Integer getAddPrivilege() {
		return addPrivilege;
	}

	public void setAddPrivilege(Integer addPrivilege) {
		this.addPrivilege = addPrivilege;
	}

	public Integer getEditPrivilege() {
		return editPrivilege;
	}

	public void setEditPrivilege(Integer editPrivilege) {
		this.editPrivilege = editPrivilege;
	}

	public Integer getDeletePrivilege() {
		return deletePrivilege;
	}

	public void setDeletePrivilege(Integer deletePrivilege) {
		this.deletePrivilege = deletePrivilege;
	}

	public Integer getSelectViewPrivilege() {
		return selectViewPrivilege;
	}

	public void setSelectViewPrivilege(Integer selectViewPrivilege) {
		this.selectViewPrivilege = selectViewPrivilege;
	}

	public Integer getSelectAddPrivilege() {
		return selectAddPrivilege;
	}

	public void setSelectAddPrivilege(Integer selectAddPrivilege) {
		this.selectAddPrivilege = selectAddPrivilege;
	}

	public Integer getSelectEditPrivilege() {
		return selectEditPrivilege;
	}

	public void setSelectEditPrivilege(Integer selectEditPrivilege) {
		this.selectEditPrivilege = selectEditPrivilege;
	}

	public Integer getSelectDeletePrivilege() {
		return selectDeletePrivilege;
	}

	public void setSelectDeletePrivilege(Integer selectDeletePrivilege) {
		this.selectDeletePrivilege = selectDeletePrivilege;
	}

	public Integer getSelectOtherPrivilege() {
		return selectOtherPrivilege;
	}

	public void setSelectOtherPrivilege(Integer selectOtherPrivilege) {
		this.selectOtherPrivilege = selectOtherPrivilege;
	}

}
