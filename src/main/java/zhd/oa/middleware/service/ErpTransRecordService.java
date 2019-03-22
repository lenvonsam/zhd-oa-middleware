package zhd.oa.middleware.service;

import zhd.oa.middleware.mapper.ErpTransRecordMapper;
import zhd.oa.middleware.model.ErpTransRecord;
import zhd.oa.middleware.utils.HttpUtil;

import java.util.HashMap;
import java.util.List;

public class ErpTransRecordService extends BaseService {

	private ErpTransRecordMapper mapper = null;

	public ErpTransRecord findOne(Integer id) {
		try {
			session = openSession();
			mapper = session.getMapper(ErpTransRecordMapper.class);
			ErpTransRecord m = mapper.findOne(25);
			System.out.println("m name:>>" + m.getFlow_name());
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

    public void findRecordList(HashMap<String, Object> result, Integer firstIndex, Integer lastIndex, String content, String startDate, String endDate) {
        System.out.println("firstIndex:" + firstIndex + ",lastIndex:" + lastIndex + ",content:" + content + ",startDate:" + startDate + ",endDate:" + endDate);
	    try {
            session = openSession();
            mapper = session.getMapper(ErpTransRecordMapper.class);
            List<ErpTransRecord> list = mapper.findRecordList(firstIndex, lastIndex, content, startDate, endDate);
            Integer count = mapper.recordCount(content, startDate, endDate);
            result.put("list", list);
            result.put("count", count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession();
        }
    }

    public String send2Erp(){
        String str = "{\"org_name\":\"江苏智恒达投资集团有限公司\",\"bank_name\":\"徽商银行\",\"company_name\":\"鑫盛彩钢结构\",\"pay_type\":\"电汇\",\"money\":25000.0,\"pay_time\":\"2019-03-07\",\"bank_account\":\"1561101021000212317\",\"originator\":\"\"}";
        System.out.println(">>>" + str);
        String result = "";
        try {
            result = HttpUtil.shareInstance().doErpPost(HttpUtil.ERPPROXYURL + "/interfacesAjax!savePayment.htm" , "body=" + str);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(">>>" + result);
        return result;
    }

	public String send2Erp(String content){
        System.out.println("content>>>" + content);
        String result = "";
        try {
            result = HttpUtil.shareInstance().doErpPost(HttpUtil.ERPPROXYURL + "/interfacesAjax!savePayment.htm" , "body=" + content);
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("result>>>" + result);
        return result;
    }
}
