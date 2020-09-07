package zhd.oa.middleware.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import zhd.oa.middleware.model.AcceptanceBill;
import zhd.oa.middleware.service.AcceptanceBillService;
import zhd.oa.middleware.utils.DateUtil;
import zhd.oa.middleware.utils.WorkflowUtil;

/**
 * 承兑电汇定时提交
 * 
 * @author liguang
 * @date 201..
 *
 */
public class AcceptanceBillJob implements Job {
	private AcceptanceBillService acceptanceBillService = new AcceptanceBillService();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("acceptanceBilljob corn start");
			List<AcceptanceBill> acceptanceBills = acceptanceBillService.queryAcceptanceBill();

			for (int i = 0; i < acceptanceBills.size(); i++) {

				String docDatet = acceptanceBills.get(i).getDocdatet();

				Date datet = DateUtil.shareInstance().str2Date(docDatet);// 承兑汇票结束日期
				System.out.println(datet+"datet*********");
				int days = (int)((datet.getTime()-new Date().getTime())/(1000*60*60*24));
				System.out.println("days-------->"+days);
				if (days <= 3) {
					
					System.out.println(acceptanceBills.get(i));
					List<String> requestids = WorkflowUtil.shareInstance().getToDoList(124, null);
					if (requestids.contains(acceptanceBills.get(i).getRequestid())) {

						WorkflowUtil.shareInstance()
								.operateRequest(Integer.parseInt(acceptanceBills.get(i).getRequestid()), 124, "submit","系统提交");
					}
				}
			}
			
			System.out.println("one time over!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
