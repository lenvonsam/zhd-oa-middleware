package zhd.oa.middleware.model;
/**
 * 费用报销 2019-09-18
 * @author conqueror
 *
 */
public class ExpenseReimburse {

	private int userid;
	private String lastname;
	private String applyDate;
	private int belongDeptId;
	private String belongDeptName;
	private int paymethodId;
	private String paymenthodName;
	private String remk;
	private String expenseType;
	private double expenseMoney;
	private String expenseDate;
	private String expenseDt;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	public String getBelongDeptName() {
		return belongDeptName;
	}
	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}
	public int getPaymethodId() {
		return paymethodId;
	}
	public void setPaymethodId(int paymethodId) {
		this.paymethodId = paymethodId;
	}
	public String getPaymenthodName() {
		return paymenthodName;
	}
	public void setPaymenthodName(String paymenthodName) {
		this.paymenthodName = paymenthodName;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public double getExpenseMoney() {
		return expenseMoney;
	}
	public void setExpenseMoney(double expenseMoney) {
		this.expenseMoney = expenseMoney;
	}
	public String getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getExpenseDt() {
		return expenseDt;
	}
	public void setExpenseDt(String expenseDt) {
		this.expenseDt = expenseDt;
	}
	@Override
	public String toString() {
		return "ExpenseReimburse [userid=" + userid + ", lastname=" + lastname + ", applyDate=" + applyDate
				+ ", belongDeptId=" + belongDeptId + ", belongDeptName=" + belongDeptName + ", paymethodId="
				+ paymethodId + ", paymenthodName=" + paymenthodName + ", remk=" + remk + ", expenseType=" + expenseType
				+ ", expenseMoney=" + expenseMoney + ", expenseDate=" + expenseDate + ", expenseDt=" + expenseDt + "]";
	}
	
	
}
