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

public interface KpiMapper {
	
	/**
	 * 查询erp上的销量和高卖
	 * @return
	 */
	@Select("select to_char(sysDate,'yyyy') yyyy, substr(YF,6,8) kpiTypeDt,case when EMP_NAME is null then employee_name else emp_name end  kpiEmp,"
			+ " ZXL erpWeight, GM erpMoney from V_ZHDERPKPI "
			+ " where YF = to_char(add_months(trunc(sysdate),-1),'yyyy-MM') ")
	public List<KpiModel> queryErpKpi();
	
	/**
	 * 每月初2号查询系统的销量 ，以计算变化量
	 * @return
	 */
	@Select("select case when emp_name is null then employee_name else emp_name end oneName,"
			+ "EMPLOYEE_NAME oneAccount,yf oneMM,sum(zxl) oneWeight,sum(gm) oneMoney ,to_char(sysdate,'yyyy-MM-dd') oneGetDate  "
			+ "from uf_zhdKpiTable where yf<to_char(trunc(sysdate,'MM'),'yyyy-MM')group by emp_name,EMPLOYEE_NAME,yf"
			)
	public List<KpiTotalChangeOne> queryChangeOne();
	
	/**
	 * 
	 * 写入销量合计 ，计算变化量
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
	public int insertKpiXSDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiEmp") String kpiEmp,
			@Param ("erpWeight") String erpWeight,@Param ("erpChangeWeight") String erpChangeWeight,@Param ("otherWeight") String otherWeight,@Param ("realWeight") String realWeight,@Param ("taskWeight") String taskWeight,@Param ("kpiWeight") String kpiWeight,
			@Param ("erpMoney") String erpMoney,@Param ("erpChangeMoney") String erpChangeMoney,@Param ("otherMoney") String otherMoney,@Param ("realMoney") String realMoney,@Param ("taskMoney") String taskMoney,@Param ("kpiMoney") String kpiMoney,
			@Param ("addScore") String addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") String reduceScore,@Param ("reduceRemk") String reduceRemk
			);
	
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
	@Select("select ONENAME, MM, ONEWEIGHT, WEIGHTCHANGE, ONEMONEY, MONEYCHANGE from  V_GETKPIDATAS t")
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
	@Insert("insert into uf_zhdKpiTable  ( yyyy,kpitypedt,kpiemp,erpweight,erpchangeweight,erpmoney,erpchangemoney,empname ) "
			+ " values ( #{yyyy} ,#{kpitypedt} , #{kpiemp} , #{erpweight} , #{erpchangeweight} , #{erpmoney} , #{erpchangemoney}, #{empname}  ) ")
	public int insertKpiData(@Param ("yyyy") String yyyy,@Param ("kpitypedt") String kpitypedt,@Param ("kpiemp") String kpiemp,
			@Param ("erpweight") String erpweight,@Param ("erpchangeweight") String erpchangeweight,
			@Param ("erpmoney") String erpmoney,@Param ("erpchangemoney") String erpchangemoney,@Param ("empname") String empname);
	
	/**
	 * 数据对比
	 * @param yyyy
	 * @param kpitypedt
	 * @param empname
	 * @return
	 */
	@Select("select yyyy, kpitypedt, kpiemp, kpijob, emparea,empname, "
			+ "erpweight erpweight, erpchangeweight erpchangeweight, otherweight otherweight, "
			+ "realweight realweight, taskweight taskweight, kpiweight kpiweight, "
			+ "erpmoney erpmoney, erpchangemoney erpchangemoney, othermoney othermoney, "
			+ " realmoney realmoney, taskmoney taskmoney, kpimoney kpimoney, "
			+ "avgprice avgprice, mmassessprice mmassessprice, mmavgstore mmavgstore,"
			+ " storerange storerange, assessweight assessweight, kpistore kpistore "
			+ "from uf_zhdkpitable t where yyyy = #{yyyy} and kpitypedt = #{kpitypedt} and empname = #{empname} ")
	public List<Kpi> getKpi(@Param ("yyyy") String yyyy,@Param ("kpitypedt") String kpitypedt,@Param ("empname") String empname);
}
