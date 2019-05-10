package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.LoansInterestMapper;
import zhd.oa.middleware.model.LoansInterest;

public class LoansInterestService extends BaseService{
	
	
	private LoansInterestMapper loansInterestMapper = null;
	
	/**
	 * 根据流程编号查询利息明细
	 * @param flowNo
	 * @return
	 */
	public List<LoansInterest> queryLoansInterest(int requestid) {
		
		List<LoansInterest> loansInterest = null; 
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);
			loansInterest = loansInterestMapper.queryLoansInterest(requestid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}			
		return loansInterest;
	
	}

	/**
	 * 插入明细
	 * @param loansInterest
	 * @return
	 */
	public String insertLoansInterest(LoansInterest loansInterest){
		
		String msg = "Failed";
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);

			int res = loansInterestMapper.insertLoansInterest(loansInterest.getMainid().intValue(),loansInterest.getPeriodNum().intValue(),
					loansInterest.getPeriods(),loansInterest.getInterestFrom(),
					loansInterest.getInterestTo(),loansInterest.getInterestDays().intValue(),
					loansInterest.getInterestAmount().doubleValue(),loansInterest.getPeriodsStatus());
		
			if(res==1){
				msg = "SUCCESS";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return msg;
	}
	/**
	 * 查询扣息byMainid
	 * @param mainid
	 * @return
	 */
	public List<LoansInterest> queryLoansInterestByMainid(int mainid){

		List<LoansInterest> loansInterest = null; 
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);
			loansInterest = loansInterestMapper.queryLoansInterestByMainid(mainid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}			
		return loansInterest;
	}
	
	public LoansInterest queryLoanInterestByPeriod(String period,int mainid){
		
		LoansInterest loansInterest = null;
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);
			loansInterest = loansInterestMapper.queryLoanInterestByPeriod(period,mainid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return loansInterest;
	}
	
	public String updateLoansInterestStautsByPeriod(String periods){
		
		String msg = "Failed";
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);

			int res = loansInterestMapper.updateLoansInterestStautsByPeriod(periods);
		
			if(res==1){
				msg = "SUCCESS";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return msg;
	}
	
	public List<LoansInterest> queryLastLoansInterest(){
		
		List<LoansInterest> loansInterests = null;
		
		try {
			session = openSession();
			loansInterestMapper = session.getMapper(LoansInterestMapper.class);
			loansInterests = loansInterestMapper.queryLastLoansInterest();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return loansInterests;
		
	}
	
}
