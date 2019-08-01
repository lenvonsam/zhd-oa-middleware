package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.OutSbills;

public interface OutSbillsMapper {
	
	/**
	 * 查询提单
	 * @param billcodes
	 * @return
	 */
	@Select("select t.SBILL_BILLCODE billcode,t.SBILL_BILLBATCH billbatch,t2.DATAS_CUSTOMERNAME customer,"
			+ " t.PNTREE_NAME pntree,t.GOODS_MATERIAL mat,t.GOODS_SPEC spec,"
			+ " t.GOODS_PROPERTY1 mlength,t.PRODUCTAREA_NAME area ,"
			+ " t.GOODS_PROPERTY4 zlfw,t.GOODS_PROPERTY5 gcfw,t.GOODS_METERING metering,"
			+ " t1.warehouse_name warehouse,t.GOODS_NUM nums "
			+ " from WAREHOUSE_DELIVERY_DETAIL@erp_cs t "
			+ " left join BASIC_WAREHOUSE@erp_cs t1 on t.WAREHOUSE_CODE = t1.WAREHOUSE_CODE "
			+ " left join WAREHOUSE_DELIVERY@erp_cs t2 on t.SBILL_BILLCODE = t2.SBILL_BILLCODE "
			+ " where t.SBILL_BILLCODE in ( #{billcodes} ) ")
	public List<OutSbills> getOutSbillsBySbillscode(@Param("billcodes") String billcodes);
	
	/**
	 *查询出库单 
	 * @param billcodes
	 * @return
	 */
	@Select("select t.ownerout_billcode billcode,t.ownerout_billcode billbatch,t1.datas_acceptcorpname customer,"
			+ " t.pntree_name pntree,t.goods_spec spec,"
			+ " t.goods_material mat,t.goods_property1 mlength,t.productarea_name area,"
			+ " t.goods_property4 zlfw,t.goods_property5 gcfw,t.goods_metering metering,"
			+ " t2.warehouse_name warehouse,t.goods_num nums "
			+ " from storage_ownerout_detail@wms t "
			+ " left join storage_ownerout@wms t1 on t.ownerout_billcode = t1.ownerout_billcode "
			+ " left join basic_warehouse@wms t2 on t.warehouse_code = t2.warehouse_code"
			+ " where t.ownerout_billcode in (#{billcodes} ) ")
	public List<OutSbills> getOutSbillsCodeBySbillscode(@Param("billcodes") String billcodes);
	
}
