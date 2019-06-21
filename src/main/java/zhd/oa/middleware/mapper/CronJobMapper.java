package zhd.oa.middleware.mapper;

import java.util.List;

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
			+ "	where  datei>=to_char(sysdate,'yyyy-MM')||'-01'"
			+ "	and datei<to_char(sysdate,'yyyy-MM-dd')"
			+ "	and 最终判定='考勤异常'"
			+ ")group by userid,uname")
	public List<CronJob> getCronJobData(); 
	
}
