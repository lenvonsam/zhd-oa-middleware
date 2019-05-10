package zhd.oa.middleware.model;

public class LoansInterest {

	private java.math.BigDecimal id;
	private java.math.BigDecimal mainid;
	private java.math.BigDecimal periodNum;
	private String periods;
	private String interestFrom;
	private String interestTo;
	private java.math.BigDecimal interestDays;
	private java.math.BigDecimal interestAmount;
	private java.math.BigDecimal paidAmount;
	private String periodsStatus;
	
	public java.math.BigDecimal getId() {
		return id;
	}
	public void setId(java.math.BigDecimal id) {
		this.id = id;
	}
	public java.math.BigDecimal getMainid() {
		return mainid;
	}
	public void setMainid(java.math.BigDecimal mainid) {
		this.mainid = mainid;
	}
	public java.math.BigDecimal getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(java.math.BigDecimal periodNum) {
		this.periodNum = periodNum;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getInterestFrom() {
		return interestFrom;
	}
	public void setInterestFrom(String interestFrom) {
		this.interestFrom = interestFrom;
	}
	public String getInterestTo() {
		return interestTo;
	}
	public void setInterestTo(String interestTo) {
		this.interestTo = interestTo;
	}
	public java.math.BigDecimal getInterestDays() {
		return interestDays;
	}
	public void setInterestDays(java.math.BigDecimal interestDays) {
		this.interestDays = interestDays;
	}
	public java.math.BigDecimal getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(java.math.BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}
	public java.math.BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(java.math.BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getPeriodsStatus() {
		return periodsStatus;
	}
	public void setPeriodsStatus(String periodsStatus) {
		this.periodsStatus = periodsStatus;
	}
	@Override
	public String toString() {
		return "LoansInterest [id=" + id + ", mainid=" + mainid + ", periodNum=" + periodNum + ", periods=" + periods
				+ ", interestFrom=" + interestFrom + ", interestTo=" + interestTo + ", interestDays=" + interestDays
				+ ", interestAmount=" + interestAmount + ", paidAmount=" + paidAmount + ", periodsStatus="
				+ periodsStatus + "]";
	}


	
}
