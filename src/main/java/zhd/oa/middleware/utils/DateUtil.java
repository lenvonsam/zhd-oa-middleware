package zhd.oa.middleware.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static DateUtil instance = null;

	private DateUtil() {
	}

	public static DateUtil shareInstance() {
		if (instance == null)
			instance = new DateUtil();
		return instance;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Date str2Date(String dateStr) throws Exception {
		return sdf.parse(dateStr);
	}

	public Date str2Date(String dateStr, String pattern) throws Exception {
		sdf.applyPattern(pattern);
		return sdf.parse(dateStr);
	}
}
