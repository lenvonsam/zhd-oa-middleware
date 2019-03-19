package zhd.oa.middleware.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class HttpUtil {
	public static String ERPPROXYURL = "";
	// public static String ERPPROXYURL = "http://172.16.120.232:8980/eep";
	// 测试地址
	// public static String ERPPROXYURL = "http://192.168.80.147:8980/eep";
	// 正式环境
	// public static String ERPPROXYURL = "http://192.168.20.53/eep";
	private static HttpUtil obj = null;

	private HttpUtil() {
	}

	public static HttpUtil shareInstance() {
		if (obj == null) {
			obj = new HttpUtil();
		}
		return obj;
	}

	public String doGet(String url) {
		return doGet(url, "utf-8");
	}

	public String doGet(String url, String charset) {
		String result = "";
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		BufferedReader reader = null;
		InputStreamReader streamReader = null;
		try {
			URL connUrl = new URL(url);
			conn = (HttpURLConnection) connUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			if (conn.getResponseCode() >= 300)
				throw new Exception(
						"HTTP Request is not success, Response code is" + String.valueOf(conn.getResponseCode()));
			inputStream = conn.getInputStream();
			streamReader = new InputStreamReader(inputStream, charset);
			reader = new BufferedReader(streamReader);
			StringBuffer strBuffer = new StringBuffer();
			String r = "";
			while ((r = reader.readLine()) != null) {
				strBuffer.append(r);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (streamReader != null) {
				try {
					streamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (conn != null)
				conn.disconnect();
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public String doErpPost(String url, String params) {
		String resp = doPost(url, params, "utf-8", "gbk");
		return URLDecoder.decode(resp);
	}

	public String doPost(String url, String params) {
		return doPost(url, params, "utf-8");
	}

	public String doPost(String url, String params, String charset) {
		return doPost(url, params, charset, charset);
	}

	public String doPost(String url, String params, String inset, String outset) {
		StringBuffer strBuff = new StringBuffer();
		String result = "";
		HttpURLConnection conn = null;
		PrintWriter out = null;
		InputStreamReader streamReader = null;
		BufferedReader reader = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), inset));
			// TODO 加入日志
			// writeLog("post params:>>>" + params);
			out.print(params);
			out.flush();
			streamReader = new InputStreamReader(conn.getInputStream(), outset);
			reader = new BufferedReader(streamReader);
			String r = "";
			while ((r = reader.readLine()) != null) {
				strBuff.append(r);
			}
			result = strBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (streamReader != null) {
				try {
					streamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null)
				conn.disconnect();
		}
		return result;
	}
}
