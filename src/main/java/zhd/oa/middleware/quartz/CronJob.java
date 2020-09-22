package zhd.oa.middleware.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhd.oa.middleware.service.CronJobService;

public class CronJob implements Job {
    protected Logger log = LoggerFactory.getLogger(CronJob.class);
    private static CronJobService cronJobService = null;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if(cronJobService == null){
            cronJobService = new CronJobService();
        }
        cronJobService.cronJob("ErpPerfEmp");
        log.info("执行定时任务CronJob: ===> ErpPerfEmp");
    }

    /**
     * 记录定时任务
     */
    public void recordJob(){

    }

}
