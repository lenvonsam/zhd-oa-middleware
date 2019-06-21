package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.KpiModel;

public interface KpiMapper {
	
	@Select("select to_char(sysDate,'yyyy') yyyy, YF kpiTypeDt, EMP_NAME kpiEmp, ZXL erpWeight, GM erpMoney from V_ZHDERPKPI ")
	public List<KpiModel> queryErpKpi();

}
