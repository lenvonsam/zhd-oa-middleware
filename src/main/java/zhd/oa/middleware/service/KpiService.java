package zhd.oa.middleware.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhd.oa.middleware.mapper.KpiMapper;
import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.utils.WorkflowUtil;

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
	 * @throws Exception 
	 */
	public Map<String,String> compareKpi(String type , String data , String uid) throws Exception{
		
		String[] datas = data.split("\\$");
		String msg = "" ; 
		String success = "0" ; 
		List<String> successList = new ArrayList<String>();
		System.out.println("datas.length"+datas.length);
		
		for (int i = 0; i < datas.length; i++) {
			String[] datasForOne = datas[i].split("\\|");

			try {

				String yyyy = datasForOne[0];//年份
				String kpiTypeDt = datasForOne[1];//月度/季度/年度
				String kpiEmp = datasForOne[2];//姓名
				
				session = openSession();
				kpiMapper = session.getMapper(KpiMapper.class);
				
				String resStr = null ;
				if("0".equals(type)){
					
					Double erpWeight = Double.parseDouble(datasForOne[3]);//销量
					Double erpMoney = Double.parseDouble(datasForOne[9]);//吨位

					resStr = kpiMapper.checkXSErpKpiByDt(yyyy, kpiTypeDt, kpiEmp, erpWeight, erpMoney);
					System.out.println(yyyy+kpiTypeDt+kpiEmp+erpWeight+erpMoney);
					
				}if("3".equals(type)){
					Double erpWeight = Double.parseDouble(datasForOne[3]);//销量
					Double mmAvgStore = Double.parseDouble(datasForOne[12]);//当月平均库存吨位
					resStr = kpiMapper.checkCGErpKpiByDt(yyyy, kpiTypeDt, kpiEmp, erpWeight, mmAvgStore);
				}
				
				if(null == resStr&&"0".equals(type)){
					successList.add("1");
					msg = msg + kpiEmp + "名字与Erp匹配不上";
				}if("1".equals(resStr)&&"0".equals(type)){
					successList.add("1");
					msg = msg + kpiEmp + "销量或高卖数据与Erp不一致";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{	
				closeSession();	
			}
			
		}
		
		String requestid = "";
		
		if(successList.contains("1")){
			success = "1" ;
		}else{
			
			String workflowId = "";
			
			switch(type){
			case "0":
				workflowId = "5201";//营销中心
			break;
			case "1":
				workflowId = "5224";//型云等公司销中心
			break;
			case "2":
				workflowId = "5223";//营销仓储部门
			break;
			case "3":
				workflowId = "5222";//营销采购部门
			break;
			case "4":
				workflowId = "5221";//营销集团常务副总
			break;
			case "5":
				workflowId = "5226";//营销产品资源中心总监
			break;
			case "6":
				workflowId = "5225";//营销中心副总监销中心
			break;
			
			}
			
			requestid = WorkflowUtil.shareInstance().createKpiWorkflow("286", workflowId, "");
			log.info("requestid==>"+requestid);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("success", success);
		map.put("msg", msg);
		map.put("requestid", requestid);
		
		return map;
	}
	
	/**
	 * 
	 * 明细表插入数据
	 */
	public void insertAndUpdateWorkflowDt(String mainid , String[] kpi , String type){
		
		//插入前需要做删除 -------后面考虑的方案是不走流程申请    所以该步骤不需要了 
//		try {
//			session = openSession();
//			kpiMapper = session.getMapper(KpiMapper.class);
//			kpiMapper.deleteKpiDt(remk);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{	
//			closeSession();	
//		}
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			
			if("0".equals(type)){
				kpiMapper.insertKpiXSDt(mainid, kpi[0], kpi[1], kpi[2],	kpi[3], kpi[4], kpi[5], kpi[6], kpi[7], 
						kpi[8], kpi[9], kpi[10], kpi[11], kpi[12],kpi[13], kpi[14], kpi[15], kpi[16], kpi[17], kpi[18]);
			}if("1".equals(type)){
				kpiMapper.insertKpiXYDt(mainid,  kpi[0],  kpi[1],  kpi[2],  kpi[3],  kpi[4],  kpi[5],  kpi[6],  kpi[7]);
			}if("2".equals(type)){
				kpiMapper.insertKpiCCDt(mainid, kpi[0], kpi[1], kpi[2], kpi[3], kpi[4], kpi[5], kpi[6], kpi[7], kpi[8], kpi[9], kpi[10],kpi[11]);
			}if("3".equals(type)){
				kpiMapper.insertKpiCGDt(mainid, kpi[0], kpi[1], kpi[2], kpi[3], kpi[4], kpi[5], kpi[6], kpi[7], 
						kpi[8], kpi[9], kpi[10], kpi[11], kpi[12],kpi[13], kpi[14], kpi[15], kpi[16], kpi[17], kpi[18],kpi[19]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
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
			kpiMapper.insertKpiConsult(kpiModel.getYyyy(),kpiModel.getKpiTypeDt(),kpiModel.getKpiEmp()==null?"":kpiModel.getKpiEmp(),
					erpWeight,erpMoney,mmAvgStore
					);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
	}
	
	/**
	 * 将数据插入明细表
	 * @param mainid
	 * @param yyyy
	 * @param kpiEmp
	 * @param kpiTypeDt
	 * @param erpWeight
	 * @param erpChangeWeight
	 * @param otherWeight
	 * @param realWeight
	 * @param taskWeight
	 * @param kpiWeight
	 * @param erpMoney
	 * @param erpChangeMoney
	 * @param otherMoney
	 * @param realMoney
	 * @param taskMoney
	 * @param kpiMoney
	 * @param addScore
	 * @param addRemk
	 * @param reduceScore
	 * @param reduceRemk
	 * @return
	 */
	public int insertKpiDt(String mainid,String yyyy,String kpiEmp,String kpiTypeDt,
			String erpWeight,String erpChangeWeight,String otherWeight,String realWeight,String taskWeight,String kpiWeight,
			String erpMoney,String erpChangeMoney,String otherMoney,String realMoney,String taskMoney,String kpiMoney,
			String addScore,String addRemk,String reduceScore,String reduceRemk){
		
		int insertRes = 0;
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			insertRes = kpiMapper.insertKpiXSDt(mainid, yyyy, kpiEmp, kpiTypeDt , erpWeight, erpChangeWeight, otherWeight, realWeight, taskWeight, kpiWeight, erpMoney, erpChangeMoney, otherMoney, realMoney, taskMoney, kpiMoney, addScore, addRemk, reduceScore, reduceRemk);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return insertRes;

	}
 
	public String getMainid(String requestid){
		
		String mainid = "";
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			mainid = kpiMapper.getMainid(requestid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return mainid;
		
	}
	

}
