package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zhd.oa.middleware.model.LogisticsOutSbill;

import java.util.List;

public interface LogisticsOutSbillMapper {

    @Select("select a.delivery_billcode  deliverybillcode, a.sbill_billcode   sbillbillcode, to_char(a.delivery_date,'yyyy-mm-dd')  " +
            "  deliverydate, a.datas_customer datascustomer, a.datas_customername  datascustomername, a.datas_carnum " +
            "  datascarnum, a.car_code  carcode,b.car_driver_id driverCarId, a.datas_driver   datasdriver,a.driver_code  drivercode, " +
            " a.goods_num  goodsnum, a.goods_weight  goodsweight, a.employee_code  employeecode, " +
            " a.org_code  orgcode,a.dept_code  deptcode, a.partsname_name partsnamename, a.goods_material  goodsmaterial," +
            " a.goods_spec  goodsspec, a.productarea_name  productareaname, a.goods_property4  zlfw, a.goods_property5 gcfw " +
            " from v_dc_delivery_bills a " +
            " left join v_cars b on a.car_code = b.car_code  " +
            " where instr(#{codes}, a.sbill_billcode ) > 0 ")
    public List<LogisticsOutSbill> getLogisticsOutSbill(@Param("codes") String SbillCodes);


}
