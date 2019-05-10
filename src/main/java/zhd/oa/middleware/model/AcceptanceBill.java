package zhd.oa.middleware.model;

import java.math.BigDecimal;

public class AcceptanceBill {

	private	String	id;
	private	String	requestid;
	private	String	flowno;//流程编号
	private	String	docno;//票号
	private	String	docdatef;//开始日期
	private	String	docdatet;//结束日期
	private	BigDecimal	docmoney;//汇票金额
	private	String	docfromemp;//出票人
	private	String	docfrombank;//出票行
	private	BigDecimal	docdeposit;//保证金

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getFlowno() {
		return flowno;
	}
	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getDocdatef() {
		return docdatef;
	}
	public void setDocdatef(String docdatef) {
		this.docdatef = docdatef;
	}
	public String getDocdatet() {
		return docdatet;
	}
	public void setDocdatet(String docdatet) {
		this.docdatet = docdatet;
	}
	public BigDecimal getDocmoney() {
		return docmoney;
	}
	public void setDocmoney(BigDecimal docmoney) {
		this.docmoney = docmoney;
	}
	public String getDocfromemp() {
		return docfromemp;
	}
	public void setDocfromemp(String docfromemp) {
		this.docfromemp = docfromemp;
	}
	public String getDocfrombank() {
		return docfrombank;
	}
	public void setDocfrombank(String docfrombank) {
		this.docfrombank = docfrombank;
	}
	public BigDecimal getDocdeposit() {
		return docdeposit;
	}
	public void setDocdeposit(BigDecimal docdeposit) {
		this.docdeposit = docdeposit;
	}
	@Override
	public String toString() {
		return "AcceptanceBill [id=" + id + ", requestid=" + requestid + ", flowno=" + flowno + ", docno=" + docno
				+ ", docdatef=" + docdatef + ", docdatet=" + docdatet + ", docmoney=" + docmoney + ", docfromemp="
				+ docfromemp + ", docfrombank=" + docfrombank + ", docdeposit=" + docdeposit + "]";
	}
	
	

	
}
