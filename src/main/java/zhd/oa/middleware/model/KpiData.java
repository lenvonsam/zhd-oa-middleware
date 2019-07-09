package zhd.oa.middleware.model;
/**
 * erp上的数据
 * 销量、高卖和变化量
 * @author conqueror
 *
 */
public class KpiData {
	
	private String oneaccount; 
	private int hrmid; 
	private String lastname; 
	private int jobtitle; 
	private String jobtitlename; 
	private int yyyy; 
	private String mmstr; 
	private int mm; 
	
	private double weightrate;
	private double moneyrate;
	
	private double oneweight; 
	private double weightchange; 
	private double realweight; 
	private double taskweight; 
	private double weightscore; 
	
	private double onemoney; 
	private double moneychange; 
	private double othermoney;
	private double realmoney; 
	private double taskmoney; 
	private double moneyscore;
	public String getOneaccount() {
		return oneaccount;
	}
	public void setOneaccount(String oneaccount) {
		this.oneaccount = oneaccount;
	}
	public int getHrmid() {
		return hrmid;
	}
	public void setHrmid(int hrmid) {
		this.hrmid = hrmid;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getJobtitle() {
		return jobtitle;
	}
	public void setJobtitle(int jobtitle) {
		this.jobtitle = jobtitle;
	}
	public String getJobtitlename() {
		return jobtitlename;
	}
	public void setJobtitlename(String jobtitlename) {
		this.jobtitlename = jobtitlename;
	}
	public int getYyyy() {
		return yyyy;
	}
	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}
	public String getMmstr() {
		return mmstr;
	}
	public void setMmstr(String mmstr) {
		this.mmstr = mmstr;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public double getWeightrate() {
		return weightrate;
	}
	public void setWeightrate(double weightrate) {
		this.weightrate = weightrate;
	}
	public double getMoneyrate() {
		return moneyrate;
	}
	public void setMoneyrate(double moneyrate) {
		this.moneyrate = moneyrate;
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
	public double getWeightscore() {
		return weightscore;
	}
	public void setWeightscore(double weightscore) {
		this.weightscore = weightscore;
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
	public double getMoneyscore() {
		return moneyscore;
	}
	public void setMoneyscore(double moneyscore) {
		this.moneyscore = moneyscore;
	}
	@Override
	public String toString() {
		return "KpiData [oneaccount=" + oneaccount + ", hrmid=" + hrmid + ", lastname=" + lastname + ", jobtitle="
				+ jobtitle + ", jobtitlename=" + jobtitlename + ", yyyy=" + yyyy + ", mmstr=" + mmstr + ", mm=" + mm
				+ ", weightrate=" + weightrate + ", moneyrate=" + moneyrate + ", oneweight=" + oneweight
				+ ", weightchange=" + weightchange + ", realweight=" + realweight + ", taskweight=" + taskweight
				+ ", weightscore=" + weightscore + ", onemoney=" + onemoney + ", moneychange=" + moneychange
				+ ", othermoney=" + othermoney + ", realmoney=" + realmoney + ", taskmoney=" + taskmoney
				+ ", moneyscore=" + moneyscore + "]";
	}

	
	
	

}
