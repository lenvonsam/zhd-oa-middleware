package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.ExpenseReimburseDetail;
import zhd.oa.middleware.model.ExpenseReimburseMain;

public interface ExpenseReimburseMapper {
	/**
	 * 查询创建费用报销流程所需的主表数据 
	 */
	@Select(" select t1.applicant_ userid,t1.applydate_ applyDate,t1.belongdept_ belongDeptId,t1.payment_ paymethodId,t1.remk_ remk, "
			+ " sum(t1.domoney) domoney from formtable_main_501 t "
			+ " left join formtable_main_501_dt1 t1 on t.id = t1.mainid "
//			+ " where t.requestid = #{requestid} "
			+ " where t.reqid = #{requestid} "
			+ " group by t1.applicant_ ,t1.applydate_ ,t1.belongdept_ ,t1.payment_ ,t1.remk_ ")
	public List<ExpenseReimburseMain> getExpenseReimburseMain(@Param("requestid")String requestid);
	
	/**
	 * 根据主表的数据获取明细表数据
	 */
	@Select(" select t1.dodt expenseType, t1.domoney expenseMoney,t1.dodate expenseDate,t1.doremk expenseDt "
			+ " from formtable_main_501 t "
			+ " left join formtable_main_501_dt1 t1 on t.id = t1.mainid "
//			+ " where t.requestid = #{requestid} "
			+ " where t.reqid = #{requestid} "
			+ " and t1.applicant_ = #{applicant} "
			+ " and t1.applydate_ = #{applydate} "
			+ " and t1.belongdept_ = #{belongdept} "
			+ " and t1.payment_ = #{payment} "
			+ " and t1.remk_  = #{remk}   ")
	public List<ExpenseReimburseDetail> getExpenseReimburseDetail(@Param("requestid")String requestid,
			@Param("applicant")int applicant,@Param("applydate")String applydate,@Param("belongdept")int belongdept,
			@Param("payment")int payment,@Param("remk")String remk);
	
}
