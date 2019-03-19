package zhd.oa.middleware.mapper;

import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.ErpTransRecord;

public interface ErpTransRecordMapper {

	@Select("select id, flow_name, insert_date, trans_content from uf_erp_sale_record where id = #{id}")
	public ErpTransRecord findOne(Integer id);
}
