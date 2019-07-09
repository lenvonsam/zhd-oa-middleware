package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.EmpOA;
import zhd.oa.middleware.model.Kpi;
import zhd.oa.middleware.model.KpiData;
import zhd.oa.middleware.model.KpiModel;
import zhd.oa.middleware.model.KpiTotalChange;
import zhd.oa.middleware.model.KpiTotalChangeOne;
import zhd.oa.middleware.model.SaleDatas;
import zhd.oa.middleware.model.ZhdKpi;

public interface KpiMapper {
	
	/**
	 * 查询erp上的销量和高卖
	 * @return
	 */
	@Select("select to_char(sysDate,'yyyy') yyyy, substr(YF,6,8) kpiTypeDt,employee_name  kpiEmp, emp empCode,bm deptCode, "
			+ " ZXL erpWeight, GM erpMoney from V_ZHDERPKPI "
			+ " where YF = to_char(add_months(trunc(sysdate),-1),'yyyy-MM') ")
	public List<KpiModel> queryErpKpi();
	
	/**
	 * 每月初2号查询系统的销量 ，将当前需要统计的时间的 一月到当前上个月的数据查询出来 。以插入统计表中 ，以计算变化量
	 * @return
	 */
	@Select("select case when emp_name is null then employee_name else emp_name end oneName,"
			+ "EMPLOYEE_NAME oneAccount,yf oneMM,sum(zxl) oneWeight,sum(gm) oneMoney ,to_char(sysdate,'yyyy-MM-dd') oneGetDate  "
			+ "from V_ZHDERPKPI where yf<to_char(trunc(sysdate,'MM'),'yyyy-MM')group by emp_name,EMPLOYEE_NAME,yf"
			)
	public List<KpiTotalChangeOne> queryChangeOne();
	
	/**
	 * 
	 * 写入销量合计数据，将每个月统计的数据插入到uf_oneTotal中 ， 以统计变化量 ，计算变化量
	 * @param oneName
	 * @param oneWeight
	 * @param oneMoney
	 * @return
	 */
	@Insert("insert into uf_oneTotal ( oneName,oneAccount,oneMM,oneWeight,oneMoney,oneGetDate )  values ("
			+ " #{oneName},#{oneAccount},#{oneMM},#{oneWeight},#{oneMoney},#{oneGetDate} )  ")
	public int insertChangeOne(@Param ("oneName")String oneName,@Param ("oneAccount")String oneAccount,
			@Param ("oneMM")String oneMM,@Param ("oneWeight")Double oneWeight,
			@Param ("oneMoney")Double oneMoney,@Param ("oneGetDate")String oneGetDate);
	
	/**
	 * 对比下载下来的营销数据
	 * @param yyyy
	 * @param kpiTypeDt
	 * @param kpiEmp
	 * @param erpWeight
	 * @param erpMoney
	 * @return
	 */
	@Select("select "
			+ "case when ERPWEIGHT <> #{erpWeight} or ERPMONEY <> #{erpMoney} then 1  "
			+ " else 0 end res "
			+ "from  uf_zhdKpiConsult "
			+ "where YYYY = #{yyyy} and KPITYPEDT = #{kpiTypeDt} and KPIEMP = #{kpiEmp} ")
	public String checkXSErpKpiByDt(@Param ("yyyy")String yyyy,@Param ("kpiTypeDt")String kpiTypeDt,@Param ("kpiEmp")String kpiEmp,@Param ("erpWeight")Double erpWeight,@Param ("erpMoney")Double erpMoney);
	
