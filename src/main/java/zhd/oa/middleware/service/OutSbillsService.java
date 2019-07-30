package zhd.oa.middleware.service;

import java.util.ArrayList;
import java.util.List;

import zhd.oa.middleware.mapper.OutSbillsMapper;
import zhd.oa.middleware.model.OutSbills;

public class OutSbillsService extends BaseService{
	
	private OutSbillsMapper outSbillsMapper;
	
	/**
	 * 获取提单
	 * @param sbillcodes
	 * @return
	 */
	public List<OutSbills> getOutSbills(String sbillcodes){
		
		List<OutSbills> outSbillsList = new ArrayList<OutSbills>();
		
		try {
			session = openSession();
			outSbillsMapper = session.getMapper(OutSbillsMapper.class);
			
			String[] sbillcode = sbillcodes.split(",");
			
			for (int i = 0; i < sbillcode.length; i++) {
				List<OutSbills> outSbillsList_ = outSbillsMapper.getOutSbillsBySbillscode(sbillcode[i]); 
				if(null!=outSbillsList_){
					outSbillsList.addAll(outSbillsList_);
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
		return outSbillsList;
	}

	/**
	 * 获取出库单
	 * @param sbillcodes
	 * @return
	 */
	public List<OutSbills> getOutSbillsCode(String sbillcodes){
		
		List<OutSbills> outSbillsList = new ArrayList<OutSbills>();
		
		try {
			session = openSession();
			outSbillsMapper = session.getMapper(OutSbillsMapper.class);
			
			String[] sbillcode = sbillcodes.split(",");
			
			for (int i = 0; i < sbillcode.length; i++) {
				List<OutSbills> outSbillsList_ = outSbillsMapper.getOutSbillsCodeBySbillscode(sbillcode[i]); 
				if(null!=outSbillsList_){
					outSbillsList.addAll(outSbillsList_);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
		return outSbillsList;
	}
	
	
}
