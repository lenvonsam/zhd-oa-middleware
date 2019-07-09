package zhd.oa.middleware.model;
/**
 * 查询Erp上的结果，是实时的;年月,int月份,Erp人员code,Erp账号,部门code,部门名称,总销量,高卖
 * @author conqueror
 *
 */
public class SaleDatas {
	/**
	 * 年月
	 */
	private String yf; 
	/**
	 * int月份
	 */
	private int mm; 
	/**
	 * Erp人员code
	 */
	private String emp; 
	/**
	 * Erp账号
	 */
	private String ename; 
	/**
	 * 部门code
	 */
	private String bm; 
	/**
	 * 部门名称
	 */
	private String dname;
	
	/**
	 * 总销量
	 */
	private double zxl; 
	/**
	 * 高卖
	 */
	private double gm;
	
	public String getYf() {
		return yf;
	}
	public void setYf(String yf) {
		this.yf = yf;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public double getZxl() {
		return zxl;
	}
	public void setZxl(double zxl) {
		this.zxl = zxl;
	}
	public double getGm() {
		return gm;
	}
	public void setGm(double gm) {
		this.gm = gm;
	}
	@Override
	public String toString() {
		return "SaleDatas [yf=" + yf + ", mm=" + mm + ", emp=" + emp + ", ename=" + ename + ", bm=" + bm + ", dname="
				+ dname + ", zxl=" + zxl + ", gm=" + gm + "]";
	}
	
	
}
