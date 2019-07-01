package zhd.oa.middleware.model;
/**
 * 变化量
 * @author conqueror
 *
 */
public class KpiTotalChange {

	private String uname;
	private Double weightChange;
	private Double moneyChange;
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Double getWeightChange() {
		return weightChange;
	}
	public void setWeightChange(Double weightChange) {
		this.weightChange = weightChange;
	}
	public Double getMoneyChange() {
		return moneyChange;
	}
	public void setMoneyChange(Double moneyChange) {
		this.moneyChange = moneyChange;
	}
	@Override
	public String toString() {
		return "KpiTotalChange [uname=" + uname + ", weightChange=" + weightChange + ", moneyChange=" + moneyChange
				+ "]";
	}
	
	
	
	
	
}