	/**
	 * 对比下载下来的采购数据
	 * @param yyyy
	 * @param kpiTypeDt
	 * @param kpiEmp
	 * @param erpWeight
	 * @param mmAvgStore
	 * @return
	 */
	@Select("select "
			+ "case when ERPWEIGHT <> #{erpWeight} or mmAvgStore <> #{mmAvgStore} then 1  "
			+ " else 0 end res"
			+ "from  uf_zhdKpiConsult "
			+ "where YYYY = #{yyyy} and KPITYPEDT = #{kpiTypeDt} and KPIEMP = #{kpiEmp}")
	public String checkCGErpKpiByDt(@Param ("yyyy") String yyyy,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiEmp") String kpiEmp,@Param ("erpWeight") Double erpWeight,@Param ("mmAvgStore") Double mmAvgStore);
	
	/**
	 * 清空流程明细里的数据
	 * @param remk
	 * @return
	 */
	@Delete("delete from FORMTABLE_MAIN_451_dt1 where mainid = #{remk} ")
	public int deleteKpiDt(String remk);
	
	/**
	 * 写入销售绩效流程明细
	 * @param remk
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
	@Insert("insert into FORMTABLE_MAIN_451_dt1 ( mainid,yyyy,kpiTypeDt,kpiEmp, "
			+ " erpWeight,erpChangeWeight,otherWeight,realWeight,taskWeight,kpiWeight, "
			+ " erpMoney,erpChangeMoney,otherMoney,realMoney,taskMoney,kpiMoney, "
			+ " addScore,addRemk,reduceScore,reduceRemk ) values "
			+ " ( #{mainid},#{yyyy},#{kpiTypeDt},#{kpiEmp},#{erpWeight},#{erpChangeWeight},#{otherWeight},#{realWeight},#{taskWeight},#{kpiWeight}, "
			+ " #{erpMoney} ,#{erpChangeMoney},#{otherMoney},#{realMoney},#{taskMoney},#{kpiMoney},"
			+ " #{addScore},#{addRemk},#{reduceScore},#{reduceRemk} ) ")
	public int insertKpiXSDt(@Param ("mainid") int mainid,@Param ("yyyy") int yyyy,@Param ("kpiTypeDt") int kpiTypeDt,@Param ("kpiEmp") int kpiEmp,
			@Param ("erpWeight") double erpWeight,@Param ("erpChangeWeight") double erpChangeWeight,@Param ("otherWeight") double otherWeight,@Param ("realWeight") double realWeight,@Param ("taskWeight") double taskWeight,@Param ("kpiWeight") double kpiWeight,
			@Param ("erpMoney") double erpMoney,@Param ("erpChangeMoney") double erpChangeMoney,@Param ("otherMoney") double otherMoney,@Param ("realMoney") double realMoney,@Param ("taskMoney") double taskMoney,@Param ("kpiMoney") double kpiMoney,
			@Param ("addScore") String addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") String reduceScore,@Param ("reduceRemk") String reduceRemk			);
	
	/**
	 * 写入采购数据明细
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
	 * @param avgPrice
	 * @param mmAssessPrice
	 * @param mmAvgStore
	 * @param storeRange
	 * @param AssessWeight
	 * @param kpiStore
	 * @param addScore
	 * @param addRemk
	 * @param reduceScore
	 * @param reduceRemk
	 * @param kpitotal
	 * @return
	 */
	@Insert("insert into FORMTABLE_MAIN_451_dt1 ( mainid,yyyy,kpiEmp,kpiTypeDt, "
			+ " erpWeight,erpChangeWeight,otherWeight,realWeight,taskWeight,kpiWeight, "
			+ " avgPrice,mmAssessPrice,mmAvgStore,storeRange,AssessWeight,kpiStore, "
			+ " addScore,addRemk,reduceScore,reduceRemk,kpitotal ) values "
			+ " (#{avgPrice},#{yyyy},#{kpiEmp},#{kpiTypeDt},#{erpWeight},#{erpChangeWeight},#{otherWeight},#{realWeight},#{taskWeight},#{kpiWeight}, "
			+ "  #{avgPrice},#{mmAssessPrice},#{mmAvgStore},#{storeRange},#{AssessWeight},#{kpiStore},#{addScore},#{addRemk},#{reduceScore},#{reduceRemk},#{kpitotal}) ")
	public int insertKpiCGDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiEmp") String kpiEmp,@Param ("kpiTypeDt") String kpiTypeDt,
			@Param ("erpWeight") String erpWeight,@Param ("erpChangeWeight") String erpChangeWeight,@Param ("otherWeight") String otherWeight,@Param ("realWeight") String realWeight,@Param ("taskWeight") String taskWeight,@Param ("kpiWeight") String kpiWeight,
			@Param ("avgPrice") String avgPrice,@Param ("mmAssessPrice") String mmAssessPrice,@Param ("mmAvgStore") String mmAvgStore,@Param ("storeRange") String storeRange,@Param ("AssessWeight") String AssessWeight,@Param ("kpiStore") String kpiStore,
			@Param ("addScore") String addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") String reduceScore,@Param ("reduceRemk") String reduceRemk,@Param ("kpitotal") String kpitotal);
	
	/**
	 * 写入仓储数据
	 * @param mainid
	 * @param yyyy
	 * @param kpiEmp
	 * @param kpiTypeDt
	 * @param addScore
	 * @param addRemk
	 * @param reduceScore
	 * @param reduceRemk
	 * @param basicScore
	 * @param otherScore
	 * @param otherRemk
	 * @param kpitotal
	 * @return
	 */
	@Insert("insert into FORMTABLE_MAIN_451_dt1 (mainid,yyyy,kpiTypeDt,kpiEmp,kpiArea, "
			+ " addScore,addRemk,reduceScore,reduceRemk,"
			+ " basicScore,otherScore,otherRemk,kpitotal ) values "
			+ " ( #{mainid},#{yyyy},#{kpiTypeDt},#{kpiEmp},#{kpiArea},#{addScore},#{addRemk},#{reduceScore},#{reduceRemk},"
			+ " #{basicScore},#{otherScore},#{otherRemk},#{kpitotal} ) ")
	public int insertKpiCCDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiEmp") String kpiEmp,@Param ("kpiArea") String kpiArea,
			@Param ("addScore") String addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") String reduceScore,@Param ("reduceRemk") String reduceRemk,
			@Param ("basicScore") String basicScore,@Param ("otherScore") String otherScore,@Param ("otherRemk") String otherRemk,@Param ("kpitotal") String kpitotal);
	
