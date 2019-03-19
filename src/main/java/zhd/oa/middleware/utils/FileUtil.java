package zhd.oa.middleware.utils;

import java.io.File;
import java.lang.reflect.Method;

import zhd.oa.middleware.config.BeanFactory;

public class FileUtil {
	private static FileUtil instance = null;

	private FileUtil() {
	}

	public static FileUtil shareInstance() {
		if (instance == null)
			instance = new FileUtil();
		return instance;
	}

	public void initFileConfig(String filePath) throws Exception {
		if (filePath != null) {
			String folderURL = filePath.replace(".", "/");
			String path = this.getClass().getResource("/").getPath();
			String fileFullPath = path + folderURL;
			File ctrlFolder = new File(fileFullPath);
			if (ctrlFolder.isDirectory()) {
				File[] files = ctrlFolder.listFiles();
				for (File f : files) {
					if (f.getName().endsWith(".class")) {
						String classname = (f.getPath().substring(f.getPath().indexOf("classes") + 8,
								f.getPath().length() - 6)).replace("/", ".");
						Object o = null;
						Class<?> c = Class.forName(classname);
						o = BeanFactory.getBean(c);
						System.out.println("classname:>>" + classname);
						// = c.newInstance();
						for (Method m : c.getMethods()) {
							if (m.getName().equals("router"))
								m.invoke(o);
						}
						// for (Method m : c.getMethods()) {
						// if (!GlobalVariable.COMMETHODS.contains(m.getName())) {
						// System.out.println(
						// m.getName() + "adjust:>>" + GlobalVariable.COMMETHODS.contains(m.getName()));
						// m.invoke(o);
						// }
						// }
					} else if (f.isDirectory()) {
						System.out.println("sub direction:>>" + f.getName());
						initFileConfig(filePath + "." + f.getName());
					}
				}
			} else {
				throw new Exception("filepath is not a folder path");
			}
		} else {
			throw new NullPointerException();
		}
	}
}
