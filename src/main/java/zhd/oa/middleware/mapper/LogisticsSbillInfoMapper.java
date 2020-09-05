package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zhd.oa.middleware.model.LogisticsSbillInfo;

import java.util.List;

public interface LogisticsSbillInfoMapper {
    @Select(" select a.sbill_billcode sbillCode, a.sbill_billbatch sbillDtCode, " +
            "       a.partsname_name goodsname, a.goods_material goodsMaterial, a.goods_spec goodsSpec, a.productarea_name goodsArea, " +
            "       a.goods_property4 goodsZLFW, a.goods_property5 goodsGCFW, a.data_bnum goodsNum, a.data_bweight goodsWeight, " +
            "       b.datas_customer buyerComCode,b.datas_customername buyerComName,c.org_code goodsOrgCode,c.org_abbreviate goodsOrgName " +
            " from v_codes_for_dc_dt a " +
            " left join v_codes_for_dc b on a.sbill_billcode = b.sbill_billcode " +
            " left join basic_org@oa_erp c on a.org_code = c.org_code " +
            " where instr(#{SbillCodes}, a.sbill_billcode )  > 0 " +
            " order by a.sbill_billcode asc , sbill_billbatch asc ")
    List<LogisticsSbillInfo> getSbillInfoList(@Param("SbillCodes") String SbillCodes);

}
