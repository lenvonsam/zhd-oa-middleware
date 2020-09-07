package zhd.oa.middleware.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhd.oa.middleware.controller.ExceptionController;
import zhd.oa.middleware.enums.DefaultProps;
import zhd.oa.middleware.utils.FileUtil;
import zhd.oa.middleware.utils.HttpUtil;
import zhd.oa.middleware.utils.PropertyUtil;
import zhd.oa.middleware.utils.WorkflowUtil;

import java.util.Properties;

import static spark.Spark.*;

public class AutoWebConfig {
	private static Logger log = LoggerFactory.getLogger(AutoWebConfig.class);

	// 初始化web application config
	public static void autoInit() {
		try {
			// 读取数据库配置
			Properties globalProperties = PropertyUtil.shareInstance().initProperties(DefaultProps.BOOTFILE.getName());
			// ERP proxy url
			HttpUtil.ERPPROXYURL = globalProperties.getProperty("zhd.erp.proxy");
			// OA proxy url
			HttpUtil.OAPROXYURL = globalProperties.getProperty("zhd.oa.proxy");
			// 当前模式
			GlobalVariable.CURRENTPROFILE = globalProperties.getProperty(DefaultProps.PROFILE.getName(), "default");
			// 端口启动
			String defaultPort = globalProperties.getProperty(DefaultProps.PORT.getName(), "4567");
			// 静态资源配置
			staticFiles.location("static");
			staticFiles.expireTime(600);
			port(Integer.parseInt(defaultPort));
			// 初始化数据库
			DatabaseConfig.shareInstance().configData();
			// 配置controller路径
			String controllerPackagePath = globalProperties.getProperty(DefaultProps.CONTROLLER.getName(), null);
			if (controllerPackagePath != null) {
				exception(Exception.class, (exception, request, response) -> {
					ExceptionController.handle(exception, request, response);
				});
				FileUtil.shareInstance().initFileConfig(controllerPackagePath);
				after((request, response) -> {
					response.header("Content-Encoding", "gzip");
				});
				log.info("controller init config finish");
				// 初始化定时任务
				String quartzPath = globalProperties.getProperty(DefaultProps.QUARTZ.getName(), null);
				FileUtil.shareInstance().initFileConfig(quartzPath);
				log.info("quartz init finish");
			} else {
				log.info("server close");
				stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}
}
