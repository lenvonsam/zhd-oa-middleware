package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.KpiModel;

public interface KpiMapper {
	
	/**
	 * 查询erp上的销量和高卖
	 * @return
	 */
	@Select("select to_char(sysDate,'yyyy') yyyy, substr(YF,6,8) kpiTypeDt, EMP_NAME kpiEmp, ZXL erpWeight, GM erpMoney from V_ZHDERPKPI "
			+ " where YF = to_char(add_months(trunc(sysdate),-1),'yyyy-MM') ")
	public List<KpiModel> queryErpKpi();
	
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
	@Insert("insert into FORMTABLE_MAIN_451_dt1 ( mainid,yyyy,kpiEmp,kpiTypeDt, "
			+ " erpWeight,erpChangeWeight,otherWeight,realWeight,taskWeight,kpiWeight, "
			+ " erpMoney,erpChangeMoney,otherMoney,realMoney,taskMoney,kpiMoney, "
			+ " addScore,addRemk,reduceScore,reduceRemk ) values "
			+ " ( #{mainid},#{yyyy},#{kpiEmp},#{kpiTypeDt},#{erpWeight},#{erpChangeWeight},#{otherWeight},#{realWeight},#{taskWeight},#{kpiWeight}, "
			+ " #{erpMoney} ,#{erpChangeMoney},#{otherMoney},#{realMoney},#{taskMoney},#{kpiMoney},"
			+ " #{addScore},#{addRemk},#{reduceScore},#{reduceRemk} ) ")
	public int insertKpiXSDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiEmp") String kpiEmp,@Param ("kpiTypeDt") String kpiTypeDt,
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
	@Insert("insert into FORMTABLE_MAIN_451_dt1 (mainid,yyyy,kpiEmp,kpiTypeDt,kpiArea, "
			+ " addScore,addRemk,reduceScore,reduceRemk,"
			+ " basicScore,otherScore,otherRemk,kpitotal ) values "
			+ " ( #{mainid},#{yyyy},#{kpiEmp},#{kpiTypeDt},#{kpiArea},#{addScore},#{addRemk},#{reduceScore},#{reduceRemk},"
			+ " #{basicScore},#{otherScore},#{otherRemk},#{kpitotal} ) ")
	public int insertKpiCCDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiEmp") String kpiEmp,@Param ("kpiTypeDt") String kpiTypeDt,@Param ("kpiArea") String kpiArea,
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
	@Insert("insert into FORMTABLE_MAIN_451_dt1 ( mainid,yyyy,kpiEmp,kpiTypeDt, "
			+ " addScore,addRemk,reduceScore,reduceRemk,kpitotal ) values "
			+ " (#{mainid},#{yyyy},#{kpiEmp},#{kpiTypeDt},#{addScore},#{addRemk},#{reduceScore},#{reduceRemk},#{kpitotal})  ")
	public int insertKpiXYDt(@Param ("mainid") String mainid,@Param ("yyyy") String yyyy,@Param ("kpiEmp") String kpiEmp,@Param ("kpiTypeDt") String kpiTypeDt,
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
	
	
	@Select(" select id from FORMTABLE_MAIN_451 where requestid = #{requestid} ")
	public String getMainid(String requestid);
	
	
}
