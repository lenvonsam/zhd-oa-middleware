package zhd.oa.middleware.model;
/**
 * 2019-07-18
 * 已出库提单
 * @author conqueror
 * 
 *
 */
public class OutSbills {
	
	private String billcode;    
	private String billbatch;
	private String customer;   
	private String org;
	private String pntree;       
	private String mat;       
	private String spec;       
	private String mlength;       
	private String area;       
	private String zlfw;       
	private String gcfw;       
	private String metering;       
	private String warehouse;       
	private String nums;
	
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
	public String getBillbatch() {
		return billbatch;
	}
	public void setBillbatch(String billbatch) {
		this.billbatch = billbatch;
	}
	public String getCustomer() {
		return customer;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPntree() {
		return pntree;
	}
	public void setPntree(String pntree) {
		this.pntree = pntree;
	}
	public String getMat() {
		return mat;
	}
	public void setMat(String mat) {
		this.mat = mat;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getMlength() {
		return mlength;
	}
	public void setMlength(String mlength) {
		this.mlength = mlength;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getZlfw() {
		return zlfw;
	}
	public void setZlfw(String zlfw) {
		this.zlfw = zlfw;
	}
	public String getGcfw() {
		return gcfw;
	}
	public void setGcfw(String gcfw) {
		this.gcfw = gcfw;
	}
	public String getMetering() {
		return metering;
	}
	public void setMetering(String metering) {
		this.metering = metering;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	@Override
	public String toString() {
		return "OutSbills [billcode=" + billcode + ", billbatch=" + billbatch + ", customer=" + customer + ", org="
				+ org + ", pntree=" + pntree + ", mat=" + mat + ", spec=" + spec + ", mlength=" + mlength + ", area="
				+ area + ", zlfw=" + zlfw + ", gcfw=" + gcfw + ", metering=" + metering + ", warehouse=" + warehouse
				+ ", nums=" + nums + "]";
	}
	
	
	
	
	
}
