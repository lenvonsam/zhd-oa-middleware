package zhd.oa.middleware.utils;

public class KpiUtil {
	
	private static KpiUtil instance = null;

	private KpiUtil() {
	}

	public static KpiUtil shareInstance() {
		if (instance == null) {
			instance = new KpiUtil();
		}
		return instance;
	}
	
	 
	
	
}
