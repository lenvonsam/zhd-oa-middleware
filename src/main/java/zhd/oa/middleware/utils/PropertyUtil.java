package zhd.oa.middleware.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	private static PropertyUtil instance = null;

	private PropertyUtil() {
	}

	public static PropertyUtil shareInstance() {
		if (instance == null) {
			instance = new PropertyUtil();
		}
		return instance;
	}

	public Properties initProperties(String filePath) throws Exception {
		Properties props = new Properties();

		String path = this.getClass().getResource("/").getPath();
		InputStream in = new BufferedInputStream(new FileInputStream(path + filePath));
		props.load(in);
		// props.load(lassLoader.getSystemResourceAsStream(path + filePath));
		return props;
	}

	public String getPropertyValue(String filePath, String key) throws Exception {
		Properties props = initProperties(filePath);
		return props == null ? null : props.get(key).toString();
	}
}
