package com.olp.user_mgmt.dao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.entity.UserAppProfileMapping;
import com.olp.user_mgmt.exception.DAOException;

@Repository
@Transactional
public class UserAppProfileMappingRepository {

	private static final Logger lOGGER = LoggerFactory.getLogger(UserAppProfileMappingRepository.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Environment env;

	public UserAppProfileMapping getAppProfileDetailsByCodeUserId(String code, Integer userId) throws DAOException {
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

}
