package zhd.oa.middleware.model;

public class KpiModel {
	/**
	 * 
	 */
	private String yyyy;
	private String kpiTypeDt;
	private String kpiEmp;
	private String empCode;
	private String deptCode;
	/**
	 * 业务员销量
	 */
	private double erpWeight;
	/**
	 * 业务员高卖
	 */
	private double erpMoney;
	/**
	 * 采购本月平均库存
	 */
	private double mmAvgStore;
	
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public double getErpWeight() {
		return erpWeight;
	}
	public void setErpWeight(double erpWeight) {
		this.erpWeight = erpWeight;
	}
	public double getErpMoney() {
		return erpMoney;
	}
	public void setErpMoney(double erpMoney) {
		this.erpMoney = erpMoney;
	}
	public double getMmAvgStore() {
		return mmAvgStore;
	}
	public void setMmAvgStore(double mmAvgStore) {
		this.mmAvgStore = mmAvgStore;
	}
	@Override
	public String toString() {
		return "KpiModel [yyyy=" + yyyy + ", kpiTypeDt=" + kpiTypeDt + ", kpiEmp=" + kpiEmp + ", empCode=" + empCode
				+ ", deptCode=" + deptCode + ", erpWeight=" + erpWeight + ", erpMoney=" + erpMoney + ", mmAvgStore="
				+ mmAvgStore + "]";
	}
	

	

	
}
