package com.olp.user_mgmt.entity;

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

@Entity
@Table(name = "app_menu", uniqueConstraints = @UniqueConstraint(columnNames = "menu_code"))
public class AppMenu implements java.io.Serializable {

	private Integer menuId;
	private AppAction appAction;
	private String menuCode;
	private String menuDesc;
	private String image;
	private byte status;
	private byte hasSubMenu;
	private Integer parentMenuId;
	private Byte displayOrder;
	private int createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date modifiedDate;

	public AppMenu() {
	}

	public AppMenu(String menuCode, String menuDesc, byte status, byte hasSubMenu, int createdBy, Date createdDate) {
		this.menuCode = menuCode;
		this.menuDesc = menuDesc;
		this.status = status;
		this.hasSubMenu = hasSubMenu;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public AppMenu(AppAction appAction, String menuCode, String menuDesc, String image, byte status, byte hasSubMenu,
			Integer parentMenuId, Byte displayOrder, int createdBy, Date createdDate, Integer modifiedBy,
			Date modifiedDate) {
		this.appAction = appAction;
		this.menuCode = menuCode;
		this.menuDesc = menuDesc;
		this.image = image;
		this.status = status;
		this.hasSubMenu = hasSubMenu;
		this.parentMenuId = parentMenuId;
		this.displayOrder = displayOrder;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "menu_id", unique = true, nullable = false)
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	
	  @ManyToOne(fetch = FetchType.LAZY)
	  
	  @JoinColumn(name = "action_id") public AppAction getAppAction() { return
	  this.appAction; }
	  
	  public void setAppAction(AppAction appAction) { this.appAction = appAction; }
	 

	@Column(name = "menu_code", unique = true, nullable = false, length = 64)
	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Column(name = "menu_desc", nullable = false, length = 64)
	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	@Column(name = "image", length = 64)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Column(name = "has_sub_menu", nullable = false)
	public byte getHasSubMenu() {
		return this.hasSubMenu;
	}

	public void setHasSubMenu(byte hasSubMenu) {
		this.hasSubMenu = hasSubMenu;
	}

	@Column(name = "parent_menu_id")
	public Integer getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Column(name = "display_order")
	public Byte getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Byte displayOrder) {
		this.displayOrder = displayOrder;
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