	/**
	 * 写入型云数据
	 * @param mainid
	 * @param yyyy
	 * @param kpiEmp
	 * @param kpiTypeDt
	 * @param addScore
	 * @param addRemk
	 * @param reduceScore
	 * @param reduceRemk
	 * @param kpitotal
	 * @return
	 */
	@Insert("insert into FORMTABLE_MAIN_451_dt1 ( mainid,yyyy,kpiTypeDt,kpiEmp, "
			+ " addScore,addRemk,reduceScore,reduceRemk,kpitotal ) values "
			+ " (#{mainid},#{yyyy},#{kpiTypeDt},#{kpiEmp},#{addScore},#{addRemk},#{reduceScore},#{reduceRemk},#{kpitotal})  ")
	public int insertKpiXYDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiEmp") String kpiEmp,
			@Param ("addScore") String addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") String reduceScore,@Param ("reduceRemk") String reduceRemk,@Param ("kpitotal") String kpitotal);
	
	
	/**
	 * 插入临时的绩效数据（数据对比表）
	 * @param yyyy
	 * @param kpiTypeDt
	 * @param kpiEmp
	 * @param erpWeight
	 * @param erpMoney
	 * @param mmAvgStore
	 * @return
	 */
	@Insert("insert into uf_zhdKpiConsult (yyyy,kpiTypeDt,kpiEmp,erpWeight,erpMoney,mmAvgStore) "
			+ " values ( #{yyyy}, #{kpiTypeDt}, #{kpiEmp}, #{erpWeight}, #{erpMoney}, #{mmAvgStore})")
	public int insertKpiConsult(@Param ("yyyy") String yyyy,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiEmp") String kpiEmp,
			@Param ("erpWeight") Double erpWeight,@Param ("erpMoney") Double erpMoney,@Param ("mmAvgStore") Double mmAvgStore);
	
	
	/**
	 * 根据人员查询部门
	 * @param uid
	 * @return
	 */
	@Select(" select DEPARTMENTID from hrmresource where id = #{uid} ")
	public int getDeptIdByUid(String uid);
	
	/**
	 * 根据uid查询操作类型
	 * @param uid
	 * @return
	 */
	@Select(" select  case   "
			+ " when a.id in (25,26,27,123,121) then '0'   "
			+ " when a.id in (22,23,30,41) then '1'  "
			+ " when a.id in (31,201) then '2'  "
			+ " when a.id = 24 then '3'    "
			+ " else '无' end as datatype "
			+ " from hrmdepartment a left join hrmresource b on a.id = b.departmentid   where b.id = #{uid}  " )
	public String getTypeByUid(String uid);
	
	
	@Select(" select id from FORMTABLE_MAIN_451 where requestid = #{requestid} ")
	public String getMainid(String requestid);
	
