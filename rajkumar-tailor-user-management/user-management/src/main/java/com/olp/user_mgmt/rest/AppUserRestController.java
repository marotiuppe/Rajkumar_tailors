package com.olp.user_mgmt.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.olp.user_mgmt.common.to.CommonAPIsJson;
import com.olp.user_mgmt.common.to.ResponseStatus;
import com.olp.user_mgmt.common.to.SaveResponseTO;
import com.olp.user_mgmt.entity.AppUser;
import com.olp.user_mgmt.service.AppUserServiceImpl;
import com.olp.user_mgmt.to.AppUserSaveTO;
import com.olp.user_mgmt.to.AppUserTO;
import com.olp.user_mgmt.to.AppUserViewResponseTO;

@RestController
@RequestMapping("/appuser")
public class AppUserRestController {

	private static final Logger lOGGER = LoggerFactory.getLogger(AppUserRestController.class);

	@Autowired
	AppUserServiceImpl appUserServiceImpl;

	@Autowired
	private Environment env;

	@GetMapping("/view")
	@ResponseBody
	public AppUserViewResponseTO viewAllAppUser(@RequestHeader(required = false) String userName,
			@RequestHeader(required = false) String displayName, @RequestHeader(required = false) String roleId,
			@RequestHeader(required = false) String currentPage, @RequestHeader(required = false) String recordsPerPage) {
		lOGGER.info(CommonConstants.BEGIN);
		String regex = "[0-9]+";
		AppUserViewResponseTO viewResponse = new AppUserViewResponseTO();
		try {

			if (recordsPerPage == null || recordsPerPage == "") {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("RECORDSPERPAGE_MAND_CD")));
				response.setStatusMessage(env.getProperty("RECORDSPERPAGE_MAND"));
				viewResponse.setResponseStatus(response);
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

			userName = StringUtils.isBlank(userName) ? "" : userName;
			displayName = StringUtils.isBlank(displayName) ? "" : displayName;
			roleId = StringUtils.isBlank(roleId) ? "" : roleId;

			int roleId1 = -1;
			try {
				if (roleId != "") {
					roleId1 = Integer.parseInt(roleId);
				}
			} catch (NumberFormatException e) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_ROLE_NFE_ID")));
				response.setStatusMessage(env.getProperty("APPUSER_ROLE_NFE_MSG"));
				viewResponse.setResponseStatus(response);
				return viewResponse;
			}

			//Integer userid = CommonUtils.getWebUser().getUserid();

			List<AppUserTO> appUserSearchDetail = appUserServiceImpl.getAppUserDetailList(userName, displayName,
					roleId1, currentPageId, recordsPerPageId);

			Integer count = appUserServiceImpl.getAppUsersCount(userName, displayName, roleId1);

