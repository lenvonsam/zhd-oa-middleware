package zhd.oa.middleware.service;

import zhd.oa.middleware.mapper.ErpTransRecordMapper;
import zhd.oa.middleware.model.ErpTransRecord;

public class ErpTransRecordService extends BaseService {
	private ErpTransRecordMapper mapper = null;

	public ErpTransRecord findOne(Integer id) {
		try {
			session = openSession();
			mapper = session.getMapper(ErpTransRecordMapper.class);
			ErpTransRecord m = mapper.findOne(25);
			System.out.println("m name:>>" + m.getFlow_name());
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

}
