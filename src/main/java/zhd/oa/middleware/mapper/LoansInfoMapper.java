package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.LoansInfo;

public interface LoansInfoMapper {
	/**
	 * 查询所有的贷款流程信息
	 * @return
	 */
	@Select("select T.ID, T.REQUESTID, T.FLOWNO, T.APPLICANT, T.APPLYDEPT, T.APPLYDATE, T.APPLYREMK, T.LOANSORG, "
			+ "T.LOANSACCOUNT, T.LOANSBANK, T.LOANSMONEY, T.LOANSDATEFROM, T.LOANSDATETO, T.LOANRATE, T.DISCOUNTDATE, "
			+ "T.DISCOUNTACCOUNT, T.SUPERVISIONFEE, T.PREMIUM, T.FINISHED, T.LOANSTATUS "
			+ "from  formtable_main_448  t where t.loanStatus = 1 and ( t.finished is null or t.finished <> 1 ) ")
	public List<LoansInfo> queryLoansInfo();
	
	/**
	 * 根据明细传的mainid查询主表贷款信息
	 * @param id
	 * @return
	 */
	@Select("select T.ID, T.REQUESTID, T.FLOWNO, T.APPLICANT, T.APPLYDEPT, T.APPLYDATE, T.APPLYREMK, T.LOANSORG, "
			+ "T.LOANSACCOUNT, T.LOANSBANK, T.LOANSMONEY, T.LOANSDATEFROM, T.LOANSDATETO, T.LOANRATE, T.DISCOUNTDATE, "
			+ "T.DISCOUNTACCOUNT, T.SUPERVISIONFEE, T.PREMIUM, T.FINISHED, T.LOANSTATUS "
			+ " from  formtable_main_448  t where id = #{id}")
	public LoansInfo queryLoansInfoById(int id);
	
	/**
	 * 查询首次贷款(进行中且扣息未完结)的没有明细的流程主表 
	 * @return
	 */
	@Select("select T.ID, T.REQUESTID, T.FLOWNO, T.APPLICANT, T.APPLYDEPT, T.APPLYDATE, T.APPLYREMK, T.LOANSORG, "
			+ "T.LOANSACCOUNT, T.LOANSBANK, T.LOANSMONEY, T.LOANSDATEFROM, T.LOANSDATETO, T.LOANRATE, T.DISCOUNTDATE, "
			+ "T.DISCOUNTACCOUNT, T.SUPERVISIONFEE, T.PREMIUM, T.FINISHED, T.LOANSTATUS "
			+ " from  formtable_main_448  t left join  formtable_main_448_Dt1 t1 on t.id = t1.mainid "
			+ " where t1.id is null and ( t.finished = 0 or t.finished is null ) and t.loanstatus = 1 ")
	public List<LoansInfo> queryFirstLoansInfo();
	
	/**
	 * 根据明细mainid查询主表requestid进行提交
	 * @param mainid
	 * @return
	 */
	@Select("select t.requestid from  formtable_main_448  t where t.id = #{mainid} ")
	public String queryLoansInfoRequestid(int mainid);
	
}
