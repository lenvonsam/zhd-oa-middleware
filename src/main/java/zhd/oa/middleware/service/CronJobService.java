package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.CronJobMapper;
import zhd.oa.middleware.model.CronJob;
import zhd.oa.middleware.model.CronJobChecker;
import zhd.oa.middleware.utils.CreateNoticeWorkflow;

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

	public void cronJob(String identity){
		log.info("进入定时任务--->");
		try {
			session = openSession();
			cronJobMapper = session.getMapper(CronJobMapper.class);
			CronJobChecker cronJobChecker = cronJobMapper.checkJob(identity);//1：提醒  2：同步
			String cc = "<a href='http://oa.xingyun361.com/formmode/view/AddFormMode.jsp?customTreeDataId=null&mainid=0&modeId=2821&formId=-593&type=1' target='_blank' >【点击添加配置】</a>";
			if("1".equals(cronJobChecker.getRes())){
				log.info("创建绩效同步提醒流程！");
				log.info(cronJobChecker.getEmps());
				String requestid = CreateNoticeWorkflow.shareInstance().notice("绩效同步提醒",cronJobChecker.getEmps()+",1",cc);
				log.info("流程id-->"+requestid);
			}if("2".equals(cronJobChecker.getRes())){
				log.info("执行同步操作！");
				syncPerfJob();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
	}

}
