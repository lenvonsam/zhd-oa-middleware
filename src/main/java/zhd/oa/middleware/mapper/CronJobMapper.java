package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.CronJob;

public interface CronJobMapper {
	
	@Select("select userid, uname,to_char(wm_concat(msg)) msg "
			+ "from ("
			+ "	select userid,用户名 uname,"
			+ "	case   "
			+ "	when 上班='异常' then datei||' '||week||' '||am||' '||pm|| '    【上班异常】'  "
			+ "	when 下班='异常' then datei||' '||week||' '||am||' '||pm|| '    【下班异常】'  "
			+ "	end msg "
			+ "	from v_sign_base t "
			+ "	where datei>=to_char(add_months(sysdate,-1),'yyyy-MM')||'-01'"
			+ "	and datei<to_char(sysdate,'yyyy-MM-dd')"
			+ "	and 最终判定='考勤异常'"
			+ ")group by userid,uname")
	public List<CronJob> getCronJobData();

	/**
	 * 同步Erp绩效相关的数据到OA的uf_ErpPerfEmp表中
	 * @return
	 */
	@Insert(" insert into uf_ErpPerfEmp (dept_name,emp_name,emp_account,emp_job,entry_date,probationend_date,emp_seclevel, " +
			"       yyyymm,weight_mission,weight_erp,weight_change, " +
			"       zf_weight,zf_price,zf_tc,dh_weight,dh_price,dh_tc, " +
			"       new_count,new_weight,new_price,new_tc,old_weight,old_price,old_tc, " +
			"       hs_weight,hs_price,hs_tc,hs_new_weight, " +
			"       data_yyyymm,dept_code,emp_id,emp_code)  " +
			" ( select c.departmentname,b.emp_name,a.employee_name,b.emp_job,d.field8,e.probationenddate,e.seclevel, " +
			"       a.ny,f.weight,a.data_bweight,'', " +
			"       a.zf_bweight,a.zf_price,a.zf_tc,a.dh_bweight,a.dh_price,a.dh_tc, " +
			"       a.new_count,a.new_weight,a.new_price,a.new_tc,a.old_weight,a.old_price,a.old_tc, " +
			"       a.hs_bweight,a.hs_price,a.hs_tc,'', " +
			"       to_char(sysdate,'yyyymm'),a.dept_code,b.emp_id,b.account_code " +
			" from v_sale_tchz_oa@oa_erp a " +
			" left join uf_EmpJoinErp b on a.employee_code = b.account_code " +
			" left join hrmdepartment c on b.emp_dept = c.id " +
			" left join cus_fielddata d on b.emp_id = d.id and d.scopeid = -1 " +
			" left join hrmresource e on b.emp_id = e.id " +
			" left join basic_sale_task@oa_erp f on a.ny = f.year||month and b.emp_name = f.employee_name " +
			" where b.emp_name is not null " +
			" and a.ny = to_char(add_months(sysdate,-1),'yyyymm')) ")
	public boolean syncPerfJob();

	/**
	 * 同步前清除uf_ErpPerfEmp当月记录的数据   避免重复
	 * @return
	 */
	@Delete(" delete from uf_ErpPerfEmp where data_yyyymm = to_char(sysdate,'yyyymm') ")
	public boolean clearErpPerfEmp();

}
