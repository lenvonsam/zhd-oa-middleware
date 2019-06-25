package zhd.oa.middleware.model;

import java.math.BigDecimal;

/**
 * oa定时通知
 * @author conqueror
 *@since 2019-06-05
 */
public class CronJob {
	/**
	 * 需要通知的OA人员ID
	 */
	private BigDecimal userid;
	
	/**
	 * OA用户名
	 */
	private String userName;

	/**
	 * 需要通知的信息
	 */
	private String msg;
	
	public CronJob(BigDecimal userid, String msg) {
		super();
		this.userid = userid;
		this.msg = msg;
	}

	public CronJob(BigDecimal userid, String userName, String msg) {
		super();
		this.userid = userid;
		this.userName = userName;
		this.msg = msg;
	}

	public BigDecimal getUserid() {
		return userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CronJob [userid=" + userid + ", userName=" + userName + ", msg=" + msg + "]";
	}

	

	
}