	/**
	 * 需要校验导入的名字是否正确
	 * @param lastname
	 * @return
	 */
	@Select(" select count(1) from hrmresource where lastname = #{lastname}")
	public int checkLastname(String lastname);

	/**
	 * 查询所有人员   ，以便在程序里面做转换
	 * @return
	 */
	@Select("select id,lastname,jobtitle job,departmentid dept from hrmresource where lastname = #{lastname} ")
	public List<EmpOA> queryEmp(String lastname);
	
	/**
	 * 查询变化量
	 * @return
	 */
	@Select("select NAME1 uname, sum( WEIGHTCHANGE) weightChange , sum(MONEYCHANGE) moneyChange from V_ERPCHANGE t group by NAME1 ")
	public List<KpiTotalChange> queryChange();
	
	/**
	 * 
	 * 查询营销中心绩效
	 */
	@Select("select ONEACCOUNT, HRMID, LASTNAME, JOBTITLE, JOBTITLENAME, yyyy,mmstr, MM,weightrate,moneyrate, "
			+ " ONEWEIGHT, WEIGHTCHANGE, REALWEIGHT, TASKWEIGHT, WEIGHTSCORE, "
			+ " ONEMONEY, MONEYCHANGE,OTHERMONEY, REALMONEY, TASKMONEY, MONEYSCORE "
			+ " from V_GETKPIDATAS t")
	public List<KpiData> getKpiData();
	
	/**
	 * 将计算或得出的结果插入报表中
	 * @param kpiTypeDt
	 * @param kpiEmp
	 * @param erpWeight
	 * @param changeWeight
	 * @param erpMoney
	 * @param changeMoney
	 */
	@Insert(" insert into uf_zhdKpiTable "
			+ " (YYYY, KPITYPEDT, KPIEMP, EMPNAME,KPIJOB,weightrate,moneyrate,type_,job_,yyyy_ , "
			+ " ERPWEIGHT, ERPCHANGEWEIGHT, OTHERWEIGHT, REALWEIGHT, TASKWEIGHT, KPIWEIGHT, "
			+ " ERPMONEY, ERPCHANGEMONEY, OTHERMONEY, REALMONEY, TASKMONEY, KPIMONEY)  values ("
			+ " #{YYYY},#{KPITYPEDT},#{KPIEMP},#{EMPNAME},#{KPIJOB},#{weightrate},#{moneyrate},#{type_},#{job_},#{yyyy_}, "
			+ " #{ERPWEIGHT},#{ERPCHANGEWEIGHT},#{OTHERWEIGHT},#{REALWEIGHT},#{TASKWEIGHT},#{KPIWEIGHT},"
			+ " #{ERPMONEY},#{ERPCHANGEMONEY},#{OTHERMONEY},#{REALMONEY},#{TASKMONEY},#{KPIMONEY} ) ")
	public int insertKpiData(@Param ("YYYY") int yyyy,@Param ("KPITYPEDT") int KPITYPEDT,@Param ("KPIEMP") int kpiemp,
			@Param ("EMPNAME") String EMPNAME,@Param ("KPIJOB") int KPIJOB,	@Param ("weightrate") double weightrate,@Param ("moneyrate") double moneyrate,@Param ("type_") String type_,@Param ("job_") String job_,@Param ("yyyy_") String yyyy_,
			@Param ("ERPWEIGHT") double ERPWEIGHT,@Param ("ERPCHANGEWEIGHT") double ERPCHANGEWEIGHT,@Param ("OTHERWEIGHT") double OTHERWEIGHT,@Param ("REALWEIGHT") double REALWEIGHT,@Param ("TASKWEIGHT") double TASKWEIGHT,@Param ("KPIWEIGHT") double KPIWEIGHT,
			@Param ("ERPMONEY") double ERPMONEY,@Param ("ERPCHANGEMONEY") double ERPCHANGEMONEY,@Param ("OTHERMONEY") double OTHERMONEY,@Param ("REALMONEY") double REALMONEY,@Param ("TASKMONEY") double TASKMONEY,@Param ("KPIMONEY") double KPIMONEY
			);
	
