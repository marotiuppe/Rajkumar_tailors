package com.olp.user_mgmt.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.common.CommonUtils;
import com.olp.user_mgmt.common.dao.TransactionMngtRepository;
import com.olp.user_mgmt.dao.AppUserRepository;
import com.olp.user_mgmt.dao.AppUserRepositoryJpa;
import com.olp.user_mgmt.entity.AppUser;
import com.olp.user_mgmt.entity.AppUserLoginFailedAttempts;
import com.olp.user_mgmt.entity.TransactionHistory;
import com.olp.user_mgmt.entity.User;
import com.olp.user_mgmt.entity.UserAppProfileMapping;
import com.olp.user_mgmt.exception.DAOException;
import com.olp.user_mgmt.role.entity.AppRole;
import com.olp.user_mgmt.role.entity.AppUserRoleMapping;
import com.olp.user_mgmt.to.AppUserSaveTO;
import com.olp.user_mgmt.to.AppUserTO;
import com.olp.user_mgmt.to.NotificationRequest;


@Service
public class AppUserServiceImpl {

	private static final Logger lOGGER = LoggerFactory.getLogger(AppUserServiceImpl.class);

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	AppUserRepositoryJpa appUserRepositoryJpa;

	@Autowired
	TransactionMngtRepository transactionMngtRepository;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	NotificationServiceImpl notificationServiceImpl;

	public List<AppUserTO> getAppUserDetailList(String userName, String displayName, int roleId, Integer currentPage,
			Integer recordsPerPage) {
		lOGGER.info(CommonConstants.BEGIN);

		List<AppUserTO> appUserLst = null;
		try {
			currentPage = (currentPage - 1) * recordsPerPage;
			appUserLst = appUserRepository.getAppUserSearchDetail(userName, displayName, roleId, currentPage,
					recordsPerPage);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		}
		lOGGER.info(CommonConstants.END);
		return appUserLst;
	}

	public Integer getAppUsersCount(String userName, String displayName, int roleId) {
		lOGGER.info(CommonConstants.BEGIN);
		Integer count = 0;
		try {
			count = appUserRepository.getAppUsersCount(userName, displayName, roleId);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		}
		lOGGER.info(CommonConstants.END);
		return count;
	}

