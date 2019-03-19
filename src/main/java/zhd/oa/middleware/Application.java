package zhd.oa.middleware;

import spark.servlet.SparkApplication;
import zhd.oa.middleware.config.AutoWebConfig;

public class Application implements SparkApplication{

	public static void main(String[] args) throws Exception {
		 AutoWebConfig.autoInit();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		AutoWebConfig.autoInit();
	}
}