	/**
	 * 数据对比
	 * @param yyyy
	 * @param kpitypedt
	 * @param empname
	 * @return
	 */
	@Select("select YYYY, to_number(KPITYPEDT) KPITYPEDT, KPIEMP, KPIJOB, EMPAREA, EMPNAME, JOB_, TYPE_,"
			+ " ERPWEIGHT, ERPCHANGEWEIGHT, OTHERWEIGHT, REALWEIGHT, TASKWEIGHT, KPIWEIGHT, "
			+ " ERPMONEY, ERPCHANGEMONEY, OTHERMONEY, REALMONEY, TASKMONEY, KPIMONEY, "
			+ " AVGPRICE, MMASSESSPRICE, MMAVGSTORE, STORERANGE, ASSESSWEIGHT, KPISTORE from UF_ZHDKPITABLE t "
			+ " where yyyy_=#{yyyy_} and TYPE_ = #{TYPE_} and EMPNAME = #{EMPNAME}")
	public List<Kpi> getKpi(@Param ("yyyy_") String yyyy,@Param ("TYPE_") String TYPE_,@Param ("EMPNAME") String EMPNAME);
	
	/**
	 * 2019-07-06
	 * 获取Erp上的销量高卖数据
	 * @return SaleDatas
	 */
	@Select(" select YF, MM, EMP, ENAME, BM, DNAME, ZXL, GM from V_ERP_SALEDATAS t ")
	public List<SaleDatas> getSaleDatas();
	
	
	/**
	 * 2019-07-06
	 * 将查询的Erp数据定时记录
	 */
	@Insert(" insert into uf_onetotal (oneaccount,oneempcode,onedept,onedeptcode,oneweight,onemoney,onemm,onegetdate) "
			+ " values ( #{oneaccount} , #{oneempcode} , #{onedept} , #{onedeptcode} , #{oneweight} , #{onemoney} , #{onemm} , to_char(sysdate,'yyyy-MM-dd')  )  ")
	public void insertSaleDatasIntoOnetotal(@Param ("oneaccount") String oneaccount,@Param ("oneempcode") String oneempcode,@Param ("onedept") String onedept,
			@Param ("onedeptcode") String onedeptcode,@Param ("oneweight") double oneweight,@Param ("onemoney") double onemoney,@Param ("onemm") String onemm);
	
	/**
	 * 2019-07-08
	 * 将后台算出来的数据查询出来（当前日期的上个月的），  后面插入到zhdkpicompare表中去和前端数据对比
	 * @return
	 */
	@Select(" select EMPID, LASTNAME, SECLEVEL, CREATEDATE, "
			+ " DEPARTMENTID, DEPARTMENTNAME, SHOWORDER, "
			+ " JOBTITLE, JOBTITLENAME, ONEACCOUNT, EMPERP, ONEDEPTCODE, KPITYPE, YYYY, MM_, MM,MM||'月' MMSTR, "
			+ " ONEWEIGHT, WEIGHTCHANGE, OTHERWEIGHT, REALWEIGHT, TASKWEIGHT, WEIGHTKPI, "
			+ " ONEMONEY, MONEYCHANGE, OTHERMONEY, REALMONEY, TASKMONEY, MONEYKPI, "
			+ " AVGPRICE, MMASSESSPRICE, MMAVGSTORE, STORERANGE, ASSESSWEIGHT, KPISTORE "
			+ " from V_ZHD_SALEKPI_ALL t ")
	public List<ZhdKpi> getSaleKpis();
	
