package zhd.oa.middleware.service;

import java.util.List;

import zhd.oa.middleware.mapper.PropertyMapper;
import zhd.oa.middleware.model.PropertyBasic;

public class PropertyService extends BaseService{

	private PropertyMapper propertyMapper;
	
	public List<PropertyBasic> getPropertyBasic(String nos){
		
		List<PropertyBasic> propertyBasics= null;
		
		try {
			session = openSession();
			propertyMapper = session.getMapper(PropertyMapper.class);
			propertyBasics = propertyMapper.getPropertyBasicByNos(nos);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		
		return propertyBasics;
		
	}
	
	
}
