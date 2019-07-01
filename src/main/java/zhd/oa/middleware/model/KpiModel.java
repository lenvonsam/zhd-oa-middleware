package zhd.oa.middleware.model;

import java.math.BigDecimal;

public class KpiModel {
	/**
	 * 
	 */
	private String yyyy;
	private String kpiTypeDt;
	private String kpiEmp;
	/**
	 * 业务员销量
	 */
	private BigDecimal erpWeight;
	/**
	 * 业务员高卖
	 */
	private BigDecimal erpMoney;
	/**
	 * 采购本月平均库存
	 */
	private BigDecimal mmAvgStore;
	
	public String getYyyy() {
		return yyyy;
	}
	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}
	public String getKpiTypeDt() {
		return kpiTypeDt;
	}
	public void setKpiTypeDt(String kpiTypeDt) {
		this.kpiTypeDt = kpiTypeDt;
	}
	public String getKpiEmp() {
		return kpiEmp;
	}
	public void setKpiEmp(String kpiEmp) {
		this.kpiEmp = kpiEmp;
	}
	public BigDecimal getErpWeight() {
		return erpWeight;
	}
	public void setErpWeight(BigDecimal erpWeight) {
		this.erpWeight = erpWeight;
	}
	public BigDecimal getErpMoney() {
		return erpMoney;
	}
	public void setErpMoney(BigDecimal erpMoney) {
		this.erpMoney = erpMoney;
	}
	public BigDecimal getMmAvgStore() {
		return mmAvgStore;
	}
	public void setMmAvgStore(BigDecimal mmAvgStore) {
		this.mmAvgStore = mmAvgStore;
	}
	@Override
	public String toString() {
		return "KpiModel [yyyy=" + yyyy + ", kpiTypeDt=" + kpiTypeDt + ", kpiEmp=" + kpiEmp 
				+ ", erpWeight=" + erpWeight + ", erpMoney=" + erpMoney + ", mmAvgStore="
				+ mmAvgStore + "]";
	}
	

	
}
