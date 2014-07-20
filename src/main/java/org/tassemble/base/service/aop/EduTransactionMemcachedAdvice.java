package org.tassemble.base.service.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tassemble.base.commons.dao.IDBTransactionManager;



public class EduTransactionMemcachedAdvice implements MethodInterceptor {

	private static final Log log = LogFactory
			.getLog(EduTransactionMemcachedAdvice.class);
	private IDBTransactionManager idbTransactionManager;


	public Object invoke(MethodInvocation arg0) throws Throwable {

		Object result = null;
		if (getIdbTransactionManager().getAutoCommit()) {

			if (log.isDebugEnabled()) {
				log.debug(arg0.getMethod().getName()
						+ " begin to in transaction...");
			}

			try {
				getIdbTransactionManager().setAutoCommit(false);
				result = arg0.proceed();
				getIdbTransactionManager().commit();
				getIdbTransactionManager().setAutoCommit(true);
				if (log.isDebugEnabled()) {
					log.debug(arg0.getMethod().getName()
							+ " 'transaction commited.");
				}
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug(arg0.getMethod().getName()
							+ " begin to rollback...");
				}
				getIdbTransactionManager().rollback();
				getIdbTransactionManager().setAutoCommit(true);
				if (log.isDebugEnabled()) {
					log.debug(arg0.getMethod().getName()
							+ " 'transaction  rollbacked.");
				}
				throw e;
			}

		} else {
			result = arg0.proceed();
		}

		return result;
	}



	public IDBTransactionManager getIdbTransactionManager() {
		return idbTransactionManager;
	}

	public void setIdbTransactionManager(
			IDBTransactionManager idbTransactionManager) {
		this.idbTransactionManager = idbTransactionManager;
	}

}
