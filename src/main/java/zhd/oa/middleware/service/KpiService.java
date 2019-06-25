package zhd.oa.middleware.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public String queryErpKpiByDt(String yyyy, String kpiTypeDt, String kpiEmp, Double erpWeight, Double erpMoney){
		
		String resStr = null;
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			resStr = kpiMapper.checkXSErpKpiByDt(yyyy, kpiTypeDt, kpiEmp, erpWeight, erpMoney);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return resStr;
		
	}
	/**
	 * 判断上传发的数据和Erp的数据是否一致
	 * @param type
	 * @param data
	 * @return
	 */
	public Map<String,String> compareKpi(String type , String data){
		
		String[] datas = data.split("$");
		String msg = "" ; 
		String success = "0" ; 
		List<String> successList = new ArrayList<String>();
		
		for (int i = 0; i < datas.length; i++) {
			
			String[] datasForOne = datas[i].split("|");
//			Double mmAvgStore  = Double.parseDouble(datasForOne[12]);//均库存
			
			try {

				String yyyy = datasForOne[0];//年份
				String kpiTypeDt = datasForOne[1];//月度/季度/年度
				String kpiEmp = datasForOne[2];//姓名
				Double erpWeight = Double.parseDouble(datasForOne[3]);//销量
				Double erpMoney = Double.parseDouble(datasForOne[4]);//吨位
				Double mmAvgStore = Double.parseDouble(datasForOne[12]);//吨位
				
				session = openSession();
				kpiMapper = session.getMapper(KpiMapper.class);
				
				String resStr = null ;
				if("0".equals(type)){
					resStr = kpiMapper.checkXSErpKpiByDt(yyyy, kpiTypeDt, kpiEmp, erpWeight, erpMoney);
				}if("3".equals(type)){
					resStr = kpiMapper.checkCGErpKpiByDt(yyyy, kpiTypeDt, kpiEmp, erpWeight, mmAvgStore);
				}
				
				if(null == resStr){
					successList.add("1");
					msg = msg + kpiEmp + "与Erp匹配不上";
				}if("1".equals(resStr)){
					successList.add("1");
					msg = msg + kpiEmp + "数据与Erp不一致";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{	
				closeSession();	
			}
			
		}
		if(successList.contains("1")){
			success = "1" ;
		}
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("success", success);
		map.put("msg", msg);
		
		return map;
	}
	
	/**
	 * 
	 * 明细表插入数据
	 */
	public void insertAndUpdateWorkflowDt(String remk , String data , String type){
		
		//插入前需要做删除
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			kpiMapper.deleteKpiDt(remk);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		
		//插入
		String[] datas =  data.split("$");
		
		for (int i = 0; i < datas.length; i++) {
			
			String[] kpis = datas[i].split("|");
			
			if("0".equals(type)){
				
			}
			
			
			
			
			
		}
		
		
		
		
		if("0".equals(type)){
			
			
		}
		
		
	}
	/**
	 * 定时任务去插入erp查询的数据，每月2号早上9:00
	 * @param kpiModel
	 */
	public void insertKpiConsult(KpiModel kpiModel){
		
		Double erpWeight = kpiModel.getErpWeight()==null?0:kpiModel.getErpWeight().doubleValue();
		Double erpMoney = kpiModel.getErpMoney()==null?0:kpiModel.getErpMoney().doubleValue();
		Double mmAvgStore = kpiModel.getMmAvgStore()==null?0:kpiModel.getMmAvgStore().doubleValue();
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			System.out.println(kpiModel);
			kpiMapper.insertKpiConsult(kpiModel.getYyyy(),kpiModel.getKpiTypeDt(),kpiModel.getKpiEmp()==null?"":kpiModel.getKpiEmp(),
					erpWeight,erpMoney,mmAvgStore
					);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
	}
	
	

}
