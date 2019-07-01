package zhd.oa.middleware.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zhd.oa.middleware.mapper.KpiMapper;
import zhd.oa.middleware.model.EmpOA;
import zhd.oa.middleware.model.Kpi;
import zhd.oa.middleware.model.KpiData;
import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.model.KpiTotalChangeOne;
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
	public Map<String,String> compareKpi(String type , String data , String uid,String dept) throws Exception{
		
		String[] datas = data.split("\\$");
		String msg = "" ; 
		String success = "0" ; 
		
		
		for (int i = 0; i < datas.length; i++) {
			String[] datasForOne = datas[i].split("\\|");
			
			try{
				session = openSession();
				kpiMapper = session.getMapper(KpiMapper.class);
				
				String yyyy = datasForOne[0];//年份
				String kpiTypeDt = datasForOne[1];//月度/季度/年度
				String kpiEmp = datasForOne[2];//姓名
				
				int empIsOk = kpiMapper.checkLastname(kpiEmp);
				if(empIsOk==0){
					msg = msg + kpiEmp + "不存在";
					success = "1";
					log.info(msg);
				}
				
				if("0".equals(type)||"3".equals(type)){
					
				
				List<Kpi> listKpis = kpiMapper.getKpi(yyyy, kpiTypeDt, kpiEmp);
				
				if(listKpis.size()==0){
					msg = msg + "未查询到"+kpiEmp+yyyy+kpiTypeDt+"数据！";
					success = "1";
				}
				if(listKpis.size()>0){
					Kpi kpi = listKpis.get(0);
					
					log.info(kpi.toString());
					
					String erpWeight = kpi.getErpWeight()==null||kpi.getErpWeight().startsWith(".")?"":kpi.getErpWeight();//高达销量
					String weightChange = kpi.getErpChangeWeight()==null||kpi.getErpChangeWeight().startsWith(".")?"":kpi.getErpChangeWeight();//高达销量变化量
					String otherWeight = kpi.getOtherWeight()==null||kpi.getOtherWeight().startsWith(".")?"":kpi.getOtherWeight();//其他吨位
					String realWeight = kpi.getRealWeight()==null||kpi.getRealWeight().startsWith(".")?"":kpi.getRealWeight();//实际销量
					String taskWeight =kpi.getTaskWeight()==null||kpi.getTaskWeight().startsWith(".")?"":kpi.getTaskWeight();//销量任务量
					String kpiWeight = kpi.getKpiWeight()==null||kpi.getKpiWeight().startsWith(".")?"":kpi.getKpiWeight();//销量得分
					String erpMoney = kpi.getErpMoney()==null||kpi.getErpMoney().startsWith(".")?"":kpi.getErpMoney();//高达高卖
					String moneyChange = kpi.getErpChangeMoney()==null||kpi.getErpChangeMoney().startsWith(".")?"":kpi.getErpChangeMoney();//高卖变化量
					String otherMoney = kpi.getOtherMoney()==null||kpi.getOtherMoney().startsWith(".")?"":kpi.getOtherMoney();//应扣费用
					String realMoney = kpi.getRealMoney()==null||kpi.getRealMoney().startsWith(".")?"":kpi.getRealMoney();//实际高卖
					String taskMoney = kpi.getTaskMoney()==null||kpi.getTaskMoney().startsWith(".")?"":kpi.getTaskMoney();//高卖任务量
					String kpiMoney = kpi.getKpiMoney()==null||kpi.getKpiMoney().startsWith(".")?"":kpi.getKpiMoney();//高卖得分
					
					String avgPrice = kpi.getAvgPrice()==null||kpi.getAvgPrice().startsWith(".")?"":kpi.getAvgPrice();//高卖得分
					String mmAssessPrice = kpi.getMmAssessPrice()==null||kpi.getMmAssessPrice().startsWith(".")?"":kpi.getMmAssessPrice();//高卖得分
					String mmAvgStore = kpi.getMmAvgStore()==null||kpi.getMmAvgStore().startsWith(".")?"":kpi.getMmAvgStore();//高卖得分
					String storeRange = kpi.getStoreRange()==null||kpi.getStoreRange().startsWith(".")?"":kpi.getStoreRange();//高卖得分
					String AssessWeight = kpi.getAssessWeight()==null||kpi.getAssessWeight().startsWith(".")?"":kpi.getAssessWeight();//高卖得分
					String kpiStore = kpi.getKpiStore()==null||kpi.getKpiStore().startsWith(".")?"":kpi.getKpiStore();//高卖得分
					
					
					if("0".equals(type)){
						if(!erpWeight.equals(datasForOne[3])){
							msg = msg + kpiEmp + "和高达销量不一致"+erpWeight+",";
							success = "1";
						}if(!weightChange.equals(datasForOne[4])){
							msg = msg + kpiEmp + "和高达销量变化量不一致"+weightChange+",";
							success = "1";
						}if(!otherWeight.equals(datasForOne[5])){
							msg = msg + kpiEmp + "其他吨位不一致"+otherWeight+",";
							success = "1";
						}if(!realWeight.equals(datasForOne[6])){
							msg = msg + kpiEmp + "实际销量不一致"+realWeight+",";
							success = "1";
						}if(!taskWeight.equals(datasForOne[7])){
							msg = msg + kpiEmp + "销量任务量不一致"+taskWeight+",";
							success = "1";
						}if(!kpiWeight.equals(datasForOne[8])){
							msg = msg + kpiEmp + "销量得分不一致"+kpiWeight+",";
							success = "1";
						}if(!erpMoney.equals(datasForOne[9])){
							msg = msg + kpiEmp + "高达高卖不一致"+erpMoney+",";
							success = "1";
						}if(!moneyChange.equals(datasForOne[10])){
							msg = msg + kpiEmp + "高卖变化量不一致"+moneyChange+",";
							success = "1";
						}if(!otherMoney.equals(datasForOne[11])){
							msg = msg + kpiEmp + "应扣费用不一致"+otherMoney+",";
							success = "1";
						}if(!realMoney.equals(datasForOne[12])){
							msg = msg + kpiEmp + "实际高卖不一致"+realMoney+",";
							success = "1";
						}if(!taskMoney.equals(datasForOne[13])){
							msg = msg + kpiEmp + "高卖任务量不一致"+taskMoney+",";
							success = "1";
						}if(!kpiMoney.equals(datasForOne[14])){
							msg = msg + kpiEmp + "高卖得分不一致"+kpiMoney+",";
							success = "1";
						}
					}if("3".equals(type)){
						if(!erpWeight.equals(datasForOne[3])){
							msg = msg + kpiEmp + "和高达销量不一致"+erpWeight+",";
							success = "1";
						}if(!weightChange.equals(datasForOne[4])){
							msg = msg + kpiEmp + "和高达销量变化量不一致"+weightChange+",";
							success = "1";
						}if(!otherWeight.equals(datasForOne[5])){
							msg = msg + kpiEmp + "其他吨位不一致"+otherWeight+",";
							success = "1";
						}if(!realWeight.equals(datasForOne[6])){
							msg = msg + kpiEmp + "实际销量不一致"+realWeight+",";
							success = "1";
						}if(!taskWeight.equals(datasForOne[7])){
							msg = msg + kpiEmp + "销量任务量不一致"+taskWeight+",";
							success = "1";
						}if(!kpiWeight.equals(datasForOne[8])){
							msg = msg + kpiEmp + "销量得分不一致"+kpiWeight+",";
							success = "1";
						}if(!avgPrice.equals(datasForOne[9])){
							msg = msg + kpiEmp + "本月平均价格不一致"+avgPrice+",";
							success = "1";
						}if(!mmAssessPrice.equals(datasForOne[10])){
							msg = msg + kpiEmp + "本月考核价格不一致"+mmAssessPrice+",";
							success = "1";
						}if(!mmAvgStore.equals(datasForOne[11])){
							msg = msg + kpiEmp + "本月平均库存不一致";
							success = "1";
						}if(!storeRange.equals(datasForOne[12])){
							msg = msg + kpiEmp + "库存范围不一致";
							success = "1";
						}if(!AssessWeight.equals(datasForOne[13])){
							msg = msg + kpiEmp + "考核吨位不一致";
							success = "1";
						}if(!kpiStore.equals(datasForOne[14])){
							msg = msg + kpiEmp + "库存得分不一致";
							success = "1";
						}
					}
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{	
				closeSession();	
			}
			
		}
		
		String requestid = "";
		
		if("1".equals(success)){
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
			requestid = WorkflowUtil.shareInstance().createKpiWorkflow(uid, workflowId, dept);
			log.info("requestid==>"+requestid);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("success", success);
		map.put("requestid", requestid);
		map.put("msg", msg);
		log.info("map==>"+map);
		
		return map;
	}
	
	/**
	 * 
	 * 明细表插入数据,按每个人 的数据单个写入
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
		System.out.println("kpi.length===>"+kpi.length);
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			
			if("0".equals(type)){
				kpiMapper.insertKpiXSDt(mainid, kpi[0], kpi[1], kpi[2],	kpi[3], kpi[4], kpi[5], kpi[6], kpi[7], 
						kpi[8], kpi[9], kpi[10], kpi[11], kpi[12],kpi[13], kpi[14], kpi[15], kpi[16], kpi[17], kpi[18]);
			} if("1".equals(type)){
				kpiMapper.insertKpiXYDt(mainid,  kpi[0], kpi[1], kpi[2], kpi[3],  kpi[4],  kpi[5],  kpi[6],  kpi[7]);
			} if("2".equals(type)){
				kpiMapper.insertKpiCCDt(mainid, kpi[0], kpi[1], kpi[2], kpi[3], kpi[4], kpi[5], kpi[6], kpi[7], kpi[8], kpi[9], kpi[10],kpi[11]);
			} if("3".equals(type)){
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
	
	public int getDeptIdByUid(String uid){
		
		int dept = 0;
		
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			dept = kpiMapper.getDeptIdByUid(uid);

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return dept;
		
	}
	
	
	/**
	 * 查询人员List做转换
	 * @return
	 */
	public List<EmpOA> getEmpsOA(String lastname){
		List<EmpOA> list = new ArrayList<EmpOA>();
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		list = kpiMapper.queryEmp(lastname);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return list;
		
	}
	
	/**
	 * 查询总销量
	 * @return
	 */
	public List<KpiTotalChangeOne> queryChangeOne(){
		List<KpiTotalChangeOne> list = new ArrayList<KpiTotalChangeOne>();
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		list = kpiMapper.queryChangeOne();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return list;
		
	} 
	/**
	 * 写入销量合计 ，计算变化量
	 * @param oneName
	 * @param oneAccount
	 * @param oneMM
	 * @param oneWeight
	 * @param oneMoney
	 * @param oneGetDate
	 */
	public void insertChangeOne(String oneName,String oneAccount,String oneMM,Double oneWeight,Double oneMoney,String oneGetDate){
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		kpiMapper.insertChangeOne(oneName,oneAccount,oneMM, oneWeight, oneMoney,oneGetDate);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
	}  
	/**
	 * 获取营销中心绩效
	 * @return
	 */
	public List<KpiData> getKpiData(){
		
		List<KpiData> list = new ArrayList<KpiData>();
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		list = kpiMapper.getKpiData();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return list;
	}
	
	/**
	 * 将计算或得出的结果插入报表中
	 * @param kpiTypeDt
	 * @param kpiEmp
	 * @param erpWeight
	 * @param changeWeight
	 * @param erpMoney
	 * @param changeMoney
	 */
	public void insertKpiData(String kpiTypeDt,String kpiEmp,Double erpWeight,Double changeWeight,Double erpMoney,Double changeMoney){
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String yyyy = sdf.format(new Date());
		List<EmpOA> list = kpiMapper.queryEmp(kpiEmp);
		kpiMapper.insertKpiData(yyyy,kpiTypeDt, list.get(0).getUid()+"", erpWeight+"", changeWeight+"", erpMoney+"", changeMoney+"",kpiEmp);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
	}
	

}
