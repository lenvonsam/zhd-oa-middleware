package zhd.oa.middleware.service;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhd.oa.middleware.config.DatabaseConfig;

public abstract class BaseService {
    protected Logger log = LoggerFactory.getLogger(BaseService.class);

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
