package com.olp.user_mgmt.role.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.common.CommonUtils;
import com.olp.user_mgmt.common.to.FailureResponseTo;
import com.olp.user_mgmt.common.to.ResponseStatus;
import com.olp.user_mgmt.common.to.SaveResponseTO;
import com.olp.user_mgmt.entity.AppUser;
import com.olp.user_mgmt.exception.ServiceException;
import com.olp.user_mgmt.role.entity.AppRole;
import com.olp.user_mgmt.role.entity.AppRolePrivilegeMapping;
import com.olp.user_mgmt.role.service.AppRoleServiceImpl;
import com.olp.user_mgmt.role.to.AppActionPrivilegeBean;
import com.olp.user_mgmt.role.to.AppRoleAddJsonRoot;
import com.olp.user_mgmt.role.to.AppRoleEditResponseTO;
import com.olp.user_mgmt.role.to.AppRoleJson;
import com.olp.user_mgmt.role.to.AppRoleJsonRoot;
import com.olp.user_mgmt.role.to.AppRoleSaveTO;
import com.olp.user_mgmt.role.to.AppRoleTO;
import com.olp.user_mgmt.role.to.AppRoleViewResponseTO;
import com.olp.user_mgmt.service.AppUserServiceImpl;

@RestController

@RequestMapping("/approle")
public class AppRoleRestController {

	private static final Logger lOGGER = LoggerFactory.getLogger(AppRoleRestController.class);

	@Autowired
	AppRoleServiceImpl appRoleServiceImpl;

	@Autowired
	AppUserServiceImpl appUserServiceImpl;

	@Autowired
	private Environment env;

	private List<AppActionPrivilegeBean> appActionPrivilegeBeanLst;
	private List<AppActionPrivilegeBean> appActionOtherPrivilegeLst;

	@GetMapping("/view")
	@ResponseBody
	public AppRoleViewResponseTO viewAllAppUser(@RequestHeader(required = false) String roleCode,
			@RequestHeader(required = false) String roleDesc, @RequestHeader(required = false) String statusId,
			@RequestHeader(required = false) String recordsPerPage,
			@RequestHeader(required = false) String currentPage) {
		lOGGER.info(CommonConstants.BEGIN);
		lOGGER.info("HEADERS::" + "::roleCode::" + roleCode + "::roleDesc::" + roleDesc + "::statusId::" + statusId
				+ "::recordsPerPage::" + recordsPerPage + "::currentPage::" + currentPage);
		String regex = "[0-9]+";
		AppRoleViewResponseTO viewResponse = new AppRoleViewResponseTO();
		List<AppRoleTO> appRoleList = null;
		try {
			if (recordsPerPage == null || recordsPerPage == "") {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("RECORDSPERPAGE_MAND_CD")));
				response.setStatusMessage(env.getProperty("RECORDSPERPAGE_MAND"));
				viewResponse.setResponseStatus(response);
				return viewResponse;
			} else if (currentPage == null || currentPage == "") {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("CURRENTPAGE_MAND_CD")));
				response.setStatusMessage(env.getProperty("CURRENTPAGE_MAND"));
				viewResponse.setResponseStatus(response);
				return viewResponse;
			}
			if (!recordsPerPage.matches(regex)) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("RECORDSPERPAGE_NUM_CD")));
				response.setStatusMessage(env.getProperty("RECORDSPERPAGE_NUM"));
				viewResponse.setResponseStatus(response);
				return viewResponse;
			}
			if (!currentPage.matches(regex)) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("CURRENTPAGE_NUM_CD")));
				response.setStatusMessage(env.getProperty("CURRENTPAGE_NUM"));
				viewResponse.setResponseStatus(response);
				return viewResponse;
			}

			int currentPageId = Integer.parseInt(currentPage);
			int recordsPerPageId = Integer.parseInt(recordsPerPage);

			statusId = StringUtils.isBlank(statusId) ? "" : statusId;
			int statusId1 = -1;
			Byte stat = null;
			try {
				if (statusId != "") {
					statusId1 = Integer.parseInt(statusId);
					byte b = (byte) statusId1;
					stat = b;
				} else {
					stat = -1;
				}
			} catch (NumberFormatException e) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("STATUS_ID_NFE_CD")));
				response.setStatusMessage(env.getProperty("STATUS_ID_NFE_MSG"));
				viewResponse.setResponseStatus(response);