	public AppUser getAppUserById(Integer userId) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		AppUser appUser = null;
		try {
			appUser = appUserRepository.getAppUserById(userId);
		} catch (DAOException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		}
		lOGGER.info(CommonConstants.END);
		return appUser;
	}

	public AppUser getAppUserByUserName(String username) {
		lOGGER.info(CommonConstants.BEGIN);
		AppUser user = null;
		try {
			user = appUserRepository.fetchAppUserByUserName(username.trim());
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return user;
	}

	@Transactional
	public int updateAppUser(AppUserSaveTO appUserSaveTO) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		Integer userIdUpdated = 0;
		try {
			AppUser appUserOld = this.getAppUserById(appUserSaveTO.getUserId());

//			if (appUserSaveTO.getStatusId() > 0) {
//				appUserOld.setStatus(appUserSaveTO.getStatusId());
//			}
			appUserOld.setStatus(appUserSaveTO.getStatusId());

			if (StringUtils.isNotBlank(appUserSaveTO.getDisplayName())) {
				appUserOld.setDisplayName(appUserSaveTO.getDisplayName());
			}

			if (StringUtils.isNotBlank(appUserSaveTO.getMobileNo())) {
				appUserOld.setMobileNo(appUserSaveTO.getMobileNo());
			}

			if (StringUtils.isNotBlank(appUserSaveTO.getEmailId())) {
				appUserOld.setEmailId(appUserSaveTO.getEmailId());
			}

			if (StringUtils.isNotBlank(appUserSaveTO.getRemarks())) {
				appUserOld.setRemarks(appUserSaveTO.getRemarks());
			}

			appUserOld.setModifiedBy(CommonUtils.getWebUser().getUserid());
			appUserOld.setModifiedDate(new Date());

			AppUser save = appUserRepositoryJpa.save(appUserOld);
			userIdUpdated = save.getUserId();

			Set<Integer> appUserRoleMappings = appUserSaveTO.getRoles();

			if (!CommonUtils.isNull(appUserRoleMappings)) {
				appUserRepository.deleteAppUserRoleMappingByUserId(appUserOld.getUserId());

				for (Integer roleId : appUserRoleMappings) {
					AppRole appRole = new AppRole();
					appRole.setRoleId(roleId);
					AppUserRoleMapping appUserRoleMapping = new AppUserRoleMapping();
					appUserRoleMapping.setAppRole(appRole);
					appUserRoleMapping.setAppUser(appUserOld);
					appUserRoleMapping.setStatus((byte) 1);
					appUserRoleMapping.setCreatedBy(CommonUtils.getWebUser().getUserid());
					appUserRoleMapping.setCreatedDate(new Date());
					appUserRoleMapping.setModifiedBy(CommonUtils.getWebUser().getUserid());
					appUserRoleMapping.setModifiedDate(new Date());
					appUserRepository.insertAppUserRoleMapping(appUserRoleMapping);
				}
			}

			TransactionHistory transobj = new TransactionHistory();
			transobj.setCatagory("AppUser");
			transobj.setCreatedBy(CommonUtils.getWebUser().getUserid());
			transobj.setCreatedDate(new Date());
			transobj.setChangeLog(" Appuser Updated Successfully with Details :: UserName : " + appUserOld.getUserName()
					+ ", Display Name :  " + appUserOld.getDisplayName() + " ,MobileNo : " + appUserOld.getMobileNo()
					+ ", Email : " + appUserOld.getEmailId());
			transobj.setAction("Update Appuser");

			transactionMngtRepository.insertTransactionHistory(transobj);

		} catch (ServiceException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		} catch (DAOException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		}
		lOGGER.info("::App User Updated With UserId::" + userIdUpdated);
		lOGGER.info(CommonConstants.END);
		return userIdUpdated;

	}

	@Transactional
	public Integer saveAppUser(AppUserSaveTO appUserSaveTO, HttpServletRequest request) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		Integer savedUserId = 0;
		try {
			AppUser appUser = new AppUser();

			appUser.setUserName(appUserSaveTO.getUserName());
			appUser.setStatus((byte) 1);
			appUser.setDisplayName(appUserSaveTO.getDisplayName());
			appUser.setMobileNo(appUserSaveTO.getMobileNo());
			appUser.setEmailId(appUserSaveTO.getEmailId());
			appUser.setRemarks(appUserSaveTO.getRemarks());
			appUser.setCreatedBy(CommonUtils.getWebUser().getUserid());
			appUser.setCreatedDate(new Date());
			appUser.setModifiedBy(CommonUtils.getWebUser().getUserid());
			appUser.setModifiedDate(new Date());

			appUser.setApiKey(CommonUtils.generateUniqueIdentifierNumber());
			appUser.setUserPassword("");
			appUser.setResetToken(UUID.randomUUID().toString() + ":" + System.currentTimeMillis());

			LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(20, ChronoUnit.MINUTES));
			Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
			appUser.setResetTokenExpiryTime(tmfn);

			savedUserId = appUserRepository.insertAppUser(appUser);
			appUser.setUserId(savedUserId);

			Set<Integer> appUserRoleMappings = appUserSaveTO.getRoles();
			for (Integer roleId : appUserRoleMappings) {
				AppRole appRole = new AppRole();
				appRole.setRoleId(roleId);
				AppUserRoleMapping appUserRoleMapping = new AppUserRoleMapping();
				appUserRoleMapping.setAppRole(appRole);
				appUserRoleMapping.setAppUser(appUser);
				appUserRoleMapping.setStatus((byte) 1);
				appUserRoleMapping.setCreatedBy(CommonUtils.getWebUser().getUserid());
				appUserRoleMapping.setCreatedDate(new Date());
				appUserRepository.insertAppUserRoleMapping(appUserRoleMapping);
			}

			String path = request.getRequestURL().toString();
			if (StringUtils.isNotBlank(path)) {
				if (path.contains("/appuser/save")) {
					path = path.replace("/appuser/save", "/#/");
				}
			}
			lOGGER.info("PATH", path);
			
			NotificationRequest notificationRequest = new NotificationRequest();
//			notificationRequest.setAppuser(appUser);
			AppUserTO appUserTo = new AppUserTO();
			appUserTo.setDisplayName(appUser.getDisplayName());
			appUserTo.setEmailId(appUser.getEmailId());
			appUserTo.setMobileNo(appUser.getMobileNo());
			appUserTo.setUserName(appUser.getUserName());
			appUserTo.setUserId(appUser.getUserId());
			notificationRequest.setAppuser(appUserTo);
			notificationRequest.setContextPath(path);
			notificationRequest.setResetToken(appUser.getResetToken());
			
			notificationServiceImpl.sendSignUpSuccsessNotification(notificationRequest);
			
			notificationServiceImpl.sendForgotPasswordLink(notificationRequest);

			TransactionHistory transobj = new TransactionHistory();
			transobj.setCatagory("AppUser");
			transobj.setCreatedBy(CommonUtils.getWebUser().getUserid());
			transobj.setCreatedDate(new Date());
			transobj.setChangeLog(" Appuser Created Successfully with Details :: UserName : " + appUser.getUserName()
					+ ", Display Name :  " + appUser.getDisplayName() + " MobileNo : " + appUser.getMobileNo()
					+ ", Email : " + appUser.getEmailId());
			transobj.setAction("Create Appuser");
			transactionMngtRepository.insertTransactionHistory(transobj);

		} catch (DAOException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage());
		}
		lOGGER.info("::App User Saved With UserId::" + savedUserId);
		lOGGER.info(CommonConstants.END);
		return savedUserId;

	}

