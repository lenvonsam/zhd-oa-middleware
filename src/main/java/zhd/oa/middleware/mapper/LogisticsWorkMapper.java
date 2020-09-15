package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LogisticsWorkMapper {

    /**
     * 根据货源id查询流程id
     * @param goodSourceNo
     * @return
     */
    @Select(" select max(id) from formtable_main_583 where sourceid = #{goodSourceNo} ")
    public String getMainid(@Param("goodSourceNo") String goodSourceNo);

    /**
     * 根据mainid获取流程requestid
     */
    @Select(" select requestid from formtable_main_583 where id = #{mainid} ")
    public String getRequestid(@Param("mainid") String mainid);

    /**
     * 根据货源id查找流程请求id
     * @param sourceid 货源id即货源编号goodSourceNo
     * @return
     */
    @Select(" select requestid from formtable_main_583 where sourceid = #{sourceid} ")
    public String getRequestidBySourceid(@Param("sourceid") String sourceid);

    /**
     * 校验流程状态
     * @param requestid
     * @return
     */
    @Select(" select currentnodetype from workflow_requestbase where requestid = #{requestid} ")
    public String checkWorkStatus(@Param("requestid") String requestid);

    /**
     * 校验单位名是否在Erp系统
     * @param comname
     * @return
     */
    @Select(" select company_code from v_erp_company where company_name = #{comname} ")
    public String checkComname(@Param("comname") String comname);

    /**
     * 校验司机名是否在Erp系统
     * @param driver
     * @return
     */
    @Select(" select max(driver_code) from v_erp_driver where driver_name = #{driver} ")
    public String checkDriver(@Param("driver") String driver);

    /**
     * 校验车牌号使用
     * @param carno
     * @return
     */
    @Select(" select max(car_code) from v_erp_cars where car_number = #{carno} ")
    public String checkCarNo(@Param("carno") String carno);
    
    @Delete("delete from formtable_main_583_dt2 where driver is null and mainid=#{mainId}")
    public int batchDeleteByMainId(String mainId);

    /**
     * 新版本调车流程更新明细数据
     * @param mainid
     * @param closeCom
     * @param tranCom
     * @param driver
     * @param phone
     * @param carNo
     * @param freightMode
     * @param price
     * @param unit
     * @param weight
     * @param money
     * @param carMax
     * @param carLength
     * @param remk
     * @return
     */
    @Insert(" insert into formtable_main_583_dt2 a ( " +
            " a.mainid, a.balance_com, a.tran_com, " +
            " a.driver_str, a.phone, a.car_no_str, a.freight_mode, a.freight_price, a.freight_unit, " +
            " a.tran_weight, a.tran_money, a.car_max, a.car_length, a.remk ) " +
            " values ( #{mainid},#{closeCom},#{tranCom},#{driver},#{phone},#{carNo}, " +
            " #{freightMode},#{price},#{unit},#{weight},#{money},#{carMax},#{carLength},#{remk} ) ")
    public boolean insetLogisticsWorkDetail(@Param("mainid") String mainid,@Param("closeCom") String closeCom, @Param("tranCom") String tranCom,
                                            @Param("driver") String driver,@Param("phone") String phone,@Param("carNo") String carNo,
                                            @Param("freightMode") String freightMode,@Param("price") String price,@Param("unit") String unit,
                                            @Param("weight") String weight,@Param("money") String money,@Param("carMax") String carMax,
                                            @Param("carLength") String carLength,@Param("remk") String remk);

    /**
     * 记录回调接口
     * @param data
     * @param result
     * @param remk
     * @param url
     */
    @Insert(" insert into uf_zf_record ( work_reqid,req_time,req_data,req_result,remk,req_url ) " +
            " values (#{requestid},to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),#{data},#{result},#{remk},#{url} ) ")
    public boolean insertLogisticsRecord(@Param("requestid") String requestid,@Param("data") String data,
                                      @Param("result") String result,@Param("remk") String remk,@Param("url") String url);




}
