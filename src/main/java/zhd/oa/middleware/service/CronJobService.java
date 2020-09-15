package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.CronJobMapper;
import zhd.oa.middleware.model.CronJob;

public class CronJobService extends BaseService{
	
	private CronJobMapper cronJobMapper;
	
	public List<CronJob> getCronJobData(){
		
		List<CronJob> jobs = null;
		
		try {
			session = openSession();
			cronJobMapper = session.getMapper(CronJobMapper.class);
			jobs = cronJobMapper.getCronJobData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return jobs;
	}

	/**
	 * 同步Erp绩效相关的数据到OA的uf_ErpPerfEmp表中
	 * @return
	 */
	public boolean syncPerfJob(){
		boolean res = false ;

		try {
			session = openSession();
			cronJobMapper = session.getMapper(CronJobMapper.class);
			cronJobMapper.clearErpPerfEmp();//同步前清除
			res = cronJobMapper.syncPerfJob();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
		return res;
	}


}