//	@Transactional
//	public void sendAppUserPasswordLink(AppUser appUser, String contextPath) throws ServiceException {
//		try {
//			Notification notification = new Notification();
//			Map<String, String> msgContentMap = new HashMap<String, String>();
//			msgContentMap.put("#USER_NAME#", appUser.getUserName());
//			msgContentMap.put("#LINK#", contextPath + "createnewPassword?username=" + appUser.getUserName() + "&token="
//					+ appUser.getResetToken());
//
//			AppUser au = appUserRepository.fetchAppUserByUserName("admin");
//			String refKey = "APP_USER_ID";
//			Integer userId = null;
//			String refValue = "";
//			if (null != appUser.getUserId()) {
//				refValue = appUser.getUserId().toString();
//				userId = appUser.getUserId();
//			} else {
//				refValue = appUser.getUserName().toString();
//				userId = 1;
//			}
//
//			NotificationTemplateDetails notificationTemplateDetails = notificationServiceImpl
//					.getNotitifcationTemplateDetailsByTemplateCodeAndLanguageCode("APP_USER_PASSWORD", "ENGLISH");
//			if (notificationTemplateDetails != null) {
//				notificationTemplateDetails = messageHandler
//						.modifyEmailSubjectAndContentForSMSAndEmail(notificationTemplateDetails, msgContentMap);
//
//				notification.setRefKey(refKey);
//				notification.setRefValue(refValue);
//				notification.setSubject(notificationTemplateDetails.getEmailSubject());
//				notification.setMessage(notificationTemplateDetails.getEmailContent());
//				notification.setSmsMessage(notificationTemplateDetails.getSmsContent());
//				notification.setPriority(notificationTemplateDetails.getPriority());
//				notification.setMessageType(notificationTemplateDetails.getMessageType());
//				notification.setNotificationCategory(notificationTemplateDetails.getNotificationCategory());
//				notification.setCreatedBy(userId);
//				notification.setCreatedDate(new Date());
//
//				notificationRepository.saveNotification(notification);
//				NotificationDetails notificationDetails = new NotificationDetails();
//				notificationDetails.setNotification(notification);
//				notificationDetails.setMacAddress(appUser.getEmailId());
//				notificationDetails.setSmsStatus((byte) notificationTemplateDetails.getIsSmsEnabled());
//				notificationDetails.setEmailStatus((byte) notificationTemplateDetails.getIsEmailEnabled());
//				notificationDetails.setCreatedBy(userId);
//				notificationDetails.setCreatedDate(new Date());
//				notificationRepository.saveNotificationDetails(notificationDetails);
//
//				notificationServiceImpl.sendNotificationMail(appUser.getEmailId(),
//						notificationTemplateDetails.getEmailSubject(), notificationTemplateDetails.getEmailContent(),
//						au);
//			}
//
//		} catch (Exception ex) {
//			lOGGER.error(ex.getMessage(), ex);
//			throw new ServiceException(ex.getMessage(), ex);
//		}
//		lOGGER.info(CommonConstants.END);
//
//	}

	@Transactional
	public void updateAppUserPassword(AppUser appUser, String action) throws Exception {
		try {
			appUserRepository.updateAppUser(appUser);
			// inserting into transaction history
			TransactionHistory transobj = new TransactionHistory();
			transobj.setCatagory("AppUser");
			transobj.setCreatedBy(appUser.getUserId());
			transobj.setCreatedDate(new Date());
			transobj.setChangeLog(" Appuser Updated Successfully with Details :: UserName : " + appUser.getUserName()
					+ ", Display Name :  " + appUser.getDisplayName() + " ,MobileNo : " + appUser.getMobileNo()
					+ ", Email : " + appUser.getEmailId());
			transobj.setAction(action);
			transactionMngtRepository.insertTransactionHistory(transobj);
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
	}

	public AppUserLoginFailedAttempts getAppUserLoginFailedAttemptsByUserName(String username) {
		lOGGER.info(CommonConstants.BEGIN);
		AppUserLoginFailedAttempts obj = null;

		try {
			obj = appUserRepository.getAppUserLoginFailedAttemptsByUserName(username);			
		}catch(Exception e){
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return obj;
	}

	@Transactional
	public void saveOrUpdateAppUserLoginFailedAttempts(AppUserLoginFailedAttempts apUserLastLogin, User user) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			if (apUserLastLogin != null) {
				apUserLastLogin.setAppUserId(user.getUserid());
				apUserLastLogin.setModifiedDate(new Date());
				apUserLastLogin.setLastLoginTime(new Date());
				apUserLastLogin.setActiveSessionId(null);
				apUserLastLogin.setFailedAttempts(0);
				appUserRepository.updateAppUserLoginFailedAttempts(apUserLastLogin);
			} else {
				apUserLastLogin = new AppUserLoginFailedAttempts();
				apUserLastLogin.setAppUserId(user.getUserid());
				apUserLastLogin.setModifiedDate(new Date());
				apUserLastLogin.setLastLoginTime(new Date());
				apUserLastLogin.setActiveSessionId(null);
				apUserLastLogin.setFailedAttempts(0);
				appUserRepository.saveAppUserLoginFailedAttempts(apUserLastLogin);
			}

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
	}

}
