package zhd.oa.middleware.model;


public class EmpOA {
	
	private int id ;
	private String lastname ;
	private int job;
	private int dept;
	public int getUid() {
		return id;
	}
	public void setUid(int uid) {
		this.id = uid;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public int getDept() {
		return dept;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	@Override
	public String toString() {
		return "EmpOA [id=" + id + ", lastname=" + lastname + ", job=" + job + ", dept=" + dept + "]";
	}


	

}
