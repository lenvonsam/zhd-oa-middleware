package zhd.oa.middleware.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.service.KpiService;

public class KpiQueryJob implements Job{

	private KpiService kpiService = new KpiService();
	private static final Logger logger = LoggerFactory.getLogger(LoanJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		List<KpiModel> kpis = kpiService.queryKpi();
		
		for (int i = 0; i < kpis.size(); i++) {
			kpiService.insertKpiConsult(kpis.get(i));
		}
		System.out.println(kpis);
	}
	
	

}
