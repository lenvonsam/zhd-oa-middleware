package zhd.oa.middleware.model;

/**
 * 2019-07-11
 * 提成报表，uf_zhdWage
 * @author conqueror
 *
 */
public class WageTable {
	
	private int  departmentid;    
	private int  yyyy;    
	private int  mm;    
	private int  empid;    
	private int  jobtitle;    
	private String  entrydate;    
	private String  fulldate;    
	private double  fullyear;     
	private double  realweight;    
	private double  realmoney;    
	private String  basicassess;    
	private double  wageweight;    
	private double  wagemoney;    
	private double  reduce;    
	private double  total;    
	private String  insertdate;
	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}
	public int getYyyy() {
		return yyyy;
	}
	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public int getJobtitle() {
		return jobtitle;
	}
	public void setJobtitle(int jobtitle) {
		this.jobtitle = jobtitle;
	}
	public String getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}
	public String getFulldate() {
		return fulldate;
	}
	public void setFulldate(String fulldate) {
		this.fulldate = fulldate;
	}
	public double getFullyear() {
		return fullyear;
	}
	public void setFullyear(double fullyear) {
		this.fullyear = fullyear;
	}
	public double getRealweight() {
		return realweight;
	}
	public void setRealweight(double realweight) {
		this.realweight = realweight;
	}
	public double getRealmoney() {
		return realmoney;
	}
	public void setRealmoney(double realmoney) {
		this.realmoney = realmoney;
	}
	public String getBasicassess() {
		return basicassess;
	}
	public void setBasicassess(String basicassess) {
		this.basicassess = basicassess;
	}
	public double getWageweight() {
		return wageweight;
	}
	public void setWageweight(double wageweight) {
		this.wageweight = wageweight;
	}
	public double getWagemoney() {
		return wagemoney;
	}
	public void setWagemoney(double wagemoney) {
		this.wagemoney = wagemoney;
	}
	public double getReduce() {
		return reduce;
	}
	public void setReduce(double reduce) {
		this.reduce = reduce;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getInsertdate() {
		return insertdate;
	}
	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}
	@Override
	public String toString() {
		return "WageTable [departmentid=" + departmentid + ", yyyy=" + yyyy + ", mm=" + mm + ", empid=" + empid
				+ ", jobtitle=" + jobtitle + ", entrydate=" + entrydate + ", fulldate=" + fulldate + ", fullyear="
				+ fullyear + ", realweight=" + realweight + ", realmoney=" + realmoney + ", basicassess=" + basicassess
				+ ", wageweight=" + wageweight + ", wagemoney=" + wagemoney + ", reduce=" + reduce + ", total=" + total
				+ ", insertdate=" + insertdate + "]";
	}
	
	
	
	
	
	
	
	

}
