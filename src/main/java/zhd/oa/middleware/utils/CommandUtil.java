package zhd.oa.middleware.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandUtil {
	private static CommandUtil instance = null;
	// FIXME 根据实际shell脚本地址修改
	private static final String SHELLPATH = "/Users/juny/Downloads/mkpwd.sh";

	private CommandUtil() {
	}

	public static CommandUtil shareInstance() {
		if (instance == null)
			instance = new CommandUtil();
		return instance;
	}

	public static String executeShell(String shpath, String params) {
		String result = "";
		try {
			String paramPath = shpath;
			if (!(params == null || params.length() == 0)) {
				paramPath += " " + params.trim().toUpperCase();
			}
			Process ps = Runtime.getRuntime().exec(paramPath);
			ps.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result = "error:>>" + e.getMessage();
		}
		return result;
	}

	public static String executeShell() {
		return executeShell(SHELLPATH, null);
	}

	public static String executeShell(String params) {
		return executeShell(SHELLPATH, params);
	}
}
