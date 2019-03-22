package zhd.oa.middleware.config;

import zhd.oa.middleware.enums.DefaultProps;
import zhd.oa.middleware.utils.FileUtil;
import zhd.oa.middleware.utils.HttpUtil;
import zhd.oa.middleware.utils.PropertyUtil;

import java.util.Properties;

import static spark.Spark.*;

public class AutoWebConfig {

	// 初始化web application config
	public static void autoInit() {
		try {
			// 读取数据库配置
			Properties globalProperties = PropertyUtil.shareInstance().initProperties(DefaultProps.BOOTFILE.getName());
			// ERP proxy url
			HttpUtil.ERPPROXYURL = globalProperties.getProperty("zhd.erp.proxy");
			// 当前模式
			GlobalVariable.CURRENTPROFILE = globalProperties.getProperty(DefaultProps.PROFILE.getName(), "default");
			// 端口启动
			String defaultPort = globalProperties.getProperty(DefaultProps.PORT.getName(), "4567");
			staticFiles.location("static");
			port(Integer.parseInt(defaultPort));
			// 初始化数据库
			DatabaseConfig.shareInstance().configData();
			String projectPath = globalProperties.getProperty("zhd.oa.projectPath", "/");
			// 配置controller路径
			String controllerPackagePath = globalProperties.getProperty(DefaultProps.CONTROLLER.getName(), null);
			if (controllerPackagePath != null) {
				FileUtil.shareInstance().initFileConfig(controllerPackagePath);
				after((request, response) -> {
					response.header("Content-Encoding", "gzip");
				});
			} else {
				System.out.println("服务关闭");
				stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}
}
