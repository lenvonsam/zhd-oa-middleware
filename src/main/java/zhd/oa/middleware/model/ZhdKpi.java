package zhd.oa.middleware.model;
/**
 * 智恒达kpi，之前的数据查不全，重写一个
 * @author conqueror
 *
 */
public class ZhdKpi {
	//基本信息
	private String  empid;   
	private String  lastname;   
	private String  seclevel;   
	private String  createdate;   
	private String  departmentid;   
	private String  departmentname;   
	private String  showorder;   
	private String  jobtitle;   
	private String  jobtitlename;   
	private String  oneaccount;   
	private String  emperp;   
	private String  onedeptcode;   
	private String  kpitype;   
	private String  yyyy;   
	private String  mm_;   
	private String  mm; 
	private String mmstr;
	
	//销量
	private double  oneweight;   
	private double  weightchange;   
	private double  otherweight;   
	private double  realweight;   
	private double  taskweight;   
	private double  weightkpi; 
	//高卖
	private double  onemoney;   
	private double  moneychange;   
	private double  othermoney;   
	private double  realmoney;   
	private double  taskmoney;   
	private double  moneykpi; 
	//采购库存
	private double  avgprice;   
	private double  mmassessprice;   
	private double  mmavgstore;   
	private double  storerange;   
	private double  assessweight;   
	private double  kpistore;
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSeclevel() {
		return seclevel;
	}
	public void setSeclevel(String seclevel) {
		this.seclevel = seclevel;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getShoworder() {
		return showorder;
	}
	public void setShoworder(String showorder) {
		this.showorder = showorder;
	}
	public String getJobtitle() {
		return jobtitle;
	}
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	public String getJobtitlename() {
		return jobtitlename;
	}
	public void setJobtitlename(String jobtitlename) {
		this.jobtitlename = jobtitlename;
	}
	public String getOneaccount() {
		return oneaccount;
	}
	public void setOneaccount(String oneaccount) {
		this.oneaccount = oneaccount;
	}
	public String getEmperp() {
		return emperp;
	}
	public void setEmperp(String emperp) {
		this.emperp = emperp;
	}
	public String getOnedeptcode() {
		return onedeptcode;
	}
	public void setOnedeptcode(String onedeptcode) {
		this.onedeptcode = onedeptcode;
	}
	public String getKpitype() {
		return kpitype;
	}
	public void setKpitype(String kpitype) {
		this.kpitype = kpitype;
	}
	public String getYyyy() {
		return yyyy;
	}
	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}
	public String getMm_() {
		return mm_;
	}
	public void setMm_(String mm_) {
		this.mm_ = mm_;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getMmstr() {
		return mmstr;
	}
	public void setMmstr(String mmstr) {
		this.mmstr = mmstr;
	}
	public double getOneweight() {
		return oneweight;
	}
	public void setOneweight(double oneweight) {
		this.oneweight = oneweight;
	}
	public double getWeightchange() {
		return weightchange;
	}
	public void setWeightchange(double weightchange) {
		this.weightchange = weightchange;
	}
	public double getOtherweight() {
		return otherweight;
	}
	public void setOtherweight(double otherweight) {
		this.otherweight = otherweight;
	}
	public double getRealweight() {
		return realweight;
	}
	public void setRealweight(double realweight) {
		this.realweight = realweight;
	}
	public double getTaskweight() {
		return taskweight;
	}
	public void setTaskweight(double taskweight) {
		this.taskweight = taskweight;
	}
	public double getWeightkpi() {
		return weightkpi;
	}
	public void setWeightkpi(double weightkpi) {
		this.weightkpi = weightkpi;
	}
	public double getOnemoney() {
		return onemoney;
	}
	public void setOnemoney(double onemoney) {
		this.onemoney = onemoney;
	}
	public double getMoneychange() {
		return moneychange;
	}
	public void setMoneychange(double moneychange) {
		this.moneychange = moneychange;
	}
	public double getOthermoney() {
		return othermoney;
	}
	public void setOthermoney(double othermoney) {
		this.othermoney = othermoney;
	}
	public double getRealmoney() {
		return realmoney;
	}
	public void setRealmoney(double realmoney) {
		this.realmoney = realmoney;
	}
	public double getTaskmoney() {
		return taskmoney;
	}
	public void setTaskmoney(double taskmoney) {
		this.taskmoney = taskmoney;
	}
	public double getMoneykpi() {
		return moneykpi;
	}
	public void setMoneykpi(double moneykpi) {
		this.moneykpi = moneykpi;
	}
	public double getAvgprice() {
		return avgprice;
	}
	public void setAvgprice(double avgprice) {
		this.avgprice = avgprice;
	}
	public double getMmassessprice() {
		return mmassessprice;
	}
	public void setMmassessprice(double mmassessprice) {
		this.mmassessprice = mmassessprice;
	}
	public double getMmavgstore() {
		return mmavgstore;
	}
	public void setMmavgstore(double mmavgstore) {
		this.mmavgstore = mmavgstore;
	}
	public double getStorerange() {
		return storerange;
	}
	public void setStorerange(double storerange) {
		this.storerange = storerange;
	}
	public double getAssessweight() {
		return assessweight;
	}
	public void setAssessweight(double assessweight) {
		this.assessweight = assessweight;
	}
	public double getKpistore() {
		return kpistore;
	}
	public void setKpistore(double kpistore) {
		this.kpistore = kpistore;
	}
	@Override
	public String toString() {
		return "ZhdKpi [empid=" + empid + ", lastname=" + lastname + ", seclevel=" + seclevel + ", createdate="
				+ createdate + ", departmentid=" + departmentid + ", departmentname=" + departmentname + ", showorder="
				+ showorder + ", jobtitle=" + jobtitle + ", jobtitlename=" + jobtitlename + ", oneaccount=" + oneaccount
				+ ", emperp=" + emperp + ", onedeptcode=" + onedeptcode + ", kpitype=" + kpitype + ", yyyy=" + yyyy
				+ ", mm_=" + mm_ + ", mm=" + mm + ", mmstr=" + mmstr + ", oneweight=" + oneweight + ", weightchange="
				+ weightchange + ", otherweight=" + otherweight + ", realweight=" + realweight + ", taskweight="
				+ taskweight + ", weightkpi=" + weightkpi + ", onemoney=" + onemoney + ", moneychange=" + moneychange
				+ ", othermoney=" + othermoney + ", realmoney=" + realmoney + ", taskmoney=" + taskmoney + ", moneykpi="
				+ moneykpi + ", avgprice=" + avgprice + ", mmassessprice=" + mmassessprice + ", mmavgstore="
				+ mmavgstore + ", storerange=" + storerange + ", assessweight=" + assessweight + ", kpistore="
				+ kpistore + "]";
	}

}
