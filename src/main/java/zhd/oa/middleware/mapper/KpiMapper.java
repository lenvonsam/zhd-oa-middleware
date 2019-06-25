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
			+ " else 0 end res"
			+ "from  uf_zhdKpiConsult "
			+ "where YYYY = #{yyyy} and KPITYPEDT = #{kpiTypeDt} and KPIEMP = #{kpiEmp}")
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
	 * 插入绩效流程明细
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
			+ " ( #{yyyy},#{kpiEmp},#{kpiTypeDt},#{erpWeight},#{erpChangeWeight},#{otherWeight},#{realWeight},#{taskWeight},#{kpiWeight}, "
			+ " #{erpMoney} ,#{erpChangeMoney},#{otherMoney},#{realMoney},#{taskMoney},#{kpiMoney},"
			+ " #{addScore},#{addRemk},#{reduceScore},#{reduceRemk} ) ")
	public int insertKpiDt(@Param ("remk") String remk,@Param ("yyyy") String yyyy,@Param ("kpiEmp") String kpiEmp,@Param ("kpiTypeDt") String kpiTypeDt,
			@Param ("erpWeight") Double erpWeight,@Param ("erpChangeWeight") Double erpChangeWeight,@Param ("otherWeight") Double otherWeight,@Param ("realWeight") Double realWeight,@Param ("taskWeight") Double taskWeight,@Param ("kpiWeight") Double kpiWeight,
			@Param ("erpMoney") Double erpMoney,@Param ("erpChangeMoney") Double erpChangeMoney,@Param ("otherMoney") Double otherMoney,@Param ("realMoney") Double realMoney,@Param ("taskMoney") Double taskMoney,@Param ("kpiMoney") Double kpiMoney,
			@Param ("addScore") Double addScore,@Param ("addRemk") String addRemk,@Param ("reduceScore") Double reduceScore,@Param ("reduceRemk") Double reduceRemk
			);
	
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
	
	
	
}
