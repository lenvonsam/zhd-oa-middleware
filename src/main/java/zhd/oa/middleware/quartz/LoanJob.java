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

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 按最后一期做判断
		try {
			System.out.println("LoanJob start1");
			
			checkFirstInterest();
			checkLoanInterest();
			
			List<LoansInterest> loansInterestList = loansInterestService.queryLastLoansInterest();

			for (int i = 0; i < loansInterestList.size(); i++) {

				String interestTo = loansInterestList.get(i).getInterestTo();
				
				Date datet = DateUtil.shareInstance().str2Date(interestTo);
				
				int lastDays = (int)(datet.getTime()-new Date().getTime())/(1000*60*60*24);

				String status = loansInterestList.get(i).getPeriodsStatus();

				if (lastDays <= 5 && "待扣息".equals(status)) {

					List<String> requestids = WorkflowUtil.shareInstance().getToDoList(124, null);

					String requestid = loansInfoService
							.queryLoansInfoRequestid(loansInterestList.get(i).getMainid().intValue());

					if (requestids.contains(requestid)) {

						String res = WorkflowUtil.shareInstance().operateRequest(Integer.parseInt(requestid), 124,
								"submit");
						System.out.println("是时候提交了" + res + loansInterestList.get(i));
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
			System.out.println("LoanJob start2");
			List<LoansInfo> loansInfos = loansInfoService.queryFirstLoansInfo();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (loansInfos.size() > 0) {
				for (int i = 0; i < loansInfos.size(); i++) {
					System.out.println("需要插入明细的-->" + loansInfos.get(i));
					String loansDateFrom = loansInfos.get(i).getLoansDateFrom();
					
					Date loansDateF = DateUtil.shareInstance().str2Date(loansDateFrom);
					
					Calendar calendarT = Calendar.getInstance();
					calendarT.setTime(loansDateF);
					calendarT.set(Calendar.DAY_OF_MONTH, loansInfos.get(i).getDiscountDate().intValue());

					int days = (int)(new Date().getTime()-calendarT.getTime().getTime())/(1000*60*60*24);

					if (days >= 0) {
						calendarT.add(Calendar.MONTH, 1);
					}
					if (calendarT.getTime().getTime() == loansDateF.getTime()) {
						calendarT.add(Calendar.MONTH, 1);
					}

					int interestDays = (int) ((calendarT.getTimeInMillis() - loansDateF.getTime())
							/ (1000 * 60 * 60 * 24));

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

					System.out.println("插入首次扣息：" + firstLoansInterest);

					loansInterestService.insertLoansInterest(firstLoansInterest);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkLoanInterest() throws ParseException {

		try{
			System.out.println("LoanJob start3");
			List<LoansInterest> loansInterestList = loansInterestService.queryLastLoansInterest();
	
			System.out.println("贷款有[" + loansInterestList.size() + "]条记录需要继续！");
	
			if (loansInterestList.size() > 0) {
				for (int i = 0; i < loansInterestList.size(); i++) {
	
					String interestTo = loansInterestList.get(i).getInterestTo();
	
					Date dateTo = DateUtil.shareInstance().str2Date(interestTo);
					
					int dateToMonth = dateTo.getMonth() + 1;
					int dateToYear = dateTo.getYear() + 1;
	
					int currentMonth = new Date().getMonth() + 1;
					int currentYear = new Date().getYear() + 1;

					if (dateToMonth < currentMonth && dateToYear <= currentYear) {
						// 扣息明细月份小于当前日期月份的需要添加明细
						newInterest(loansInterestList.get(i));
						System.out.println(loansInterestList.get(i));
						System.out.println("生成下个月的应扣数据。。。");
					} else {
						System.out.println(loansInterestList.get(i));
						System.out.println("时间未到未生成明细");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 创建明细数据并插入(非首次情况下)
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
			newLoansInterest.setInterestFrom(loansInterest.getInterestTo());
	
			Date newLoansInterestDatet = DateUtil.shareInstance().str2Date(loansInterest.getInterestTo());
			Calendar newDatet = Calendar.getInstance();
			newDatet.setTime(newLoansInterestDatet);
			newDatet.add(Calendar.MONTH, 1);
			newDatet.set(Calendar.DAY_OF_MONTH, loansInfo.getDiscountDate().intValue());
			newLoansInterest.setInterestTo(simpleDateFormat.format(newDatet.getTime()));
	
			// 扣息计算的天数
			Date newDatef = DateUtil.shareInstance().str2Date(loansInterest.getInterestTo()); 
			int days = (int) ((newDatet.getTimeInMillis() - newDatef.getTime()) / (1000 * 60 * 60 * 24));
			newLoansInterest.setInterestDays(new BigDecimal(days));
	
			// 计算出的扣息金额
			BigDecimal rate = loansInfo.getLoanRate().multiply(new BigDecimal(0.01));// 年利率
			BigDecimal amount = rate.divide(new BigDecimal(360), 4, BigDecimal.ROUND_DOWN)
					.multiply(loansInfo.getLoansMoney()).multiply(new BigDecimal(days));
			newLoansInterest.setInterestAmount(amount);
	
			// 扣息状态
			newLoansInterest.setPeriodsStatus("待扣息");
	
			System.out.println(newLoansInterest);
	
			loansInterestService.insertLoansInterest(newLoansInterest);
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
