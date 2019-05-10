package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zhd.oa.middleware.model.LoansInterest;

public interface LoansInterestMapper {
	/**
	 * 查询贷款流程明细表
	 * @param requestid 请求id
	 * @return
	 */
	@Select("select T.ID, T.MAINID, T.PERIODNUM, T.PERIODS, T.INTERESTFROM, T.INTERESTTO, T.INTERESTDAYS, T.INTERESTAMOUNT, T.PAIDAMOUNT, T.PERIODSSTATUS from FORMTABLE_MAIN_431_DT1 t "
			+ "left join FORMTABLE_MAIN_431 t1 on t.mainid = t1.id  where t1.requestid = #{requestid} order by t.id desc")
	public List<LoansInterest> queryLoansInterest(int requestid) ;

	/**
	 * 插入明细表数据
	 * @param 
	 */
	@Insert("insert into FORMTABLE_MAIN_431_DT1 ( MAINID, PERIODNUM, PERIODS, INTERESTFROM, INTERESTTO, INTERESTDAYS, INTERESTAMOUNT, PERIODSSTATUS ) "
			+ "values ( #{mainid} , #{periodNum} , #{periods} , #{interestFrom} , #{interestTo} , #{interestDays} , #{interestAmount} , #{periodsStatus} )")
	public int insertLoansInterest(@Param ("mainid")int mainid,@Param ("periodNum")int periodNum,@Param ("periods")String periods,
			@Param ("interestFrom")String interestFrom,@Param ("interestTo")String interestTo,@Param ("interestDays")int interestDays,
			@Param ("interestAmount")Double interestAmount,@Param ("periodsStatus")String periodsStatus);
	
	/**
	 * 根据mainid查询扣息明细
	 * @param mainid
	 * @return
	 */
	@Select("select T.ID, T.MAINID, T.PERIODNUM, T.PERIODS, T.INTERESTFROM, T.INTERESTTO, T.INTERESTDAYS, T.INTERESTAMOUNT, T.PAIDAMOUNT, T.PERIODSSTATUS from FORMTABLE_MAIN_431_DT1 t  "
			+ "where T.mainid =#{mainid} "
			+ "order by PERIODS desc")
	public List<LoansInterest> queryLoansInterestByMainid(int mainid) ;
	
	/**
	 * 根据periods查询具体的一条扣息明细
	 * @param periods
	 * @return
	 */
	@Select("select T.ID, T.MAINID, T.PERIODNUM, T.PERIODS, T.INTERESTFROM, T.INTERESTTO, T.INTERESTDAYS, T.INTERESTAMOUNT, T.PAIDAMOUNT, T.PERIODSSTATUS from FORMTABLE_MAIN_431_DT1 t  "
			+ "where t.periods =#{periods} and t.mainid = #{mainid}")
	public LoansInterest queryLoanInterestByPeriod(@Param("periods")String periods ,@Param("mainid")int mainid);
	
	/**
	 * 根据periods更新扣息状态
	 * @param periods
	 * @return
	 */
	@Update("update FORMTABLE_MAIN_431_DT1 set PERIODSSTATUS = '已扣息' where PERIODS = #{periods}")
	public int updateLoansInterestStautsByPeriod(String periods);
	
	/**
	 * 查询最近的一条扣息记录，生效的和未结束的贷款
	 * @return
	 */
	@Select("select T.ID, T.MAINID, T.PERIODNUM, T.PERIODS, T.INTERESTFROM, T.INTERESTTO, T.INTERESTDAYS, T.INTERESTAMOUNT, T.PAIDAMOUNT, T.PERIODSSTATUS from FORMTABLE_MAIN_431_DT1 t  "
			+ " left join Formtable_Main_431 t1 on t.mainid = t1.id "
			+ " where t1.loanStatus = 1 and ( t1.finished is null or t1.finished <> 1 )"
			+ " and t.id in ( select max(id) from Formtable_Main_431_Dt1 group by mainid )")
	public List<LoansInterest> queryLastLoansInterest();
	
	
	
}
