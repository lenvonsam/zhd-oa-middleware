package zhd.oa.middleware.model;
/**
 * 费用报销主表数据 2019-09-18
 * @author conqueror
 *
 */
public class ExpenseReimburseMain {
	/**
	 * 申请人id
	 */
	private int userid;
	/**
	 * 申请日期
	 */
	private String applyDate;
	/**
	 * 申请部门id
	 */
	private int belongDeptId;
	/**
	 * 支付方式id
	 */
	private int paymethodId;
	/**
	 * 申请备注
	 */
	private String remk;
	/**
	 * 报销总额
	 */
	private double doMoney;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public int getBelongDeptId() {
		return belongDeptId;
	}
	public void setBelongDeptId(int belongDeptId) {
		this.belongDeptId = belongDeptId;
	}
	public int getPaymethodId() {
		return paymethodId;
	}
	public void setPaymethodId(int paymethodId) {
		this.paymethodId = paymethodId;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public double getDoMoney() {
		return doMoney;
	}
	public void setDoMoney(double doMoney) {
		this.doMoney = doMoney;
	}
	@Override
	public String toString() {
		return "ExpenseReimburseMain [userid=" + userid + ", applyDate=" + applyDate + ", belongDeptId=" + belongDeptId
				+ ", paymethodId=" + paymethodId + ", remk=" + remk + ", doMoney=" + doMoney + "]";
	}
	
	
}
