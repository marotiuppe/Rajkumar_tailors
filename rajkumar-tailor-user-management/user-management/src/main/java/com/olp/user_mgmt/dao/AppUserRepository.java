package com.olp.user_mgmt.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.entity.AppUser;
import com.olp.user_mgmt.entity.AppUserLoginFailedAttempts;
import com.olp.user_mgmt.entity.UserAppProfileMapping;
import com.olp.user_mgmt.exception.DAOException;
import com.olp.user_mgmt.role.entity.AppUserRoleMapping;
import com.olp.user_mgmt.to.AppUserTO;

@Repository
public class AppUserRepository {
	private static final Logger lOGGER = LoggerFactory.getLogger(AppUserRepository.class);
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Environment env;

	public List<AppUserTO> getAppUserSearchDetail(String userName, String displayName, int roleId, Integer currentPage,
			Integer recordsPerPage) throws DAOException{
		lOGGER.info(CommonConstants.BEGIN);
		ArrayList<AppUserTO> appUserLst = null;
		try {

			Session session = entityManager.unwrap(Session.class);
			SQLQuery query = session.createSQLQuery(env.getProperty("SELECT_APPUSER_SEARCH"));
			query.setParameter("userName", userName);
			query.setParameter("displayName", displayName);
			query.setParameter("roleId", roleId);
			query.setParameter("currentPage", currentPage);
			query.setParameter("recordsPerPage", recordsPerPage);

			appUserLst = (ArrayList<AppUserTO>) query.addScalar("userId").addScalar("userName").addScalar("displayName")
					.addScalar("mobileNo").addScalar("emailId").addScalar("status")
					.addScalar("strRoleDesc").setResultTransformer(Transformers.aliasToBean(AppUserTO.class)).list();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return appUserLst;
	}
	
	public Integer getAppUsersCount(String userName, String displayName, Integer roleId) throws HibernateException {
		lOGGER.info(CommonConstants.BEGIN);
		BigInteger count = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			SQLQuery query = session.createSQLQuery(env.getProperty("SELECT_APPUSER_SEARCH_COUNT"));
			query.setParameter("userName", userName);
			query.setParameter("displayName", displayName);
			query.setParameter("roleId", roleId);
			count = (BigInteger) query.uniqueResult();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		if (count != null)
			return count.intValue();
		return 0;
	}
	
	public AppUser getAppUserById(Integer userId) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		AppUser appUser = null;
		try {
			String strQuery = env.getProperty("SELECT_APPUSER_BY_ID");
			Session session = entityManager.unwrap(Session.class);
			Query query = session.createSQLQuery(strQuery).addEntity(AppUser.class).setInteger(1, userId);
			appUser = (AppUser) query.uniqueResult();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return appUser;
	}

	public AppUser fetchAppUserByUserName(String userName) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		AppUser appUser = null;
		try {
			String strQuery = env.getProperty("SELECT_APPUSER_BY_USERNAME");
			Session session = entityManager.unwrap(Session.class);
			Query query = session.createSQLQuery(strQuery).addEntity(AppUser.class).setString(1, userName);
			appUser = (AppUser) query.uniqueResult();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return appUser;
	}


	public Integer insertAppUser(AppUser appUser) throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.save(appUser);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return appUser.getUserId();
	}
	
	public void insertAppUserRoleMapping(AppUserRoleMapping appUserRoleMapping)
			throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.save(appUserRoleMapping);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
	}

	public void deleteAppUserRoleMappingByUserId(Integer userId) throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("DELETE_ALL_APPUSERROLEMAPPING_BY_USERID");
			Query query = session.createSQLQuery(strQuery).addEntity(AppUserRoleMapping.class).setInteger(1, userId);
			query.executeUpdate();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
	}

	public void updateAppUser(AppUser appUser) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.update(appUser);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);		
	}

	public UserAppProfileMapping getAppProfileDetailsByCodeUserId(String code, Integer userId)
			throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		UserAppProfileMapping franchiseAppProfileMapping = null;
		try {
			String Sqlquery = env.getProperty("SELECT_APP_PROFILE_OPTION_BY_CODE");
			Session session = entityManager.unwrap(Session.class);
			NativeQuery<UserAppProfileMapping> query = session.createNativeQuery(Sqlquery, UserAppProfileMapping.class);
			query.setParameter(1, code);
			query.setParameter(2, userId);
			franchiseAppProfileMapping = query.getResultList().get(0);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return franchiseAppProfileMapping;
	}

	public AppUserLoginFailedAttempts getAppUserLoginFailedAttemptsByUserName(String username) {
		lOGGER.info(CommonConstants.BEGIN);
		AppUserLoginFailedAttempts obj = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			Query query = session.createQuery("SELECT aulfa from AppUserLoginFailedAttempts aulfa, AppUser au where au.userName= ?1 and au.userId=aulfa.appUserId ");
			query.setString(1, username);
			obj = (AppUserLoginFailedAttempts) query.uniqueResult();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return obj;
	}

	public void updateAppUserLoginFailedAttempts(AppUserLoginFailedAttempts appUserLoginFailedAttempts) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.update(appUserLoginFailedAttempts);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);		
	}

	public void saveAppUserLoginFailedAttempts(AppUserLoginFailedAttempts appUserLoginFailedAttempts) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.save(appUserLoginFailedAttempts);
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);		
	}

}
