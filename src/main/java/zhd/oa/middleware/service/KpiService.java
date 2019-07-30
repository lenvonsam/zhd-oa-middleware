package zhd.oa.middleware.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.SimpleDate;
import zhd.oa.middleware.mapper.KpiMapper;
import zhd.oa.middleware.model.EmpOA;
import zhd.oa.middleware.model.KpiData;
import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.model.KpiTotalChangeOne;
import zhd.oa.middleware.model.SaleDatas;
import zhd.oa.middleware.model.WageTable;
import zhd.oa.middleware.model.ZhdKpi;
import zhd.oa.middleware.utils.FormaChecktUtil;
import zhd.oa.middleware.utils.WorkflowUtil;

public class KpiService extends BaseService{
	
	KpiMapper kpiMapper = null;
	
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
	 * 定时任务去插入erp查询的数据,当前日期统计的数据，(上个月的数据)每月2号早上9:00
	 * @param kpiModel
	 */
	public void insertKpiConsult(KpiModel kpiModel){
	
		try {
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			kpiMapper.insertKpiConsult(kpiModel.getYyyy(),kpiModel.getKpiTypeDt(),kpiModel.getKpiEmp(),kpiModel.getErpWeight(),kpiModel.getErpMoney(),kpiModel.getMmAvgStore());
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
//			insertRes = kpiMapper.insertKpiXSDt(mainid, yyyy, kpiEmp, kpiTypeDt , erpWeight, erpChangeWeight, otherWeight, realWeight, taskWeight, kpiWeight, erpMoney, erpChangeMoney, otherMoney, realMoney, taskMoney, kpiMoney, addScore, addRemk, reduceScore, reduceRemk);
			
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
	public void insertChangeOne(KpiTotalChangeOne kpiTotalChangeOne){
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		kpiMapper.insertChangeOne(kpiTotalChangeOne.getOneName(),kpiTotalChangeOne.getOneAccount(),kpiTotalChangeOne.getOneMM(),
				kpiTotalChangeOne.getOneWeight(),kpiTotalChangeOne.getOneMoney(),kpiTotalChangeOne.getOneGetDate());
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
	public void insertKpiData(KpiData kpiData){
		try {
		session = openSession();
		kpiMapper = session.getMapper(KpiMapper.class);
		
		kpiMapper.insertKpiData(kpiData.getYyyy(), kpiData.getMm(),kpiData.getHrmid(), 
				kpiData.getLastname(),kpiData.getJobtitle(),kpiData.getWeightrate(),kpiData.getMoneyrate(),kpiData.getMmstr(),kpiData.getJobtitlename(),kpiData.getYyyy()+"", 
				kpiData.getOneweight(), kpiData.getWeightchange(), 0.0, kpiData.getRealweight(), kpiData.getTaskweight(), kpiData.getWeightscore(), 
				kpiData.getOnemoney(), kpiData.getMoneychange(), 0.0, kpiData.getOnemoney()+kpiData.getMoneychange(), kpiData.getTaskmoney(), kpiData.getMoneyscore());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
	}
	
	public String getTypeByUid(String uid){
		
		String datatype = "无";
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			datatype = kpiMapper.getTypeByUid(uid);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return datatype;
	}
	/**
	 * 查询实时的Erp数据,只查当前月份的上个月的
	 * @return
	 */
	public List<SaleDatas> getSaleDatas(){
		
		List<SaleDatas> listSaleDatas = new ArrayList<SaleDatas>();
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			listSaleDatas = kpiMapper.getSaleDatas();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
		return listSaleDatas;
	}
	
	//插入查询出来的数据到onetotal表中
	public void insertSaleDatasIntoOnetotal(SaleDatas saleDatas){
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			kpiMapper.insertSaleDatasIntoOnetotal(saleDatas.getEname(), saleDatas.getEmp(), saleDatas.getBm(), saleDatas.getDname(), 
					saleDatas.getZxl(), saleDatas.getGm(), saleDatas.getYf());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		
	}
	/**
	 * 2019-07-08
	 * 将后台算出来的数据查询出来（当前日期的上个月的），  后面插入到kpitable中去和前端数据对比
	 * @return 
	 */
	public List<ZhdKpi> getSaleKpis(){
		List<ZhdKpi> listZhdKpi = new ArrayList<ZhdKpi>();
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			listZhdKpi = kpiMapper.getSaleKpis();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return listZhdKpi;
	}
	
	/**
	 * 
	 * 2019-07-08
	 * 插入到kpitable中去和前端数据对比
	 * @param zhdKpi
	 */
	public void insertSaleKpisIntoKpicompare(ZhdKpi zhdKpi){
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);

			String  empid = zhdKpi.getEmpid()   ; 
			String  lastname = zhdKpi.getLastname()   ; 
			String  seclevel = zhdKpi.getSeclevel()   ; 
			String  createdate = zhdKpi.getCreatedate()   ; 
			String  departmentid = zhdKpi.getDepartmentid()   ; 
			String  departmentname = zhdKpi.getDepartmentname()   ; 
			String  showorder = zhdKpi.getShoworder()   ; 
			String  jobtitle = zhdKpi.getJobtitle()   ; 
			String  jobtitlename = zhdKpi.getJobtitlename()   ; 
			String  oneaccount = zhdKpi.getOneaccount()   ; 
			String  emperp = zhdKpi.getEmperp()   ; 
			String  onedeptcode = zhdKpi.getOnedeptcode()   ; 
			String  kpitype = zhdKpi.getKpitype()   ; 
			String  yyyy = zhdKpi.getYyyy()   ; 
			String  mm_ = zhdKpi.getMm_()   ; 
			String  mm =  zhdKpi.getMm()  ; 
			String  mmstr = zhdKpi.getMmstr()   ; 
			
			kpiMapper.insertSaleKpisIntoKpicompare(empid==null?"":empid, lastname==null?"":lastname, seclevel==null?"":seclevel, createdate==null?"":createdate, 
					departmentid==null?"":departmentid, departmentname==null?"":departmentname, showorder==null?"":showorder, jobtitle==null?"":jobtitle, 
					jobtitlename==null?"":jobtitlename, oneaccount==null?"":oneaccount, emperp==null?"":emperp, onedeptcode==null?"":onedeptcode, 
					kpitype==null?"":kpitype, yyyy==null?"":yyyy, mm_==null?"":mm_, mm==null?"":mm, mmstr==null?"":mmstr, 
					zhdKpi.getOneweight(), zhdKpi.getWeightchange(), zhdKpi.getOtherweight(), zhdKpi.getRealweight(), zhdKpi.getTaskweight(), zhdKpi.getWeightkpi(), 
					zhdKpi.getOnemoney(), zhdKpi.getMoneychange(), zhdKpi.getOthermoney(), zhdKpi.getRealmoney(), zhdKpi.getTaskmoney(), zhdKpi.getMoneykpi(), 
					zhdKpi.getAvgprice(), zhdKpi.getMmassessprice(), zhdKpi.getMmavgstore(), zhdKpi.getStorerange(), zhdKpi.getAssessweight(), zhdKpi.getKpistore());
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
	}
	/**
	 * 数据对比，2019-07-08
	 * @param uid
	 * @param data
	 * @return
	 */
	public Map<String,String> compareKpi(String uid,String data){
		
		Map<String,String> resMap = new HashMap<String,String>();
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			
			String type = kpiMapper.getTypeByUid(uid);
			
			int checkRes = FormaChecktUtil.shareInstance().checkDatas(data, type);
			
			if(checkRes==0&&null!=uid&&!"1".equals(uid)){
				
				String[] datas = data.split("\\$");
				String msg = "" ; 
				String success = "0" ; 
				String requestid = "";
				
				for (int i = 0; i < datas.length; i++) {
					String[] datasForOne = datas[i].split("\\|");
					
					String yyyy = datasForOne[0];//年份
					String kpiTypeDt = datasForOne[1];//月度/季度/年度
					String kpiEmp = datasForOne[2];//姓名
					
					List<Integer> mm = kpiMapper.getMMdt(kpiTypeDt);
					List<Integer> kpiUid = kpiMapper.getUid(kpiEmp);
					if(!"2019".equals(yyyy)&&!"2020".equals(yyyy)){
						success = "1";
						msg = yyyy + "年份不对";
					}if(mm.size()==0){
						success = "1";
						msg = kpiTypeDt + "该类型未设定";
					}if(kpiUid.size()==0){
						success = "1";
						msg = kpiEmp + "未找到";
					}if("0".equals(type)||"3".equals(type)){
						
						List<ZhdKpi> listZhdKpi = kpiMapper.getZhdKpi(yyyy, kpiTypeDt , kpiEmp);
						log.info(yyyy+"---"+ kpiTypeDt+"---"+ kpiEmp);
						if(listZhdKpi.size()==0){
							success = "1";
							msg = kpiEmp + "数据未找到";
						}if(listZhdKpi.size()>0){
							
							ZhdKpi zhdKpi = listZhdKpi.get(0);
							
							double erpWeight = zhdKpi.getOneweight();//高达销量
							double weightChange = zhdKpi.getWeightchange();//高达销量变化量
							double otherWeight = zhdKpi.getOtherweight();//其他吨位
							double realWeight = zhdKpi.getRealweight();//实际销量
							double taskWeight =zhdKpi.getTaskweight();//销量任务量
							double kpiWeight = zhdKpi.getWeightkpi();//销量得分
							
							double erpMoney = zhdKpi.getOnemoney();//高达高卖
							double moneyChange = zhdKpi.getMoneychange();//高卖变化量
							double otherMoney = zhdKpi.getOthermoney();//应扣费用
							double realMoney = zhdKpi.getRealmoney();//实际高卖
							double taskMoney = zhdKpi.getTaskmoney();//高卖任务量
							double kpiMoney = zhdKpi.getMoneykpi();//高卖得分
							
							double avgPrice = zhdKpi.getAvgprice();//平均价格
							double mmAssessPrice = zhdKpi.getMmassessprice();//考核价格
							double mmAvgStore = zhdKpi.getMmavgstore();//平均库存
							double storeRange = zhdKpi.getStorerange();//库存范围
							double AssessWeight = zhdKpi.getAssessweight();//考核吨位
							double kpiStore = zhdKpi.getKpistore();//库存得分
							
							if("0".equals(type)){
								
								double erpWeight1 = Double.parseDouble(datasForOne[3].equals("")?"0":datasForOne[3]); 
								double weightChange1 = Double.parseDouble(datasForOne[4].equals("")?"0":datasForOne[4]); 
								double otherWeight1 = Double.parseDouble(datasForOne[5].equals("")?"0":datasForOne[5]); 
								double realWeight1 = Double.parseDouble(datasForOne[6].equals("")?"0":datasForOne[6]); 
								double taskWeight1 = Double.parseDouble(datasForOne[7].equals("")?"0":datasForOne[7]); 
								double kpiWeight1 = Double.parseDouble(datasForOne[8].equals("")?"0":datasForOne[8]); 
								double erpMoney1 = Double.parseDouble(datasForOne[9].equals("")?"0":datasForOne[9]); 
								double moneyChange1 = Double.parseDouble(datasForOne[10].equals("")?"0":datasForOne[10]); 
								double otherMoney1 = Double.parseDouble(datasForOne[11].equals("")?"0":datasForOne[11]); 
								double realMoney1 = Double.parseDouble(datasForOne[12].equals("")?"0":datasForOne[12]); 
								double taskMoney1 = Double.parseDouble(datasForOne[13].equals("")?"0":datasForOne[13]); 
								double kpiMoney1 = Double.parseDouble(datasForOne[14].equals("")?"0":datasForOne[14]); 
								
								log.info("moneyChange1==>"+moneyChange1);
								
								if(erpWeight!=erpWeight1){
									msg = msg + kpiEmp + "和高达销量不一致"+erpWeight+",";
									success = "1";
								}if(weightChange!=weightChange1){
									msg = msg + kpiEmp + "和高达销量变化量不一致"+weightChange+",";
									success = "1";
								}if(otherWeight!=otherWeight1){
									msg = msg + kpiEmp + "其他吨位不一致"+otherWeight+",";
									success = "1";
								}if(realWeight!=realWeight1){
									msg = msg + kpiEmp + "实际销量不一致"+realWeight+",";
									success = "1";
								}if(taskWeight!=taskWeight1){
									msg = msg + kpiEmp + "销量任务量不一致"+taskWeight+",";
									success = "1";
								}if(kpiWeight!=kpiWeight1){
									msg = msg + kpiEmp + "销量得分不一致"+kpiWeight+",";
									success = "1";
								}if(erpMoney!=erpMoney1){
									msg = msg + kpiEmp + "高达高卖不一致"+erpMoney+",";
									success = "1";
								}if(moneyChange!=moneyChange1){
									msg = msg + kpiEmp + "高卖变化量不一致"+moneyChange+",";
									success = "1";
								}if(otherMoney!=otherMoney1){
									msg = msg + kpiEmp + "应扣费用不一致"+otherMoney+",";
									success = "1";
								}if(realMoney!=realMoney1){
									msg = msg + kpiEmp + "实际高卖不一致"+realMoney+",";
									success = "1";
								}if(taskMoney!=taskMoney1){
									msg = msg + kpiEmp + "高卖任务量不一致"+taskMoney+",";
									success = "1";
								}if(kpiMoney!=kpiMoney1){
									msg = msg + kpiEmp + "高卖得分不一致"+kpiMoney+",";
									success = "1";
								}
							}if("3".equals(type)){
								
								double erpWeight1 = Double.parseDouble(datasForOne[3].equals("")?"0":datasForOne[3]); 
								double weightChange1 = Double.parseDouble(datasForOne[4].equals("")?"0":datasForOne[4]); 
								double otherWeight1 = Double.parseDouble(datasForOne[5].equals("")?"0":datasForOne[5]); 
								double realWeight1 = Double.parseDouble(datasForOne[6].equals("")?"0":datasForOne[6]); 
								double taskWeight1 = Double.parseDouble(datasForOne[7].equals("")?"0":datasForOne[7]); 
								double kpiWeight1 = Double.parseDouble(datasForOne[8].equals("")?"0":datasForOne[8]); 
								double avgPrice1 = Double.parseDouble(datasForOne[9].equals("")?"0":datasForOne[9]); 
								double mmAssessPrice1 = Double.parseDouble(datasForOne[10].equals("")?"0":datasForOne[10]); 
								double mmAvgStore1 = Double.parseDouble(datasForOne[11].equals("")?"0":datasForOne[11]); 
								double storeRange1 = Double.parseDouble(datasForOne[12].equals("")?"0":datasForOne[12]); 
								double AssessWeight1 = Double.parseDouble(datasForOne[13].equals("")?"0":datasForOne[13]); 
								double kpiStore1 = Double.parseDouble(datasForOne[14].equals("")?"0":datasForOne[14]); 
								
								if(erpWeight!=erpWeight1){
									msg = msg + kpiEmp + "和高达销量不一致"+erpWeight+",";
									success = "1";
								}if(weightChange!=weightChange1){
									msg = msg + kpiEmp + "和高达销量变化量不一致"+weightChange+",";
									success = "1";
								}if(otherWeight!=otherWeight1){
									msg = msg + kpiEmp + "其他吨位不一致"+otherWeight+",";
									success = "1";
								}if(realWeight!=realWeight1){
									msg = msg + kpiEmp + "实际销量不一致"+realWeight+",";
									success = "1";
								}if(taskWeight!=taskWeight1){
									msg = msg + kpiEmp + "销量任务量不一致"+taskWeight+",";
									success = "1";
								}if(kpiWeight!=kpiWeight1){
									msg = msg + kpiEmp + "销量得分不一致"+kpiWeight+",";
									success = "1";
								}if(avgPrice!=avgPrice1){
									msg = msg + kpiEmp + "本月平均价格不一致"+avgPrice+",";
									success = "1";
								}if(mmAssessPrice!=mmAssessPrice1){
									msg = msg + kpiEmp + "本月考核价格不一致"+mmAssessPrice+",";
									success = "1";
								}if(mmAvgStore!=mmAvgStore1){
									msg = msg + kpiEmp + "本月平均库存不一致";
									success = "1";
								}if(storeRange!=storeRange1){
									msg = msg + kpiEmp + "库存范围不一致";
									success = "1";
								}if(AssessWeight!=AssessWeight1){
									msg = msg + kpiEmp + "考核吨位不一致";
									success = "1";
								}if(kpiStore!=kpiStore1){
									msg = msg + kpiEmp + "库存得分不一致";
									success = "1";
								}
							}
						}
					}
				}
				if("0".equals(success)){
					
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
					
					int deptId = kpiMapper.getDeptIdByUid(uid);
					requestid = WorkflowUtil.shareInstance().createKpiWorkflow(uid, workflowId, deptId+"");
					log.info("requestid==>"+requestid);
				}
				
				resMap.put("success", success);
				resMap.put("msg", msg);
				resMap.put("requestid", requestid);
				resMap.put("type", type);
				
			}else{
				resMap.put("success", "1");
				resMap.put("msg", "格式或数据类型不正确，请检查后上传");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
		return resMap;
	}
	
	/**
	 * 数据插入明细表
	 * @param resMap
	 * @param data
	 * @param type
	 */
	public void insertAndUpdateWorkflowDt(Map<String,String> resMap , String data , String type){
		
		try{
			session = openSession();
			kpiMapper = session.getMapper(KpiMapper.class);
			
			String[] datas = data.split("\\$");
			for (int i = 0; i < datas.length; i++) {
				String[] datasForOne = datas[i].split("\\|");
				
				String requestid = resMap.get("requestid");
				String mainid = kpiMapper.getMainid(requestid);
				
				String yyyy_ = datasForOne[0];
				String kpiTypeDt = datasForOne[1];
				String kpiEmp = datasForOne[2];
				
				String uid = kpiMapper.getUid(kpiEmp).get(0)+"";
				String mm = kpiMapper.getMMdt(kpiTypeDt).get(0)+"";
				String job = kpiMapper.getJobId(kpiEmp).get(0)+"";
				
				List<ZhdKpi> listZhdKpi = kpiMapper.getZhdKpi(yyyy_, kpiTypeDt , kpiEmp);
				
				if("0".equals(type)){
					ZhdKpi zhdKpi = listZhdKpi.get(0);
					
					kpiMapper.insertIntoWorkflowDt(mainid, yyyy_, "", uid, job, 
							zhdKpi.getOneweight(), zhdKpi.getWeightchange(), zhdKpi.getOtherweight(), zhdKpi.getRealweight(), zhdKpi.getTaskweight(), zhdKpi.getWeightkpi(), 
							zhdKpi.getOnemoney(), zhdKpi.getMoneychange(), zhdKpi.getOthermoney(), zhdKpi.getRealmoney(), zhdKpi.getTaskmoney(), zhdKpi.getMoneykpi(), 
							0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
							Double.parseDouble(datasForOne[15].equals("")?"0":datasForOne[15]), datasForOne[16], 
							Double.parseDouble(datasForOne[17].equals("")?"0":datasForOne[17]), datasForOne[18], 0.0, 0.0, "", 
							Double.parseDouble(datasForOne[19].equals("")?"0":datasForOne[19]), mm, kpiEmp);
					
				}if("3".equals(type)){
					ZhdKpi zhdKpi = listZhdKpi.get(0);
					
					kpiMapper.insertIntoWorkflowDt(mainid, yyyy_, "", uid, job, 
							zhdKpi.getOneweight(), zhdKpi.getWeightchange(), zhdKpi.getOtherweight(), zhdKpi.getRealweight(), zhdKpi.getTaskweight(), zhdKpi.getWeightkpi(), 
							0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
							zhdKpi.getAvgprice(),0.0,zhdKpi.getMmassessprice(),zhdKpi.getMmavgstore(),zhdKpi.getStorerange(),zhdKpi.getAssessweight(),zhdKpi.getKpistore(),
							Double.parseDouble(datasForOne[15].equals("")?"0":datasForOne[15]), datasForOne[16], 
							Double.parseDouble(datasForOne[17].equals("")?"0":datasForOne[17]), datasForOne[18], 0.0, 0.0, "", 
							Double.parseDouble(datasForOne[19].equals("")?"0":datasForOne[19]), mm, kpiEmp);
					
				}if("1".equals(type)){
					
					kpiMapper.insertIntoWorkflowDt(mainid, yyyy_, "", uid, job, 
							0.0,0.0,0.0,0.0,0.0,0.0,
							0.0,0.0,0.0,0.0,0.0,0.0,
							0.0,0.0,0.0,0.0,0.0,0.0,0.0,
							Double.parseDouble(datasForOne[3].equals("")?"0":datasForOne[3]),datasForOne[4],
							Double.parseDouble(datasForOne[5].equals("")?"0":datasForOne[5]),datasForOne[6],
							0.0,0.0,"",Double.parseDouble(datasForOne[7].equals("")?"0":datasForOne[7]),mm,kpiEmp);
					
				}if("2".equals(type)){
					
					kpiMapper.insertIntoWorkflowDt(mainid, yyyy_, datasForOne[3], uid, job, 
							0.0,0.0,0.0,0.0,0.0,0.0,
							0.0,0.0,0.0,0.0,0.0,0.0,
							0.0,0.0,0.0,0.0,0.0,0.0,0.0,
							Double.parseDouble(datasForOne[4].equals("")?"0":datasForOne[4]),datasForOne[5],
							Double.parseDouble(datasForOne[6].equals("")?"0":datasForOne[6]),datasForOne[7],
							Double.parseDouble(datasForOne[8].equals("")?"0":datasForOne[8]),
							Double.parseDouble(datasForOne[9].equals("")?"0":datasForOne[9]),datasForOne[10],
							Double.parseDouble(datasForOne[11].equals("")?"0":datasForOne[11]),mm,kpiEmp);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{	
			closeSession();	
		}
	}
	/**
	 * 2019-07-12
	 * 提成报表
	 */
	public void getKpidataAndInsertIntoWagetable(){
		
		try{
			session = openSession();
			
			kpiMapper = session.getMapper(KpiMapper.class);
			
			List<WageTable> wageTableList = kpiMapper.getWageTable();
			
			log.info(wageTableList+"");
			
			for (int i = 0; i < wageTableList.size(); i++) {
				WageTable wageTable = wageTableList.get(i);
				
				int  departmentid = wageTable.getDepartmentid();    
				int  yyyy = wageTable.getYyyy();    
				int  mm = wageTable.getMm();    
				int  empid = wageTable.getEmpid();    
				int  jobtitle = wageTable.getJobtitle();    
				String  entrydate = wageTable.getEntrydate();    
				String  fulldate = wageTable.getFulldate();    
				double  fullyear = wageTable.getFullyear();     
				double  realweight = wageTable.getRealweight();    
				double  realmoney = wageTable.getRealmoney();    
				
				String  basicassess = wageTable.getBasicassess(); 
				
				double  wageweight = wageTable.getWageweight();    
				double  wagemoney = wageTable.getWagemoney();    
				String  insertdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				
				kpiMapper.insertIntoZhdWage(departmentid, yyyy, mm, empid, jobtitle, 
						entrydate, fulldate==null?"":fulldate, fullyear, basicassess==null?"":basicassess, 
								realweight, realmoney, wageweight, wagemoney, 0.0, 0.0, insertdate);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeSession();
		}
		
	}
	
	

}
