package zhd.oa.middleware.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zhd.oa.middleware.model.KpiData;
import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.model.KpiTotalChangeOne;
import zhd.oa.middleware.service.KpiService;

public class KpiQueryJob implements Job{

	private KpiService kpiService = new KpiService();
	private static final Logger logger = LoggerFactory.getLogger(LoanJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		/**
		 * 定时每月2号9：00查询数据并插入临时的数据表中，用来计算变化量。固定的数据
		 */
		List<KpiModel> kpis = kpiService.queryKpi();
		logger.info(kpis.toString());
		for (int i = 0; i < kpis.size(); i++) {
			kpiService.insertKpiConsult(kpis.get(i));
			
		}
		
		/**
		 * 定时写入销售的整个数据
		 */
		List<KpiTotalChangeOne> ChangeOne = kpiService.queryChangeOne();
		logger.info(ChangeOne.toString());
		for (int i = 0; i < ChangeOne.size(); i++) {
			kpiService.insertChangeOne(ChangeOne.get(i).getOneName(),ChangeOne.get(i).getOneAccount(),
					ChangeOne.get(i).getOneMM(), ChangeOne.get(i).getOneWeight(),
					ChangeOne.get(i).getOneMoney(),ChangeOne.get(i).getOneGetDate());
		}
		
		/**
		 * 定时将可以查询出来的数据插入绩效表中 ，以做对比
		 */
		List<KpiData> list = kpiService.getKpiData();
		logger.info(list.toString());
		for (int i = 0; i < list.size(); i++) {
			
			int mm = Integer.parseInt(list.get(i).getMM())-1;//当月获取的是上个月的数据
			String MM = mm<=9?"0"+mm:""+mm;
			
			kpiService.insertKpiData(MM, list.get(i).getOneName(), 
					list.get(i).getOneWeight(), list.get(i).getWeightChange(),
					list.get(i).getOneMoney(), list.get(i).getMoneyChange());
		}
		
		
		
		
	}
	
	

}