	/**
	 * 2019-07-08
	 * 后台计算查询出的数据插入到数据对比表uf_zhdkpicompare中
	 * @param empid
	 * @param lastname
	 * @param seclevel
	 * @param createdate
	 * @param departmentid
	 * @param departmentname
	 * @param showorder
	 * @param jobtitle
	 * @param jobtitlename
	 * @param oneaccount
	 * @param emperp
	 * @param onedeptcode
	 * @param kpitype
	 * @param yyyy
	 * @param mm_
	 * @param mm
	 * @param mmstr
	 * @param oneweight
	 * @param weightchange
	 * @param otherweight
	 * @param realweight
	 * @param taskweight
	 * @param weightkpi
	 * @param onemoney
	 * @param moneychange
	 * @param othermoney
	 * @param realmoney
	 * @param taskmoney
	 * @param moneykpi
	 * @param avgprice
	 * @param mmassessprice
	 * @param mmavgstore
	 * @param storerange
	 * @param assessweight
	 * @param kpistore
	 */
	@Insert(" insert into uf_zhdkpicompare "
			+ " (empid, lastname, seclevel, createdate, departmentid, "
			+ " departmentname, showorder, jobtitle, jobtitlename, oneaccount, emperp, onedeptcode, kpitype, yyyy, mm_, mm, mmstr, "
			+ " oneweight, weightchange, otherweight, realweight, taskweight, weightkpi, "
			+ " onemoney, moneychange, othermoney, realmoney, taskmoney, moneykpi, "
			+ " avgprice, mmassessprice, mmavgstore, storerange, assessweight, kpistore ) values ( "
			+ " #{empid} , #{lastname} , #{seclevel} , #{createdate} , #{departmentid} , "
			+ " #{departmentname} , #{showorder} , #{jobtitle} , #{jobtitlename} , #{oneaccount} ,"
			+ " #{emperp} , #{onedeptcode} , #{kpitype} , #{yyyy} , #{mm_} , #{mm} , #{mmstr} , "
			+ " #{oneweight} , #{weightchange} , #{otherweight} , #{realweight} , #{taskweight} , #{weightkpi} , "
			+ " #{onemoney} , #{moneychange} , #{othermoney} , #{realmoney} , #{taskmoney} , #{moneykpi} , "
			+ " #{avgprice} , #{mmassessprice} , #{mmavgstore} , #{storerange} , #{assessweight} , #{kpistore} ) ")
	public void insertSaleKpisIntoKpicompare(@Param ("empid") String empid,@Param ("lastname") String lastname,@Param ("seclevel") String seclevel,
			@Param ("createdate") String createdate,@Param ("departmentid") String departmentid,@Param ("departmentname") String departmentname,
			@Param ("showorder") String showorder,@Param ("jobtitle") String jobtitle,@Param ("jobtitlename") String jobtitlename,
			@Param ("oneaccount") String oneaccount,@Param ("emperp") String emperp,@Param ("onedeptcode") String onedeptcode,
			@Param ("kpitype") String kpitype,@Param ("yyyy") String yyyy,@Param ("mm_") String mm_,@Param ("mm") String mm,@Param ("mmstr") String mmstr,
			@Param ("oneweight") double oneweight,@Param ("weightchange") double weightchange,@Param ("otherweight") double otherweight,
			@Param ("realweight") double realweight,@Param ("taskweight") double taskweight,@Param ("weightkpi") double weightkpi,
			@Param ("onemoney") double onemoney,@Param ("moneychange") double moneychange,@Param ("othermoney") double othermoney,
			@Param ("realmoney") double realmoney,@Param ("taskmoney") double taskmoney,@Param ("moneykpi") double moneykpi,
			@Param ("avgprice") double avgprice,@Param ("mmassessprice") double mmassessprice,@Param ("mmavgstore") double mmavgstore,
			@Param ("storerange") double storerange,@Param ("assessweight") double assessweight,@Param ("kpistore") double kpistore);
	
	/**
	 * 2019-07-08
	 * 转换月份
	 * @param mm
	 * @return
	 */
	@Select(" select id from UF_ZHDKPITYPEDT where KPITYPEDT = #{mm} ")
	public List<Integer> getMMdt(String mm);
	
	
	/**
	 * 2019-07-08
	 * 转换人员
	 * @param lastname
	 * @return
	 */
	@Select(" select id from hrmresource where lastname = #{lastname} ")
	public List<Integer> getUid(String lastname);
	
	/**
	 * 2019-07-08
	 * 查询岗位
	 * @param lastname
	 * @return
	 */
	@Select(" select jobtitle from hrmresource where lastname = #{lastname} ")
	public List<Integer> getJobId(String lastname);
	
