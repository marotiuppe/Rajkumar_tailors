package com.olp.user_mgmt.role.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.entity.AppPrivilege;
import com.olp.user_mgmt.exception.DAOException;
import com.olp.user_mgmt.role.entity.AppRole;
import com.olp.user_mgmt.role.entity.AppRolePrivilegeMapping;
import com.olp.user_mgmt.role.to.AppActionPrivilegeBean;
import com.olp.user_mgmt.role.to.AppRoleTO;

@SuppressWarnings("deprecation")
@Repository
//@PropertySource("classpath:queries.properties")
public class AppRoleRepository {

	private static final Logger lOGGER = LoggerFactory.getLogger(AppRoleRepository.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Environment env;

	@SuppressWarnings("unchecked")
	public List<AppRoleTO> fetchAppRolesByCodeDescStatus(Integer userId, String rolecode, String roledesc, Byte stat,
			Integer currentPage, Integer recordsPerPage) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRoleTO> AppRoleToList = null;
		try {
			String strQuery = env.getProperty("SELECT_APPROLE_BY_CODE_DESC_STATUS");
			Session session = entityManager.unwrap(Session.class);
			@SuppressWarnings("rawtypes")
			SQLQuery query = session.createSQLQuery(strQuery);
			AppRoleToList = query.addScalar("roleId", IntegerType.INSTANCE).addScalar("roleCode", StringType.INSTANCE)
					.addScalar("roleDesc", StringType.INSTANCE).addScalar("status", StringType.INSTANCE)
					.setInteger(1, userId).setString(2, rolecode).setString(3, rolecode).setString(4, roledesc)
					.setString(5, roledesc).setByte(6, stat).setByte(7, stat).setInteger(8, currentPage)
					.setInteger(9, recordsPerPage).setResultTransformer(Transformers.aliasToBean(AppRoleTO.class))
					.list();

		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return AppRoleToList;

	}

	@SuppressWarnings("rawtypes")
	public Integer fetchAppRolesByCodeDescStatusCount(Integer userId, String rolecode, String roledesc, Byte stat)
			throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		BigInteger count = null;
		try {
			String strQuery = env.getProperty("SELECT_APPROLE_BY_CODE_DESC_STATUS_COUNT");
			Session session = entityManager.unwrap(Session.class);
			SQLQuery query = session.createSQLQuery(strQuery);
			query.setInteger(1, userId).setString(2, rolecode).setString(3, rolecode).setString(4, roledesc)
					.setString(5, roledesc).setByte(6, stat).setByte(7, stat);
			count = (BigInteger) query.uniqueResult();
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		if (count != null)
			return count.intValue();
		return 0;
	}

	public AppRole getAppRoleById(Integer roleId) throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		AppRole appRole = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			appRole = (AppRole) session.get(AppRole.class, roleId);
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRole;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AppRolePrivilegeMapping> getAllAppRolePriviligeMappingByRoleId(Integer id) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRolePrivilegeMapping> appRoleMapping = null;
		try {
			String strQuery = env.getProperty("SELCT_APP_ROLE_PRIVILAGES_BY_ROLE_ID");
			Session session = entityManager.unwrap(Session.class);
			Query query = session.createSQLQuery(strQuery).addEntity(AppRolePrivilegeMapping.class).setInteger(1, id);
			appRoleMapping = (List<AppRolePrivilegeMapping>) query.list();
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRoleMapping;
	}

	@SuppressWarnings("unchecked")
	public List<AppActionPrivilegeBean> getAllAppActionPrivilegeBeanByUserId(Integer userId) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppActionPrivilegeBean> appActionPrivilegeBeanLst = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("SELECT_ALL_APPACTIONPRIVILEGEBEAN_BY_USER");
			appActionPrivilegeBeanLst = session.createSQLQuery(strQuery).addScalar("actionId").addScalar("actionCode")
					.addScalar("actionDesc").addScalar("viewPrivilege", IntegerType.INSTANCE)
					.addScalar("addPrivilege", IntegerType.INSTANCE).addScalar("editPrivilege", IntegerType.INSTANCE)
					.addScalar("deletePrivilege", IntegerType.INSTANCE).setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(AppActionPrivilegeBean.class)).list();
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appActionPrivilegeBeanLst;
	}

	@SuppressWarnings("unchecked")
	public List<AppActionPrivilegeBean> getAllAppActionOtherPrivilegesByUserId(Integer userId) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppActionPrivilegeBean> appActionPrivilegeBeanLst = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("SELECT_OTHER_PRIVILEGES_BY_USERID");
			appActionPrivilegeBeanLst = session.createSQLQuery(strQuery).addScalar("actionId").addScalar("actionCode")
					.addScalar("actionDesc").addScalar("otherPrivilege", IntegerType.INSTANCE)
					.addScalar("privilegedesc", StringType.INSTANCE).setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(AppActionPrivilegeBean.class)).list();
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appActionPrivilegeBeanLst;
	}

	public AppRole getAppRoleByCode(String code) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		AppRole appRole = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("SELCT_APP_ROLE_BY_CODE");
			appRole = (AppRole) session.createSQLQuery(strQuery).addEntity(AppRole.class).setString(1, code)
					.uniqueResult();
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRole;
	}

	public Integer save(AppRole appRole) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.save(appRole);
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRole.getRoleId();
	}

	public void update(AppRole existAppRole) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			session.update(existAppRole);
		} catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
	}

	public void saveAppRolePrivilege(List<Integer> appRolePrivilegeSelList, AppRole appRole, Integer loginUserId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			for (Integer privilegeId : appRolePrivilegeSelList) {
				AppRolePrivilegeMapping appMapping = new AppRolePrivilegeMapping();
				AppPrivilege appPrivilege = new AppPrivilege();
				appMapping.setAppRole(appRole);
				appMapping.setStatus((byte) 1);
				appMapping.setCreatedBy(loginUserId);
				appMapping.setCreatedDate(new Date());
				appPrivilege.setPrivilegeId(privilegeId);
				appMapping.setAppPrivilege(appPrivilege);
				session.save(appMapping);
			}
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
	}

	public void deleteAppRolePrivilegeMapping(Integer roleId, Set<Integer> deleteappPrivilegeIds) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("DELETE_APPROLEPRIVILAGES_BY_ROLE_AND_PRIVILEGE");
			for (Integer privilegeId : deleteappPrivilegeIds) {
				session.createSQLQuery(strQuery).addEntity(AppRolePrivilegeMapping.class).setInteger(1, roleId)
						.setInteger(2, privilegeId).executeUpdate();
			}

		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
	}

	public void updateAppRolePrivilegeMpgStatus(Integer roleId, Integer rolePrivilegeStatus) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			String strQuery = env.getProperty("UPDATE_APPROLEPRIVILAGESTATUS_BY_ROLEID");
			session.createSQLQuery(strQuery).addEntity(AppRolePrivilegeMapping.class).setInteger(1, rolePrivilegeStatus)
					.setInteger(2, roleId).executeUpdate();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);

	}

	public void insertappRolePrivilages(Set<Integer> insertappPrivilegeIds, AppRole existAppRole, Integer loginUserId) {
		lOGGER.info(CommonConstants.BEGIN);
		try {
			Session session = entityManager.unwrap(Session.class);
			for (Integer privilegeId : insertappPrivilegeIds) {
				AppRolePrivilegeMapping appMapping = new AppRolePrivilegeMapping();
				AppPrivilege appPrivilege = new AppPrivilege();
				appMapping.setAppRole(existAppRole);
				appMapping.setStatus((byte) 1);
				appMapping.setCreatedBy(loginUserId);
				appMapping.setCreatedDate(new Date());
				appPrivilege.setPrivilegeId(privilegeId);
				appMapping.setAppPrivilege(appPrivilege);
				session.save(appMapping);
			}
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
	}

	// Application user screen
	public List<AppRole> getActiveAppRoles(Integer userId, String userName) throws DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		List<AppRole> appRoleLst = null;
		String strQuery = "";
		Query query = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			if (userName.toUpperCase().equals("ADMIN")) {
				strQuery = env.getProperty("SELECT_ACTIVE_APPROLE");
				query = session.createSQLQuery(strQuery).addEntity(AppRole.class).setInteger(1, userId);
			} else {
				strQuery = env.getProperty("SELECT_ACTIVE_ASSIGNED_APPROLE");
				query = session.createSQLQuery(strQuery).addEntity(AppRole.class).setParameter(1, userName);
			}
			appRoleLst = (List<AppRole>) query.list();
		}

		catch (Exception e) {
			lOGGER.error(e.getMessage(), e);
		}
		lOGGER.info(CommonConstants.END);
		return appRoleLst;

	}

}
