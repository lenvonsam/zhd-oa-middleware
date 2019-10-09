package zhd.oa.middleware.model;
/**
 * 费用报销明细数据 2019-09-18
 * @author conqueror
 *
 */
public class ExpenseReimburseDetail {
	/**
	 * 费用类型
	 */
	private String expenseType;
	/**
	 * 费用金额
	 */
	private double expenseMoney;
	/**
	 * 费用日期
	 */
	private String expenseDate;
	/**
	 * 费用内容
	 */
	private String expenseDt;
	
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
		return "ExpenseReimburseDetail [expenseType=" + expenseType + ", expenseMoney=" + expenseMoney
				+ ", expenseDate=" + expenseDate + ", expenseDt=" + expenseDt + "]";
	}
	
	
	
}
