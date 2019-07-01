package zhd.oa.middleware.model;
/**
 * erp上的数据
 * 销量、高卖和变化量
 * @author conqueror
 *
 */
public class KpiData {
	
	private String oneName;
	private String MM;
	private Double oneWeight;
	private Double weightChange;
	private Double oneMoney;
	private Double moneyChange;
	public String getOneName() {
		return oneName;
	}
	public void setOneName(String oneName) {
		this.oneName = oneName;
	}
	public String getMM() {
		return MM;
	}
	public void setMM(String mM) {
		MM = mM;
	}
	public Double getOneWeight() {
		return oneWeight;
	}
	public void setOneWeight(Double oneWeight) {
		this.oneWeight = oneWeight;
	}
	public Double getWeightChange() {
		return weightChange;
	}
	public void setWeightChange(Double weightChange) {
		this.weightChange = weightChange;
	}
	public Double getOneMoney() {
		return oneMoney;
	}
	public void setOneMoney(Double oneMoney) {
		this.oneMoney = oneMoney;
	}
	public Double getMoneyChange() {
		return moneyChange;
	}
	public void setMoneyChange(Double moneyChange) {
		this.moneyChange = moneyChange;
	}
	@Override
	public String toString() {
		return "KpiData [oneName=" + oneName + ", MM=" + MM + ", oneWeight=" + oneWeight + ", weightChange="
				+ weightChange + ", oneMoney=" + oneMoney + ", moneyChange=" + moneyChange + "]";
	}
	
	

}
