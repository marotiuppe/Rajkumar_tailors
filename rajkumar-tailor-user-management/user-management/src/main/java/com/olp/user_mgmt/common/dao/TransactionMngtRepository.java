package com.olp.user_mgmt.common.dao;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.entity.TransactionHistory;
import com.olp.user_mgmt.exception.DAOException;

@Repository
@PropertySource("classpath:queries.properties")
public class TransactionMngtRepository {

	private static final Logger lOGGER = LoggerFactory.getLogger(TransactionMngtRepository.class);

	@Autowired
	private EntityManager entityManager;

	public Integer insertTransactionHistory(TransactionHistory transactionHistory)
			throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		Integer tranHistoryId = 0;
		try {
			Session session = entityManager.unwrap(Session.class);
			session.save(transactionHistory);
			tranHistoryId = transactionHistory.getTranHistoryId();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return tranHistoryId;
	}

	public Integer insertTransactionHistory(TransactionHistory transactionHistory, Session session)
			throws HibernateException, DAOException {
		lOGGER.info(CommonConstants.BEGIN);
		Integer tranHistoryId = 0;
		try {
			session.save(transactionHistory);
			tranHistoryId = transactionHistory.getTranHistoryId();
		} catch (Exception ex) {
			lOGGER.error(ex.getMessage(), ex);
		}
		lOGGER.info(CommonConstants.END);
		return tranHistoryId;
	}

}