//				return viewResponse;
			}

			if (StringUtils.isNotBlank(roleCode)) {
				roleCode = roleCode.trim().concat("%");
			} else {
				roleCode = "";
			}
			if (StringUtils.isNotBlank(roleDesc)) {
				roleDesc = roleDesc.trim().concat("%");
			} else {
				roleDesc = "";
			}

			Integer userId = CommonUtils.getWebUser().getUserid();
			appRoleList = appRoleServiceImpl.fetchAppRolesByCodeDescStatus(userId, roleCode, roleDesc, stat,
					currentPageId, recordsPerPageId);

			Integer count = appRoleServiceImpl.fetchAppRolesByCodeDescStatusCount(userId, roleCode, roleDesc, stat);

			if (!appRoleList.isEmpty()) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				viewResponse.setResponseStatus(response);
				viewResponse.setTotalsize(count);
				viewResponse.setRecordsPerPage(recordsPerPage);
				viewResponse.setCurrentPage(currentPage);
				viewResponse.setRoleCode(roleCode);
				viewResponse.setRoleDesc(roleDesc);
				viewResponse.setStatusId(statusId1);
				viewResponse.setAppRoleSearchDetail(appRoleList);
			} else {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_NOT_AVAIL_CD")));
				response.setStatusMessage(env.getProperty("APPROLE_NOT_AVAIL_MSG"));
				viewResponse.setResponseStatus(response);
				viewResponse.setTotalsize(count);
				viewResponse.setRecordsPerPage(recordsPerPage);
				viewResponse.setCurrentPage(currentPage);
				viewResponse.setRoleCode(roleCode);
				viewResponse.setRoleDesc(roleDesc);
				viewResponse.setStatusId(statusId1);
				viewResponse.setAppRoleSearchDetail(appRoleList);
			}

		} catch (ServiceException e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus response = new ResponseStatus();
			response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			viewResponse.setAppRoleSearchDetail(null);
			viewResponse.setResponseStatus(response);
			return viewResponse;
		}
		lOGGER.info(CommonConstants.END);
		return viewResponse;
	}

	@GetMapping("/edit")
	public Object editAppRolePrivileges(@RequestHeader(required = false) String roleId) {
		lOGGER.info(CommonConstants.BEGIN);
		lOGGER.info("HEADERS::" + "::roleId::" + roleId);
		Integer roleId1 = 0;
		AppRoleEditResponseTO viewResponse = new AppRoleEditResponseTO();
		List<Integer> existingPrivilegeList = new ArrayList<Integer>();
		try {
			if (StringUtils.isBlank(roleId)) {
				ResponseStatus response = new ResponseStatus();
				FailureResponseTo failureResponse = new FailureResponseTo();
				response.setStatusCode(Integer.parseInt(env.getProperty("APROLE_ROLEID_EDIT_ID")));
				response.setStatusMessage(env.getProperty("APROLE_ROLEID_EDIT_MSG"));
				failureResponse.setResponseStatus(response);
				return failureResponse;
			} else {
				try {
					roleId1 = Integer.parseInt(roleId);
				} catch (NumberFormatException e) {
					ResponseStatus response = new ResponseStatus();
					FailureResponseTo failureResponse = new FailureResponseTo();
					response.setStatusCode(Integer.parseInt(env.getProperty("APROLE_ROLEID_NFE_EDIT_ID")));
					response.setStatusMessage(env.getProperty("APROLE_ROLEID_NFE_EDIT_MSG"));
					failureResponse.setResponseStatus(response);
					return failureResponse;
				}
			}
			AppRole appRole = appRoleServiceImpl.getAppRoleById(roleId1);
			if (appRole == null) {
				ResponseStatus response = new ResponseStatus();
				FailureResponseTo failureResponse = new FailureResponseTo();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_NOT_AVAIL_CD")));
				response.setStatusMessage(env.getProperty("APPROLE_NOT_AVAIL_MSG"));
				failureResponse.setResponseStatus(response);
				return failureResponse;
			}
			if (appRole != null && !CommonUtils.isNull(appRole.getRoleId())) {
				List<AppRolePrivilegeMapping> mappingList = appRoleServiceImpl
						.getAllAppRolePrivilegeMappingByRoleId(roleId1);
				getRolePrivileges();
				if (mappingList != null) {
					if (appActionPrivilegeBeanLst != null) {
						for (AppActionPrivilegeBean bean : appActionPrivilegeBeanLst) {
							for (AppRolePrivilegeMapping mapping : mappingList) {
								Integer privilege = mapping.getAppPrivilege().getPrivilegeId();
								if (bean.getViewPrivilege() != null && bean.getViewPrivilege().equals(privilege)) {
									bean.setSelectViewPrivilege(privilege);
									existingPrivilegeList.add(privilege);
								} else if (bean.getAddPrivilege() != null && bean.getAddPrivilege().equals(privilege)) {
									bean.setSelectAddPrivilege(privilege);
									existingPrivilegeList.add(privilege);
								} else if (bean.getEditPrivilege() != null
										&& bean.getEditPrivilege().equals(privilege)) {
									bean.setSelectEditPrivilege(privilege);
									existingPrivilegeList.add(privilege);
								} else if (bean.getDeletePrivilege() != null
										&& bean.getDeletePrivilege().equals(privilege)) {
									bean.setSelectDeletePrivilege(privilege);
									existingPrivilegeList.add(privilege);
								}

							}
						}
					}

					if (appActionOtherPrivilegeLst != null) {
						for (AppActionPrivilegeBean bean : appActionOtherPrivilegeLst) {
							for (AppRolePrivilegeMapping mapping : mappingList) {
								Integer privilege = mapping.getAppPrivilege().getPrivilegeId();
								if (bean.getOtherPrivilege() != null && bean.getOtherPrivilege().equals(privilege)) {
									bean.setSelectOtherPrivilege(privilege);
									existingPrivilegeList.add(privilege);
								}
							}
						}
					}

				}

				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				viewResponse.setResponseStatus(response);
				viewResponse.setRoleId(appRole.getRoleId());
				viewResponse.setRoleCode(appRole.getRoleCode());
				viewResponse.setRoleDesc(appRole.getRoleDesc());
				viewResponse.setStatusId(appRole.getStatus());
				viewResponse.setPrivilegesLst(appActionPrivilegeBeanLst);
				viewResponse.setCustomPrivilegesLst(appActionOtherPrivilegeLst);
				// viewResponse.setExistingPrivilegeList(existingPrivilegeList);
			} else {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("NO_DATA_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("NO_DATA_STATUS_MESSAGE"));
				viewResponse.setRoleCode(null);
				viewResponse.setRoleDesc(null);
				viewResponse.setStatusId((byte) -1);
				viewResponse.setPrivilegesLst(null);
				viewResponse.setCustomPrivilegesLst(null);
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus response = new ResponseStatus();
			response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			viewResponse.setResponseStatus(response);
		}
		lOGGER.info(CommonConstants.END);
		return viewResponse;
	}

	public void getRolePrivileges() {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Integer userId = CommonUtils.getWebUser().getUserid();
			AppUser user = appUserServiceImpl.getAppUserById(userId);
			if (user != null) {
				appActionPrivilegeBeanLst = appRoleServiceImpl.getAllAppActionPrivilegeBeanByUserId(userId);
				appActionOtherPrivilegeLst = appRoleServiceImpl.getAllAppActionOtherPrivilegesByUserId(userId);
				if (appActionPrivilegeBeanLst == null) {
					appActionPrivilegeBeanLst = new ArrayList<AppActionPrivilegeBean>();
				}
				if (appActionOtherPrivilegeLst == null) {
					appActionOtherPrivilegeLst = new ArrayList<AppActionPrivilegeBean>();
				}
				for (int i = appActionPrivilegeBeanLst.size() - 1; i >= 0; i--) {
					if (appActionPrivilegeBeanLst.get(i).getViewPrivilege() == null) {
						appActionPrivilegeBeanLst.remove(i);
					}
				}
//				if (user.getIsSuperuser() == 1) {
//					appActionPrivilegeBeanLst = appRoleServiceImpl.getAllAppActionPrivilegeBean();
//					appActionOtherPrivilegeLst = appRoleServiceImpl.getAllAppActionOtherPrivileges();
//					if (appActionPrivilegeBeanLst == null) {
//						appActionPrivilegeBeanLst = new ArrayList<AppActionPrivilegeBean>();
//					}
//					if (appActionOtherPrivilegeLst == null) {
//						appActionOtherPrivilegeLst = new ArrayList<AppActionPrivilegeBean>();
//					}
//				} else {
//					appActionPrivilegeBeanLst = appRoleServiceImpl.getAllAppActionPrivilegeBeanByUserId(userId);
//					appActionOtherPrivilegeLst = appRoleServiceImpl.getAllAppActionOtherPrivilegesByUserId(userId);
//					if (appActionPrivilegeBeanLst == null) {
//						appActionPrivilegeBeanLst = new ArrayList<AppActionPrivilegeBean>();
//					}
//					if (appActionOtherPrivilegeLst == null) {
//						appActionOtherPrivilegeLst = new ArrayList<AppActionPrivilegeBean>();
//					}
//					for (int i = appActionPrivilegeBeanLst.size() - 1; i >= 0; i--) {
//						if (appActionPrivilegeBeanLst.get(i).getViewPrivilege() == null) {
//							appActionPrivilegeBeanLst.remove(i);
//						}
//					}
//				}
			}
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);

	}

	@PostMapping("/save")
	public SaveResponseTO saveorUpdateAppRolePrivileges(@RequestBody(required = false) AppRoleSaveTO appRoleSaveTO) {
		lOGGER.info(CommonConstants.BEGIN);
		CommonUtils.logIncomingJsonData(appRoleSaveTO);
		SaveResponseTO viewResponse = new SaveResponseTO();
		try {
			ResponseStatus resposne = screenLevelMandVaidations(appRoleSaveTO);
			if (resposne.getStatusCode() != 0 && resposne.getStatusMessage() != null) {
				viewResponse.setResponseStatus(resposne);
				return viewResponse;
			}
			if (appRoleSaveTO.getRoleId() == null) {
				ResponseStatus saveAppRolePrivileges = saveAppRolePrivileges(appRoleSaveTO);
				viewResponse.setResponseStatus(saveAppRolePrivileges);
				return viewResponse;
			} else {
				ResponseStatus updateAppRolePrivileges = updateAppRolePrivileges(appRoleSaveTO);
				viewResponse.setResponseStatus(updateAppRolePrivileges);
				return viewResponse;
			}

		} catch (Exception e) {
			ResponseStatus response = new ResponseStatus();
			response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			viewResponse.setResponseStatus(response);
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return viewResponse;

	}

	private ResponseStatus screenLevelMandVaidations(AppRoleSaveTO saveObj) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		ResponseStatus response = new ResponseStatus();
		try {
			if (saveObj.getRoleId() == null) {
				AppRole localAppRole = appRoleServiceImpl.getAppRoleByCode(saveObj.getRoleCode());
				if (!CommonUtils.isNull(localAppRole)) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_SAVE_ROLECD_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_SAVE_ROLECD_MSG"));
				}
				if (CommonUtils.isNull(saveObj)) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_REQUEST_OBJ_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_REQUEST_OBJ_MSG"));
				}
				if (CommonUtils.isNull(saveObj.getRoleCode())) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_ROLECD_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_ROLECD_MSG"));
				}
				if (CommonUtils.isNull(saveObj.getRoleDesc())) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_ROLEDESC_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_ROLEDESC_MSG"));
				}
			} else {
				if (CommonUtils.isNull(saveObj)) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_REQUEST_OBJ_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_REQUEST_OBJ_MSG"));
				}
				if (saveObj.getStatusId() == null) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_ROLE_STATUS_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_ROLE_STATUS_MSG"));
				}
				if (CommonUtils.isNull(saveObj.getRoleDesc())) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_ROLEDESC_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_ROLEDESC_MSG"));
				}
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return response;
	}

	public ResponseStatus saveAppRolePrivileges(AppRoleSaveTO appRoleSaveTO) throws ServiceException {
		Integer loginUserId = CommonUtils.getWebUser().getUserid();
		ResponseStatus response = new ResponseStatus();
		try {
			AppRole appRole = new AppRole();
			appRole.setStatus((byte) 1);
			appRole.setCreatedBy(loginUserId);
			appRole.setCreatedDate(new Date());
			appRole.setRoleCode(appRoleSaveTO.getRoleCode());
			appRole.setRoleDesc(appRoleSaveTO.getRoleDesc());

			List<AppActionPrivilegeBean> privilegesLst = appRoleSaveTO.getPrivilegesLst();
			List<AppActionPrivilegeBean> customPrivilegesLst = appRoleSaveTO.getCustomPrivilegesLst();
			List<Integer> appRolePrivilegeSelList = new ArrayList<>();

			getRolePrivileges();
			if (!privilegesLst.isEmpty()) {
				for (AppActionPrivilegeBean selPri : privilegesLst) {
					if (appActionPrivilegeBeanLst != null && appActionPrivilegeBeanLst.size() > 0) {
						for (AppActionPrivilegeBean privilege : appActionPrivilegeBeanLst) {

							if (privilege.getViewPrivilege() != null) {
								if (privilege.getViewPrivilege().equals(selPri.getSelectViewPrivilege())) {
									appRolePrivilegeSelList.add(selPri.getSelectViewPrivilege());
								}
							}

							if (privilege.getAddPrivilege() != null) {
								if (privilege.getAddPrivilege().equals(selPri.getSelectAddPrivilege())) {
									appRolePrivilegeSelList.add(selPri.getSelectAddPrivilege());
								}
							}

							if (privilege.getEditPrivilege() != null) {
								if (privilege.getEditPrivilege().equals(selPri.getSelectEditPrivilege())) {
									appRolePrivilegeSelList.add(selPri.getSelectEditPrivilege());
								}
							}

							if (privilege.getDeletePrivilege() != null) {
								if (privilege.getDeletePrivilege().equals(selPri.getSelectDeletePrivilege())) {
									appRolePrivilegeSelList.add(selPri.getSelectDeletePrivilege());
								}
							}
						}
					}
				}
			}
			if (!customPrivilegesLst.isEmpty()) {
				for (AppActionPrivilegeBean cusPri : customPrivilegesLst) {
					if (appActionOtherPrivilegeLst != null && appActionOtherPrivilegeLst.size() > 0) {
						for (AppActionPrivilegeBean privilege : appActionOtherPrivilegeLst) {

							if (privilege.getOtherPrivilege() != null) {
								if (privilege.getOtherPrivilege().equals(cusPri.getSelectOtherPrivilege())) {
									appRolePrivilegeSelList.add(cusPri.getSelectOtherPrivilege());
								}
							}

						}
					}
				}
			}

			appRoleServiceImpl.saveAppRoleAndPrivilages(appRole, appRolePrivilegeSelList, loginUserId);
			response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_INSERTED_CD")));
			response.setStatusMessage(env.getProperty("APPROLE_INSERTED_MSG"));
			lOGGER.info("::ROLE INSERTED SUCCESSFULLY::");

		} catch (ServiceException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return response;

	}

	public ResponseStatus updateAppRolePrivileges(AppRoleSaveTO appRoleSaveTO) throws ServiceException {
		Integer loginUserId = CommonUtils.getWebUser().getUserid();
		ResponseStatus response = new ResponseStatus();
		try {
			if (appRoleSaveTO.getRoleId() > 0) {
				AppRole existAppRole = appRoleServiceImpl.getAppRoleById(appRoleSaveTO.getRoleId());
				if (existAppRole == null) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_NOT_AVAIL_CD")));
					response.setStatusMessage(env.getProperty("APPROLE_NOT_AVAIL_MSG"));
					return response;
				}
				Integer rolePrivilegeStatus = null;
				if (appRoleSaveTO != null) {
					existAppRole.setRoleDesc(appRoleSaveTO.getRoleDesc());
				}
				if (!existAppRole.getStatus().equals(appRoleSaveTO.getStatusId())) {
					rolePrivilegeStatus = appRoleSaveTO.getStatusId().intValue();
				}
				existAppRole.setStatus(existAppRole.getStatus());
				existAppRole.setModifiedBy(loginUserId);
				existAppRole.setModifiedDate(new Date());

				List<AppActionPrivilegeBean> privilegesLst = appRoleSaveTO.getPrivilegesLst();
				List<AppActionPrivilegeBean> customPrivilegesLst = appRoleSaveTO.getCustomPrivilegesLst();
				getRolePrivileges();

				/*
				 * List<Integer> appRolePrivilegeDelList = new ArrayList<>();
				 * for(AppActionPrivilegeBean ab : privilegesLst) { if(ab.getViewPrivilege() !=
				 * null && !ab.getViewPrivilege().equals(ab.getSelectViewPrivilege())) {
				 * appRolePrivilegeDelList.add(ab.getViewPrivilege()); } if(ab.getAddPrivilege()
				 * != null && !ab.getAddPrivilege().equals(ab.getSelectAddPrivilege())) {
				 * appRolePrivilegeDelList.add(ab.getAddPrivilege()); } if(ab.getEditPrivilege()
				 * != null && !ab.getEditPrivilege().equals(ab.getSelectEditPrivilege())) {
				 * appRolePrivilegeDelList.add(ab.getEditPrivilege()); }
				 * if(ab.getDeletePrivilege() != null &&
				 * !ab.getDeletePrivilege().equals(ab.getSelectAddPrivilege())) {
				 * appRolePrivilegeDelList.add(ab.getDeletePrivilege()); }
				 * 
				 * }
				 * 
				 * 
				 * for(AppActionPrivilegeBean ab : customPrivilegesLst) {
				 * if(ab.getOtherPrivilege() != null &&
				 * !ab.getOtherPrivilege().equals(ab.getSelectOtherPrivilege())) {
				 * appRolePrivilegeDelList.add(ab.getOtherPrivilege()); }
				 * 
				 * }
				 */

				List<Integer> appRolePrivilegeSelList = new ArrayList<>();
				if (privilegesLst != null && !privilegesLst.isEmpty()) {
					for (AppActionPrivilegeBean selPri : privilegesLst) {
						if (appActionPrivilegeBeanLst != null && !appActionPrivilegeBeanLst.isEmpty()) {
							for (AppActionPrivilegeBean privilege : appActionPrivilegeBeanLst) {

								if (privilege.getViewPrivilege() != null) {
									if (privilege.getViewPrivilege().equals(selPri.getSelectViewPrivilege())) {
										appRolePrivilegeSelList.add(selPri.getSelectViewPrivilege());
									}
								}

								if (privilege.getAddPrivilege() != null) {
									if (privilege.getAddPrivilege().equals(selPri.getSelectAddPrivilege())) {
										appRolePrivilegeSelList.add(selPri.getSelectAddPrivilege());
									}
								}

								if (privilege.getEditPrivilege() != null) {
									if (privilege.getEditPrivilege().equals(selPri.getSelectEditPrivilege())) {
										appRolePrivilegeSelList.add(selPri.getSelectEditPrivilege());
									}
								}

								if (privilege.getDeletePrivilege() != null) {
									if (privilege.getDeletePrivilege().equals(selPri.getSelectDeletePrivilege())) {
										appRolePrivilegeSelList.add(selPri.getSelectDeletePrivilege());
									}
								}
							}
						}
					}
				}
				if (customPrivilegesLst != null && !customPrivilegesLst.isEmpty()) {
					for (AppActionPrivilegeBean cusPri : customPrivilegesLst) {
						if (appActionOtherPrivilegeLst != null && !appActionOtherPrivilegeLst.isEmpty()) {
							for (AppActionPrivilegeBean privilege : appActionOtherPrivilegeLst) {
								if (privilege.getOtherPrivilege() != null) {
									if (privilege.getOtherPrivilege().equals(cusPri.getSelectOtherPrivilege())) {
										appRolePrivilegeSelList.add(cusPri.getSelectOtherPrivilege());
									}
								}

							}
						}
					}
				}
				appRoleServiceImpl.updateAppRoleAndPrivilages(existAppRole, appRolePrivilegeSelList, loginUserId,
						rolePrivilegeStatus);
				response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_UPDATED_CD")));
				response.setStatusMessage(env.getProperty("APPROLE_UPDATED_MSG"));
				lOGGER.info("::ROLE UPDATED SUCCESSFULLY::");
			}

		} catch (ServiceException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		} catch (DataIntegrityViolationException e) {
			String message = e.getMostSpecificCause().getMessage();
			if (message.contains("un_app_role_code")) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPROLE_SAVE_ROLECD_CD")));
				response.setStatusMessage(env.getProperty("APPROLE_SAVE_ROLECD_MSG"));
			} else {
				response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
				lOGGER.error("::Message::" + message);

			}
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return response;
	}

	// Application user screen
	@GetMapping("/activeAppRoles")
	public AppRoleJsonRoot getActiveAppRoles() {
		lOGGER.info(CommonConstants.BEGIN);
		ArrayList<AppRoleJson> ajsonList = new ArrayList<>();
		ResponseStatus responseStatus = new ResponseStatus();
		AppRoleJsonRoot jsonRoot = null;
		AppRoleJson ajson = null;
		try {
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
			responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
			Integer userid = CommonUtils.getWebUser().getUserid();
			String username = CommonUtils.getWebUser().getUsername();
			List<AppRole> roles = appRoleServiceImpl.getActiveAppRoles(userid, username);
			for (AppRole role : roles) {
				ajson = new AppRoleJson();
				ajson.setRoleId(role.getRoleId());
				ajson.setRoleDesc(role.getRoleDesc());
				ajsonList.add(ajson);
			}
			jsonRoot = new AppRoleJsonRoot(responseStatus, ajsonList);
			if (roles.isEmpty()) {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("NO_DATA_STATUS_CODE")));
				responseStatus.setStatusMessage(env.getProperty("NO_DATA_STATUS_MESSAGE"));
				jsonRoot = new AppRoleJsonRoot(responseStatus, ajsonList);
			}
		} catch (Exception e) {
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			jsonRoot = new AppRoleJsonRoot(responseStatus, ajsonList);
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return jsonRoot;
	}
	
	@GetMapping("/rolePrivileges")
	public AppRoleAddJsonRoot getAppRolePrivileges() {
		lOGGER.info(CommonConstants.BEGIN);
		AppRoleAddJsonRoot apJsonRoot = new AppRoleAddJsonRoot();
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			getRolePrivileges();
			if ((appActionPrivilegeBeanLst != null && !appActionPrivilegeBeanLst.isEmpty()) || (appActionOtherPrivilegeLst!= null && !appActionOtherPrivilegeLst.isEmpty())) {
				apJsonRoot.setPrivilegesLst(appActionPrivilegeBeanLst);
				apJsonRoot.setCustomPrivilegesLst(appActionOtherPrivilegeLst);
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
				responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				apJsonRoot.setResponseStatus(responseStatus);
			} else {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("NO_DATA_STATUS_CODE")));
				responseStatus.setStatusMessage(env.getProperty("NO_DATA_STATUS_MESSAGE"));
				apJsonRoot.setResponseStatus(responseStatus);
			}
			
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			apJsonRoot.setResponseStatus(responseStatus);
		}
		lOGGER.info(CommonConstants.END);
		return apJsonRoot;

	}

}
