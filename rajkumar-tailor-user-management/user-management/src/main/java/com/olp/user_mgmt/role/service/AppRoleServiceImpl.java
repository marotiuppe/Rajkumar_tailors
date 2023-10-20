package com.olp.user_mgmt.role.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.common.dao.TransactionMngtRepository;
import com.olp.user_mgmt.dao.AppUserRepository;
import com.olp.user_mgmt.entity.AppPrivilege;
import com.olp.user_mgmt.entity.TransactionHistory;
import com.olp.user_mgmt.exception.DAOException;
import com.olp.user_mgmt.exception.ServiceException;
import com.olp.user_mgmt.role.dao.AppRoleRepository;
import com.olp.user_mgmt.role.entity.AppRole;
import com.olp.user_mgmt.role.entity.AppRolePrivilegeMapping;
import com.olp.user_mgmt.role.to.AppActionPrivilegeBean;
import com.olp.user_mgmt.role.to.AppRoleTO;

@Service
@Transactional
public class AppRoleServiceImpl {

	private static final Logger lOGGER = LoggerFactory.getLogger(AppRoleServiceImpl.class);

	@Autowired
	AppRoleRepository appRoleRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	TransactionMngtRepository transactionMngtRepository;

	public List<AppRoleTO> fetchAppRolesByCodeDescStatus(Integer userId, String roleCode, String roleDesc, Byte stat,
			Integer currentPage, Integer recordsPerPage) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRoleTO> appRoleLst = null;
		try {
			currentPage = (currentPage - 1) * recordsPerPage;
			appRoleLst = appRoleRepository.fetchAppRolesByCodeDescStatus(userId, roleCode, roleDesc, stat, currentPage,
					recordsPerPage);

		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRoleLst;
	}

	public Integer fetchAppRolesByCodeDescStatusCount(Integer userId, String rolecode, String roledesc, Byte stat)
			throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		Integer count = 0;
		try {
			count = appRoleRepository.fetchAppRolesByCodeDescStatusCount(userId, rolecode, roledesc, stat);

		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return count;
	}

	public AppRole getAppRoleById(Integer roleId) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		AppRole appRole = null;
		try {
			appRole = appRoleRepository.getAppRoleById(roleId);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRole;
	}

	public List<AppRolePrivilegeMapping> getAllAppRolePrivilegeMappingByRoleId(Integer roleId) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRolePrivilegeMapping> appRolePrivilegeMappingLst = null;
		try {
			appRolePrivilegeMappingLst = appRoleRepository.getAllAppRolePriviligeMappingByRoleId(roleId);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRolePrivilegeMappingLst;
	}

	public List<AppActionPrivilegeBean> getAllAppActionPrivilegeBeanByUserId(Integer userId) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppActionPrivilegeBean> appActionPrivilegeBeanLst = null;
		try {
			appActionPrivilegeBeanLst = appRoleRepository.getAllAppActionPrivilegeBeanByUserId(userId);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appActionPrivilegeBeanLst;
	}

	public List<AppActionPrivilegeBean> getAllAppActionOtherPrivilegesByUserId(Integer userId) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppActionPrivilegeBean> appActionPrivilegeBeanLst = null;
		try {
			appActionPrivilegeBeanLst = appRoleRepository.getAllAppActionOtherPrivilegesByUserId(userId);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appActionPrivilegeBeanLst;
	}

	public AppRole getAppRoleByCode(String roleCode) throws Exception {
		lOGGER.info(CommonConstants.BEGIN);
		AppRole appRole = null;
		try {
			appRole = appRoleRepository.getAppRoleByCode(roleCode);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRole;
	}

	public void saveAppRoleAndPrivilages(AppRole appRole, List<Integer> appRolePrivilegeSelList, Integer loginUserId)
			throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);

		try {
			Integer roleId = appRoleRepository.save(appRole);
			appRole.setRoleId(roleId);

			appRoleRepository.saveAppRolePrivilege(appRolePrivilegeSelList, appRole, loginUserId);

			// inserting into transaction history
			TransactionHistory transobj = new TransactionHistory();
			transobj.setCatagory("AppRole");
			transobj.setCreatedBy(loginUserId);
			transobj.setCreatedDate(new Date());
			transobj.setChangeLog(" AppRole Created Successfully with Details :: " + "RoleCode : "
					+ appRole.getRoleCode() + ", Description :  " + appRole.getRoleDesc() + ", Status : " + "Active");
			transobj.setAction("Create AppRole");
			transactionMngtRepository.insertTransactionHistory(transobj);

		} catch (DAOException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public void updateAppRoleAndPrivilages(AppRole existAppRole, List<Integer> appRolePrivilegeSelList,
			Integer loginUserId, Integer rolePrivStatus) throws ServiceException, DataIntegrityViolationException {

		try {
			List<AppRolePrivilegeMapping> oldAppRolePrivilegeMappingLst = appRoleRepository
					.getAllAppRolePriviligeMappingByRoleId(existAppRole.getRoleId());
			List<Integer> oldPrivilegeIdLst = new ArrayList<>();
			for (AppRolePrivilegeMapping arpm : oldAppRolePrivilegeMappingLst) {
				AppPrivilege appPrivilege = arpm.getAppPrivilege();
				if (appPrivilege != null) {
					oldPrivilegeIdLst.add(appPrivilege.getPrivilegeId());
				}
			}

			Set<Integer> deleteappPrivIds = findDifference(oldPrivilegeIdLst, appRolePrivilegeSelList);
			Set<Integer> insertappPrivilegeIds = findDifference(appRolePrivilegeSelList, oldPrivilegeIdLst);

			appRoleRepository.update(existAppRole);

			if (!deleteappPrivIds.isEmpty()) {
				appRoleRepository.deleteAppRolePrivilegeMapping(existAppRole.getRoleId(), deleteappPrivIds);
			}

			if (rolePrivStatus != null) {
				appRoleRepository.updateAppRolePrivilegeMpgStatus(existAppRole.getRoleId(), rolePrivStatus);
			}

			if (!insertappPrivilegeIds.isEmpty()) {
				appRoleRepository.insertappRolePrivilages(insertappPrivilegeIds, existAppRole, loginUserId);
			}

			String status = null;
			if (existAppRole.getStatus() == 1) {
				status = "Active";
			} else {
				status = "Inactive";
			}

			TransactionHistory transobj = new TransactionHistory();
			transobj.setCatagory("AppRole");
			transobj.setCreatedBy(loginUserId);
			transobj.setCreatedDate(new Date());
			transobj.setChangeLog(
					" AppRole Updated Successfully with Details :: " + "RoleCode : " + existAppRole.getRoleCode()
							+ ", Description :  " + existAppRole.getRoleDesc() + ", Status : " + status);
			transobj.setAction("Update AppRole");
			transactionMngtRepository.insertTransactionHistory(transobj);

		} catch (DAOException ex) {
			lOGGER.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	private static <T> Set<T> findDifference(List<T> first, List<T> second) {
		Set<T> diff = new HashSet<>(first);
		diff.removeAll(second);
		return diff;
	}

	// Application user screen
	public List<AppRole> getActiveAppRoles(Integer userId, String userName) throws ServiceException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRole> appRoleLst = null;
		try {
			appRoleLst = appRoleRepository.getActiveAppRoles(userId, userName);
		} catch (DAOException e) {
			lOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getCause().getMessage());
		}
		lOGGER.info(CommonConstants.END);
		return appRoleLst;
	}

}
