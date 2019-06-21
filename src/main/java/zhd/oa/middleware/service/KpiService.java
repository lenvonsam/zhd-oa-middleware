package zhd.oa.middleware.service;

import java.util.ArrayList;
import java.util.List;

import zhd.oa.middleware.mapper.KpiMapper;
import zhd.oa.middleware.model.KpiModel;

public class KpiService extends BaseService{
	
	private KpiMapper kpiMapper = null;
	
	public List<KpiModel> queryKpi(){
		
		List<KpiModel> kpis = new ArrayList<KpiModel>();
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			kpis = kpiMapper.queryErpKpi();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return kpis;
	}
	

}
