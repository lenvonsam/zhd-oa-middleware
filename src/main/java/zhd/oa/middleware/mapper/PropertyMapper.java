package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.PropertyBasic;

public interface PropertyMapper {

	@Select(" select t.id property_id,property_no,goods_name, goods_model ,goods_unit, property_count ,t2.area store_place ,t1.departmentname property_dept "
			+ " from  uf_property t"
			+ " left join hrmdepartment t1 on t.property_dept = t1.id"
			+ " left join uf_store_place t2 on t.store_place = t2.id "
			+ " where t.id in ( ${nos} ) ")
	public List<PropertyBasic> getPropertyBasicByNos(@Param("nos")String nos);
	
}
