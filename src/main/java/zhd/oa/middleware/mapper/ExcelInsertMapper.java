package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ExcelInsertMapper {

	@Update("update FORMTABLE_MAIN_390_DT1 set ACCEPT_DATEE=#{datee},ACCEPT_OUTDATE= #{outdate} where ACCEPT_NO=#{acceptNo} and AFFIX is null")
	public int insertExcelContent(@Param ("datee")String  datee,@Param ("outdate")String  outdate,@Param ("acceptNo")String  acceptNo);
	
}
