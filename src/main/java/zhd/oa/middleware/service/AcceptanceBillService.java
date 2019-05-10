package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.AcceptanceBillMapper;
import zhd.oa.middleware.model.AcceptanceBill;

public class AcceptanceBillService extends BaseService{

	private AcceptanceBillMapper acceptanceBillMapper;
	
	public List<AcceptanceBill> queryAcceptanceBill(){
		
		List<AcceptanceBill>  acceptanceBills = null;
		
		try {
			session = openSession();
			acceptanceBillMapper = session.getMapper(AcceptanceBillMapper.class);
			acceptanceBills = acceptanceBillMapper.queryAcceptanceBill();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		
		return acceptanceBills;

	}
	
	
}
