package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zhd.oa.middleware.model.ErpTransRecord;

import java.util.List;

public interface ErpTransRecordMapper {

	@Select("select id, flow_name, insert_date, trans_content from uf_erp_sale_record where id = #{id}")
	ErpTransRecord findOne(Integer id);

	@Select("<script>select * from (select rst.*,rownum rn from (select id, flow_name, insert_date, trans_content from uf_erp_sale_record where 1=1 <when test='content != null'>and trans_content like '%${content}%'</when> <when test='startDate != null'>and insert_date &gt;='${startDate}'</when> <when test='endDate != null'>and insert_date &lt;='${endDate}'</when> order by insert_date desc) rst where rownum &lt;#{lastIndex}) where rn &gt;#{firstIndex}</script>")
	List<ErpTransRecord> findRecordList(@Param("firstIndex")Integer firstIndex, @Param("lastIndex")Integer lastIndex, @Param("content")String content, @Param("startDate")String startDate, @Param("endDate")String endDate);

    @Select("<script>select count(1) from uf_erp_sale_record where 1=1 <when test='content != null'>and trans_content like '%${content}%'</when> <when test='startDate != null'>and insert_date &gt;='${startDate}'</when> <when test='endDate != null'>and insert_date &lt;='${endDate}'</when> order by insert_date desc</script>")
	Integer recordCount(@Param("content")String content, @Param("startDate")String startDate, @Param("endDate")String endDate);
}
