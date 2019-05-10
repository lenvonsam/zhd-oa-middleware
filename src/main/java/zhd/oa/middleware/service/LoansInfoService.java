package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.LoansInfoMapper;
import zhd.oa.middleware.model.LoansInfo;
/**
 * 贷款服务
 * @author conqueror
 *
 */
public class LoansInfoService extends BaseService{

	private LoansInfoMapper loansInfoMapper = null;
	
	public List<LoansInfo> queryLoansInfo(){
		
		List<LoansInfo> loansInfos = null; 
		
		try {
			session = openSession();
			loansInfoMapper = session.getMapper(LoansInfoMapper.class);
			loansInfos = loansInfoMapper.queryLoansInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return loansInfos;
	
	}
	
	public LoansInfo queryLoansInfo(int mainid){
		
		LoansInfo loansInfo = null; 
		
		try {
			session = openSession();
			loansInfoMapper = session.getMapper(LoansInfoMapper.class);
			loansInfo = loansInfoMapper.queryLoansInfoById(mainid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return loansInfo;
	}
	
	public List<LoansInfo> queryFirstLoansInfo(){
		
		List<LoansInfo> loansInfos = null; 
		
		try {
			session = openSession();
			loansInfoMapper = session.getMapper(LoansInfoMapper.class);
			loansInfos = loansInfoMapper.queryFirstLoansInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return loansInfos;
	}
	
	public String queryLoansInfoRequestid(int mainid){
		
		String requestid = "";
		
		try {
			session = openSession();
			loansInfoMapper = session.getMapper(LoansInfoMapper.class);
			requestid = loansInfoMapper.queryLoansInfoRequestid(mainid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}	
		return requestid;
	
	}
	
}
