package zhd.oa.middleware.model;

public class KpiTotalChangeOne {
	
	/**
	 * 
	 * 业务员名
	 */
	private String oneAccount;
	/**
	 * 姓名
	 */
	private String oneName;
	/**
	 * 月销量
	 */
	private Double oneWeight;
	/**
	 * 月高卖
	 */
	private Double oneMoney;
	/**
	 * 统计日期
	 */
	private String oneGetDate;
	/**
	 * 实际月份
	 */
	private String oneMM;
	
	public String getOneAccount() {
		return oneAccount;
	}
	public void setOneAccount(String oneAccount) {
		this.oneAccount = oneAccount;
	}
	public String getOneName() {
		return oneName;
	}
	public void setOneName(String oneName) {
		this.oneName = oneName;
	}
	public Double getOneWeight() {
		return oneWeight;
	}
	public void setOneWeight(Double oneWeight) {
		this.oneWeight = oneWeight;
	}
	public Double getOneMoney() {
		return oneMoney;
	}
	public void setOneMoney(Double oneMoney) {
		this.oneMoney = oneMoney;
	}
	public String getOneGetDate() {
		return oneGetDate;
	}
	public void setOneGetDate(String oneGetDate) {
		this.oneGetDate = oneGetDate;
	}
	public String getOneMM() {
		return oneMM;
	}
	public void setOneMM(String oneMM) {
		this.oneMM = oneMM;
	}
	@Override
	public String toString() {
		return "KpiTotalChangeOne [oneAccount=" + oneAccount + ", oneName=" + oneName + ", oneWeight=" + oneWeight
				+ ", oneMoney=" + oneMoney + ", oneGetDate=" + oneGetDate + ", oneMM=" + oneMM + "]";
	}
	
	
}
