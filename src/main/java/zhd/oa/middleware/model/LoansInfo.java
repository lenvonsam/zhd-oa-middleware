package zhd.oa.middleware.model;

import java.math.BigDecimal;

public class LoansInfo {
	
	private BigDecimal id;
	private BigDecimal requestid;
	private String flowNo;//流程编号
	private String applicant;//申请人员
	private String applyDept;//申请部门
	private String applyDate;//申请日期
	private String applyRemk;//备注
	private String loansOrg;//贷款单位
	private String loansAccount;//贷款账号
	private String loansBank;//贷款银行
	private BigDecimal loansMoney;//贷款金额
	private String loansDateFrom;//开始日期
	private String loansDateTo;//结束日期
	private BigDecimal loanRate;//贷款年利率
	private BigDecimal discountDate;//扣息日期
	private String discountAccount;//扣息银行账号
	private BigDecimal supervisionFee;//监管费
	private BigDecimal premium;//保险费
	private String finished;//是否完成
	private String loanstatus;//状态
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getRequestid() {
		return requestid;
	}
	public void setRequestid(BigDecimal requestid) {
		this.requestid = requestid;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplyRemk() {
		return applyRemk;
	}
	public void setApplyRemk(String applyRemk) {
		this.applyRemk = applyRemk;
	}
	public String getLoansOrg() {
		return loansOrg;
	}
	public void setLoansOrg(String loansOrg) {
		this.loansOrg = loansOrg;
	}
	public String getLoansAccount() {
		return loansAccount;
	}
	public void setLoansAccount(String loansAccount) {
		this.loansAccount = loansAccount;
	}
	public String getLoansBank() {
		return loansBank;
	}
	public void setLoansBank(String loansBank) {
		this.loansBank = loansBank;
	}
	public BigDecimal getLoansMoney() {
		return loansMoney;
	}
	public void setLoansMoney(BigDecimal loansMoney) {
		this.loansMoney = loansMoney;
	}
	public String getLoansDateFrom() {
		return loansDateFrom;
	}
	public void setLoansDateFrom(String loansDateFrom) {
		this.loansDateFrom = loansDateFrom;
	}
	public String getLoansDateTo() {
		return loansDateTo;
	}
	public void setLoansDateTo(String loansDateTo) {
		this.loansDateTo = loansDateTo;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public BigDecimal getDiscountDate() {
		return discountDate;
	}
	public void setDiscountDate(BigDecimal discountDate) {
		this.discountDate = discountDate;
	}
	public String getDiscountAccount() {
		return discountAccount;
	}
	public void setDiscountAccount(String discountAccount) {
		this.discountAccount = discountAccount;
	}
	public BigDecimal getSupervisionFee() {
		return supervisionFee;
	}
	public void setSupervisionFee(BigDecimal supervisionFee) {
		this.supervisionFee = supervisionFee;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	public String getLoanstatus() {
		return loanstatus;
	}
	public void setLoanstatus(String loanstatus) {
		this.loanstatus = loanstatus;
	}
	@Override
	public String toString() {
		return "LoansInfo [id=" + id + ", requestid=" + requestid + ", flowNo=" + flowNo + ", applicant=" + applicant
				+ ", applyDept=" + applyDept + ", applyDate=" + applyDate + ", applyRemk=" + applyRemk + ", loansOrg="
				+ loansOrg + ", loansAccount=" + loansAccount + ", loansBank=" + loansBank + ", loansMoney="
				+ loansMoney + ", loansDateFrom=" + loansDateFrom + ", loansDateTo=" + loansDateTo + ", loanRate="
				+ loanRate + ", discountDate=" + discountDate + ", discountAccount=" + discountAccount
				+ ", supervisionFee=" + supervisionFee + ", premium=" + premium + ", finished=" + finished
				+ ", loanstatus=" + loanstatus + "]";
	}
	
	


	
}