	/**
	 * 2019-07-08
	 * 数据对比 ，使用数据更全的对比表
	 * @param yyyy
	 * @param mm
	 * @param lastname
	 * @return
	 */
	@Select(" select EMPID, LASTNAME, SECLEVEL, CREATEDATE, DEPARTMENTID, "
			+ " DEPARTMENTNAME, SHOWORDER, JOBTITLE, JOBTITLENAME, ONEACCOUNT, "
			+ " EMPERP, ONEDEPTCODE, KPITYPE, YYYY, MM_, MM, MMSTR, "
			+ " ONEWEIGHT, WEIGHTCHANGE, OTHERWEIGHT, REALWEIGHT, TASKWEIGHT, WEIGHTKPI, "
			+ " ONEMONEY, MONEYCHANGE, OTHERMONEY, REALMONEY, TASKMONEY, MONEYKPI, "
			+ " AVGPRICE, MMASSESSPRICE, MMAVGSTORE, STORERANGE, ASSESSWEIGHT, KPISTORE "
			+ " from uf_zhdkpicompare where yyyy = #{yyyy} and mmstr = #{mm} and lastname = #{lastname}")
	public List<ZhdKpi> getZhdKpi(@Param ("yyyy") String yyyy,@Param ("mm") String mm,@Param ("lastname") String lastname);
	
	
	/**
	 * 2019-07-08
	 * 插入流程表中
	 */
	@Insert(" insert into FORMTABLE_MAIN_451_dt1 ( MAINID, YYYY, KPIAREA, KPIEMP, KPIJOB, "
			+ " ERPWEIGHT, ERPCHANGEWEIGHT, OTHERWEIGHT, REALWEIGHT, TASKWEIGHT, KPIWEIGHT, "
			+ " ERPMONEY, ERPCHANGEMONEY, OTHERMONEY, REALMONEY, TASKMONEY, KPIMONEY, "
			+ " AVGPRICE, LASTDAYPRICE, MMASSESSPRICE, MMAVGSTORE, STORERANGE, ASSESSWEIGHT, KPISTORE, "
			+ " ADDSCORE, ADDREMK, REDUCESCORE, REDUCEREMK, BASICSCORE, OTHERSCORE, OTHERREMK, "
			+ " KPITOTAL, KPITYPEDT, EMPNAME ) values ( "
			+ " #{mainid} , #{yyyy_} , #{kpiarea} , #{kpiemp} , #{kpijob} , "
			+ " #{erpweight} , #{erpchangeweight} , #{otherweight} , #{realweight} , #{taskweight} , #{kpiweight} , "
			+ " #{erpmoney} , #{erpchangemoney} , #{othermoney} , #{realmoney} , #{taskmoney} , #{kpimoney} , "
			+ " #{avgprice} , #{lastdayprice} , #{mmassessprice} , #{mmavgstore} , #{storerange} , #{assessweight} , #{kpistore} , "
			+ " #{addscore} , #{addremk} , #{reducescore} , #{reduceremk} , #{basicscore} , #{otherscore} , #{otherremk} , "
			+ " #{kpitotal} , #{kpitypedt} , #{empname} ) ")
	public void insertIntoWorkflowDt(@Param ("mainid") String mainid, @Param ("yyyy_") String  yyyy_, 
			@Param ("kpiarea") String  kpiarea, @Param ("kpiemp") String  kpiemp, @Param ("kpijob") String kpijob, 
			@Param ("erpweight") double  erpweight, @Param ("erpchangeweight") double  erpchangeweight, 
			@Param ("otherweight") double  otherweight, @Param ("realweight") double  realweight, 
			@Param ("taskweight") double  taskweight, @Param ("kpiweight") double  kpiweight, 
			@Param ("erpmoney") double  erpmoney, @Param ("erpchangemoney") double  erpchangemoney, 
			@Param ("othermoney") double  othermoney, @Param ("realmoney") double  realmoney, 
			@Param ("taskmoney") double  taskmoney, @Param ("kpimoney") double  kpimoney, 
			@Param ("avgprice") double  avgprice, @Param ("lastdayprice") double  lastdayprice, 
			@Param ("mmassessprice") double  mmassessprice, @Param ("mmavgstore") double  mmavgstore, 
			@Param ("storerange") double  storerange, @Param ("assessweight") double  assessweight, @Param ("kpistore") double  kpistore, 
			@Param ("addscore") double  addscore, @Param ("addremk") String  addremk, 
			@Param ("reducescore") double  reducescore, @Param ("reduceremk") String  reduceremk, 
			@Param ("basicscore") double  basicscore, @Param ("otherscore") double  otherscore, @Param ("otherremk") String  otherremk, 
			@Param ("kpitotal") double  kpitotal, @Param ("kpitypedt") String  kpitypedt, @Param ("empname") String  empname);

	
}
