package zhd.oa.middleware.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 全局变量
public class GlobalVariable {
	public static String CURRENTPROFILE = "dev";
	public static final List<String> COMMETHODS = new ArrayList<>(
			Arrays.asList("wait", "equals", "toString", "hashCode", "getClass", "notify", "notifyAll"));
}
