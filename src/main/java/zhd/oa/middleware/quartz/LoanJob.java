package zhd.oa.middleware.quartz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zhd.oa.middleware.model.LoansInfo;
import zhd.oa.middleware.model.LoansInterest;
import zhd.oa.middleware.service.LoansInfoService;
import zhd.oa.middleware.service.LoansInterestService;
import zhd.oa.middleware.utils.DateUtil;
import zhd.oa.middleware.utils.WorkflowUtil;
/**
 * 贷款扣息定时提交
 * @author liguang
 * @date 2019-05-13
 * */
public class LoanJob implements Job {
	private LoansInfoService loansInfoService = new LoansInfoService();
	private LoansInterestService loansInterestService = new LoansInterestService();
	private static final Logger logger = LoggerFactory.getLogger(LoanJob.class);  

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 按最后一期做判断
		try{
			checkFirstInterest();
			checkLoanInterest();
			submitLoanJob();
			System.out.println(new Date().toString());
		}catch(Exception e){
			
			e.printStackTrace();
		}
				
		
	}
	/**
	 *定时提交流程
	 */
	public void submitLoanJob(){
		
		try {
		
			List<LoansInterest> loansInterestList = loansInterestService.queryLastLoansInterest();

			for (int i = 0; i < loansInterestList.size(); i++) {

				String interestTo = loansInterestList.get(i).getInterestTo();
				
				Date datet = DateUtil.shareInstance().str2Date(interestTo);
				
				long lastDays = ((datet.getTime()-new Date().getTime())/(1000*60*60*24));
				
				String status = loansInterestList.get(i).getPeriodsStatus();
				if ((lastDays == 3 ||lastDays == 0)&& "待扣息".equals(status)) {

					List<String> requestids = WorkflowUtil.shareInstance().getToDoList(124, null);

					String requestid = loansInfoService
							.queryLoansInfoRequestid(loansInterestList.get(i).getMainid().intValue());

					if (requestids.contains(requestid)) {

						String res = WorkflowUtil.shareInstance().operateRequest(Integer.parseInt(requestid), 124,
								"submit");
						logger.info("流程提交"+loansInterestList.get(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检测贷款流程主表，针对于没有明细数据的首次扣息 并插入
	 * 
	 * @throws ParseException
	 * 
	 */
	public void checkFirstInterest() {
		try {
			List<LoansInfo> loansInfos = loansInfoService.queryFirstLoansInfo();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (loansInfos.size() > 0) {
				for (int i = 0; i < loansInfos.size(); i++) {
					String loansDateFrom = loansInfos.get(i).getLoansDateFrom();
					
					Date loansDateF = DateUtil.shareInstance().str2Date(loansDateFrom);
					
					Calendar calendarT = Calendar.getInstance();
					calendarT.setTime(loansDateF);
					calendarT.set(Calendar.DAY_OF_MONTH, loansInfos.get(i).getDiscountDate().intValue());
					
					logger.info(calendarT.getTime().toString());
					
					long days = (new Date().getTime()-calendarT.getTime().getTime())/(1000*60*60*24);

					if (days > 0) {
						calendarT.add(Calendar.MONTH, 1);
					}
					if (calendarT.getTime().getTime() == loansDateF.getTime()) {
						calendarT.add(Calendar.MONTH, 1);
					}

					int interestDays = (int) ((calendarT.getTimeInMillis() - loansDateF.getTime())
							/ (1000 * 60 * 60 * 24)+1);

					BigDecimal rate = loansInfos.get(i).getLoanRate().multiply(new BigDecimal(0.01));
					BigDecimal interestAmount = rate.divide(new BigDecimal(360), 10, BigDecimal.ROUND_DOWN)
							.multiply(loansInfos.get(i).getLoansMoney()).multiply(new BigDecimal(interestDays));

					LoansInterest firstLoansInterest = new LoansInterest();
					firstLoansInterest.setMainid(loansInfos.get(i).getId());
					firstLoansInterest.setPeriodNum(new BigDecimal(1));
					;
					firstLoansInterest.setPeriods("第1期");
					firstLoansInterest.setInterestFrom(loansInfos.get(i).getLoansDateFrom());
					firstLoansInterest.setInterestTo(simpleDateFormat.format(calendarT.getTime()));
					firstLoansInterest.setInterestDays(new BigDecimal(interestDays));
					firstLoansInterest.setInterestAmount(interestAmount);
					firstLoansInterest.setPeriodsStatus("待扣息");				
					
					logger.info("首次插入明细"+firstLoansInterest);
					
					loansInterestService.insertLoansInterest(firstLoansInterest);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查是否有需要添加的明细
	 * 
	 * @throws ParseException
	 */
	public void checkLoanInterest() throws ParseException {

		try{
			List<LoansInterest> loansInterestList = loansInterestService.queryLastLoansInterest();
	
			logger.info("贷款有[" + loansInterestList.size() + "]条记录需要继续！");
			
			if (loansInterestList.size() > 0) {
				for (int i = 0; i < loansInterestList.size(); i++) {
					
					String interestTo = loansInterestList.get(i).getInterestTo();
					
					Date dateTo = DateUtil.shareInstance().str2Date(interestTo);
					
					
					
//					int dateToMonth = dateTo.getMonth() + 1;
//					int dateToYear = dateTo.getYear() + 1;
//	
//					int currentMonth = new Date().getMonth() + 1;
//					int currentYear = new Date().getYear() + 1;

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String s = sdf.format(new Date());
					Date nowDate =  sdf.parse(s);
					
					LoansInfo loan = loansInfoService.queryLoansInfo(loansInterestList.get(i).getMainid().intValue());
					Date loanDateTo = DateUtil.shareInstance().str2Date(loan.getLoansDateTo());
					
					if (nowDate.getTime()-dateTo.getTime()>0&&new Date().getTime()-loanDateTo.getTime()<0) {
						// 扣息明细月份小于当前日期月份的需要添加明细
						newInterest(loansInterestList.get(i));
						logger.info("插入明细"+loansInterestList.get(i));
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 根据最近的一条扣息创建明细数据并插入(非首次情况下)
	 * 
	 * @param mainid
	 * @param loansInterest
	 */
	public void newInterest(LoansInterest loansInterest) {
		
		try{
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			LoansInfo loansInfo = loansInfoService.queryLoansInfo(loansInterest.getMainid().intValue());
			LoansInterest newLoansInterest = new LoansInterest();
	
			// mainid
			newLoansInterest.setMainid(loansInterest.getMainid());
	
			// 期数Num 和 显示的期数Str
			int period = loansInterest.getPeriodNum().intValue() + 1;
			newLoansInterest.setPeriodNum(new BigDecimal(period));
			newLoansInterest.setPeriods("第" + period + "期");
	
			// 开始日期
			Date interestFrom = DateUtil.shareInstance().str2Date(loansInterest.getInterestTo()); 
			Calendar interestF = Calendar.getInstance();
			interestF.setTime(interestFrom);
			interestF.add(Calendar.DATE, 1);
			
			newLoansInterest.setInterestFrom(simpleDateFormat.format(interestF.getTime()));
	
			Date newLoansInterestDatet = DateUtil.shareInstance().str2Date(loansInterest.getInterestTo());
			Calendar newDatet = Calendar.getInstance();
			newDatet.setTime(newLoansInterestDatet);
			newDatet.add(Calendar.MONTH, 1);
			newDatet.set(Calendar.DAY_OF_MONTH, loansInfo.getDiscountDate().intValue());
			
			//处理下次扣息超过了贷款的结束日期
			LoansInfo loan = loansInfoService.queryLoansInfo(loansInterest.getMainid().intValue());
			Date loanDateTo = DateUtil.shareInstance().str2Date(loan.getLoansDateTo());
			String  interestTo = simpleDateFormat.format(newDatet.getTime());
			if(newDatet.getTimeInMillis()-loanDateTo.getTime()>0){
				interestTo = loan.getLoansDateTo();
				System.out.println(interestTo+"<----->"+newDatet);
			}
			
			newLoansInterest.setInterestTo(interestTo);
	
			// 扣息计算的天数,计算第二天的
//			Date newDatef = DateUtil.shareInstance().str2Date(loansInterest.getInterestTo()); 
			int days = (int) ((newDatet.getTimeInMillis() - interestFrom.getTime()) / (1000 * 60 * 60 * 24)-1);
			
			//long转成int出现了负数的情况
			if(days<0){
				days = days*-1;
			}

			newLoansInterest.setInterestDays(new BigDecimal(days));
	
			// 计算出的扣息金额
			BigDecimal rate = loansInfo.getLoanRate().multiply(new BigDecimal(0.01));// 年利率
			BigDecimal amount = rate.divide(new BigDecimal(360), 4, BigDecimal.ROUND_DOWN)
					.multiply(loansInfo.getLoansMoney()).multiply(new BigDecimal(days));
			newLoansInterest.setInterestAmount(amount);
	
			// 扣息状态
			newLoansInterest.setPeriodsStatus("待扣息");
			
			if(loanDateTo.getTime()-new Date().getTime()>0){
				
				loansInterestService.insertLoansInterest(newLoansInterest);
				logger.info("插入明细"+newLoansInterest);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