			if (appUserSearchDetail != null && !appUserSearchDetail.isEmpty()) {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
				viewResponse.setResponseStatus(response);
				viewResponse.setTotalsize(count);
				viewResponse.setRecordsPerPage(recordsPerPage);
				viewResponse.setCurrentPage(currentPage);
				viewResponse.setUserName(userName);
				viewResponse.setDisplayName(displayName);
				viewResponse.setRoleId(roleId);
				viewResponse.setAppUserSearchDetail(appUserSearchDetail);
			} else {
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_NOT_AVAIL_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_NOT_AVAIL_MSG"));
				viewResponse.setResponseStatus(response);
				viewResponse.setTotalsize(count);
				viewResponse.setRecordsPerPage(recordsPerPage);
				viewResponse.setCurrentPage(currentPage);
				viewResponse.setUserName(userName);
				viewResponse.setDisplayName(displayName);
				viewResponse.setRoleId(roleId);
				viewResponse.setAppUserSearchDetail(appUserSearchDetail);
			}
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
			ResponseStatus response = new ResponseStatus();
			response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			viewResponse.setAppUserSearchDetail(null);
			viewResponse.setResponseStatus(response);
		}
		return viewResponse;
	}
	
	@PostMapping("/save")
	public SaveResponseTO saveAppUser(HttpServletRequest request,@RequestBody(required = true) AppUserSaveTO appUserSaveTO) {
		lOGGER.info(CommonConstants.BEGIN);
		CommonUtils.logIncomingJsonData(appUserSaveTO);
		SaveResponseTO viewReponse = new SaveResponseTO();
		try {
			Integer userId = 0;
			if (!CommonUtils.isNull(appUserSaveTO.getUserId())) {
				if (StringUtils.isNumeric(String.valueOf(appUserSaveTO.getUserId()))) {
					AppUser appUser = appUserServiceImpl.getAppUserById(appUserSaveTO.getUserId());
					if (!CommonUtils.isNull(appUser)) {
						userId = appUser.getUserId();
					}else {
						ResponseStatus response = new ResponseStatus();
						response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_NOT_AVAIL_CD")));
						response.setStatusMessage(env.getProperty("APPUSER_NOT_AVAIL_MSG"));
						viewReponse.setResponseStatus(response); 
						return viewReponse;
					}
				} else {
					ResponseStatus response = new ResponseStatus();
					response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_USERID_NFE_ID")));
					response.setStatusMessage(env.getProperty("APPUSER_USERID_NFE_MSG"));
					viewReponse.setResponseStatus(response);
				}
			}
			
			if(!CommonUtils.isNull(appUserSaveTO.getMobileNo())) {
				if(!CommonUtils.isVaildMobileNumber(appUserSaveTO.getMobileNo())) {
					ResponseStatus response = new ResponseStatus();
					response.setStatusCode(Integer.parseInt(env.getProperty("COMMON_MOBILE_NUMBER_INVALID_CD")));
					response.setStatusMessage(env.getProperty("COMMON_MOBILE_NUMBER_INVALID_MESSAGE"));
					viewReponse.setResponseStatus(response);
					return viewReponse;
				}
			}			
			
			if (userId > 0) {
				int updateAppUser = appUserServiceImpl.updateAppUser(appUserSaveTO);
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_UPDATED_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_UPDATED_MSG"));
				viewReponse.setResponseStatus(response);
				lOGGER.info("::USER UPDATED::" + updateAppUser);
			} else if (userId == 0) {
				ResponseStatus responseStatus = this.screenLevelMandVaidations(appUserSaveTO);
				if (responseStatus.getStatusCode() != 0 && responseStatus.getStatusMessage() != null) {
					viewReponse.setResponseStatus(responseStatus);
					return viewReponse;
				}
				Integer saveAppUser = appUserServiceImpl.saveAppUser(appUserSaveTO,request);
				ResponseStatus response = new ResponseStatus();
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_INSERTED_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_INSERTED_MSG"));
				viewReponse.setResponseStatus(response);
				lOGGER.info("::USER INSERTED::" + saveAppUser);
			}
		} catch (Exception e) {
			ResponseStatus response = new ResponseStatus();
			response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
			viewReponse.setResponseStatus(response);
			
			String message = e.getMessage();
			if (message.contains("un_app_user_name")) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_USER_NAME_DUP_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_USER_NAME_DUP_MSG"));
				viewReponse.setResponseStatus(response);
			} else {
				response.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
				response.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
				viewReponse.setResponseStatus(response);
				lOGGER.error("::Message::" + message);
			}
			
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);

		return viewReponse;
	}
	
	private ResponseStatus screenLevelMandVaidations(AppUserSaveTO saveObj) {
		lOGGER.info(CommonConstants.BEGIN);
		ResponseStatus response = new ResponseStatus();
		try {
			if (CommonUtils.isNull(saveObj.getUserName())) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_USERNAME_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_USERNAME_MSG"));
			} else {
				Map<String, Object> param = new HashMap<>();
				param.put("appUserName", saveObj.getUserName());
				//List<CommonAPIsJson> list = commonService.getCommonApiData("appUserNameAddDataExists", param);
				AppUser appuser=appUserServiceImpl.getAppUserByUserName(saveObj.getUserName());
				if (!CommonUtils.isNull(appuser)) {
					response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_USER_NAME_DUP_CD")));
					response.setStatusMessage(env.getProperty("APPUSER_USER_NAME_DUP_MSG"));
					return response;
				}
			}
			if (CommonUtils.isNull(saveObj.getPartnerId())) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_PARTNERID_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_PARTNERID_MSG"));
			}
			if (CommonUtils.isNull(saveObj.getDisplayName())) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_DISPLAYNAME_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_DISPLAYNAME_MSG"));
			}
			if (CommonUtils.isNull(saveObj.getEmailId())) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_EMAILID_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_EMAILID_MSG"));
			}
			if (saveObj.getRoles().isEmpty()) {
				response.setStatusCode(Integer.parseInt(env.getProperty("APPUSER_ROLES_CD")));
				response.setStatusMessage(env.getProperty("APPUSER_ROLES_MSG"));
			}
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return response;
	}
}
