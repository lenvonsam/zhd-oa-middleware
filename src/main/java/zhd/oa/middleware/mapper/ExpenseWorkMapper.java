package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ExpenseWorkMapper {

    /**
     * 插入费用报销明细表
     * @param mainid 主流程id
     * @param expenseType 报销类型
     * @param expenseMoney 费用金额
     * @param expenseDate 费用日期
     * @param expenseDetail 费用内容
     * @param expenseForCompany 业务单位
     * @param expenseFromFlow 引用流程  流程的requestid
     * @return
     */
    @Insert(" insert into formtable_main_354_dt1 (mainid,expense_type,expense_money,expense_dt1,expense_date,expense_customer,expense_flow) \n" +
            " values ( #{mainid},#{expenseType},#{expenseMoney},#{expenseDetail},#{expenseDate},#{expenseForCompany},#{expenseFromFlow} ) ")
    public boolean insertExpenseFeeDetail(@Param("mainid") String mainid,@Param("expenseType") String expenseType,
                                          @Param("expenseMoney") String expenseMoney, @Param("expenseDate") String expenseDate,
                                          @Param("expenseDetail") String expenseDetail, @Param("expenseForCompany") String expenseForCompany,
                                          @Param("expenseFromFlow") String expenseFromFlow);

    /**
     * 根据请求id获取报销主流程id
     * @param requestid 费用报销主流程请求id
     * @return
     */
    @Select(" select id from formtable_main_354 where requestid = #{requestid} ")
    public String getMainid(@Param("requestid") String requestid);





}
