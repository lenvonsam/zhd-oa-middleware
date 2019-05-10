package zhd.oa.middleware.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zhd.oa.middleware.model.AcceptanceBill;

public interface AcceptanceBillMapper {
	
	@Select("select id, requestid, flowno, docno, docdatef, docdatet, docmoney, docfromemp, docfrombank, docdeposit from formtable_main_435 where status = 1 ")
	public List<AcceptanceBill> queryAcceptanceBill();
}
