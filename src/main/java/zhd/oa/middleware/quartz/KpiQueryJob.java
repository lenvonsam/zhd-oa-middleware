package zhd.oa.middleware.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.model.SaleDatas;
import zhd.oa.middleware.model.ZhdKpi;
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
		logger.info("kpis.size()===>"+kpis.size());
		logger.info(kpis.toString());
		for (int i = 0; i < kpis.size(); i++) {
//			kpiService.insertKpiConsult(kpis.get(i));
			
		}
//		
//		/**
//		 * 定时写入销售的整个数据
//		 */
//		List<KpiTotalChangeOne> ChangeOne = kpiService.queryChangeOne();
//		logger.info("ChangeOne.size()==>"+ChangeOne.size());
//		logger.info(ChangeOne.toString());
//		for (int i = 0; i < ChangeOne.size(); i++) {
////			kpiService.insertChangeOne(ChangeOne.get(i));
//		}
//		
//		/**
//		 * 定时将可以查询出来的数据插入绩效表中 ，以做对比
//		 */
//		List<KpiData> list = kpiService.getKpiData();
//		logger.info("list.size()==>"+list.size());
//		logger.info(list.toString());
//		for (int i = 0; i < list.size(); i++) {
////			kpiService.insertKpiData(list.get(i));
//		}
//		
		/**
		 * 2019-07-06
		 * 查询当前日期的上个月的实时Erp数据,
		 * 结果插入到uf_onetotal表中,以计算得出变化量
		 * 
		 */
		List<SaleDatas> listSaleDatas = kpiService.getSaleDatas();
		logger.info("listSaleDatas.size()==>"+listSaleDatas.size());
		logger.info(listSaleDatas.toString());
		for (int i = 0; i < listSaleDatas.size(); i++) {
//			kpiService.insertSaleDatasIntoOnetotal(listSaleDatas.get(i));
		}
		
		/**
		 * 2019-07-08
		 * 将后台算出来的数据查询出来（当前日期的上个月的），  后面插入到kpitable中去和前端数据对比
		 * @return 
		 */
		List<ZhdKpi> listZhdKpi = kpiService.getSaleKpis();
		logger.info("listZhdKpi.size()==>"+listZhdKpi.size());
		logger.info(listZhdKpi.toString());
		for (int i = 0; i < listZhdKpi.size(); i++) {
//			kpiService.insertSaleKpisIntoKpicompare(listZhdKpi.get(i));
		}
		
		
		/**
		 * 2019-07-08
		 * 提成报表
		 * 每月2号将查询的数据插入表中
		 */
		//kpiService.getKpidataAndInsertIntoWagetable();
		
		logger.info("job one times is over!!!");
		
		
		
		
	}
	
	

}
