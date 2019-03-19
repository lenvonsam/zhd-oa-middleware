package zhd.oa.middleware.service;

import org.apache.ibatis.session.SqlSession;

import zhd.oa.middleware.config.DatabaseConfig;

public abstract class BaseService {

	protected SqlSession session = null;

	protected SqlSession openSession() throws Exception {
		return  DatabaseConfig.shareInstance().sessionFactory.openSession();
	}

	protected void closeSession() {
		try {
			if (session != null)
				session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
