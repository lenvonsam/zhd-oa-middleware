package zhd.oa.middleware.config;

import java.lang.reflect.Field;
import java.util.Properties;

import javax.activation.FileDataSource;

import zhd.oa.middleware.enums.DefaultProps;
import zhd.oa.middleware.innotation.Autowired;
import zhd.oa.middleware.innotation.Value;
import zhd.oa.middleware.utils.PropertyUtil;

/**
 * @author samy
 * @version 0.0.1
 */
public class BeanFactory {

	public static <Q> Q getBean(Class<Q> clazz) {
		Q result = null;
		try {
			System.out.println("class name:>>" + clazz.getName());
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println("get the " + clazz.getName() + "failed!!");
			return null;
		} catch (IllegalAccessException e) {
			System.out.println("get the " + clazz.getName() + "failed!!");
			e.printStackTrace();
			return null;
		}
		// 查找所有的字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {// 查找字段中含有依赖注入的字段 存在就进行注入
			Autowired inject = field.getAnnotation(Autowired.class);
			System.out.println("inject filed:>>" + field.getType() + ";" + (inject != null));
			if (inject != null) {
				Object object = getBean(field.getType());
				if (!field.isAccessible())
					field.setAccessible(true);
				try {
					field.set(result, object);
					System.out.println(field.getType() + ">>>inject success");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("Inject the " + field.getName() + "failed!!");
				}
			}
			// 获取value
			Value valject = field.getAnnotation(Value.class);
			System.out.println(">>>>:" + field.getName() + "; type:>>" + field.getType());
			if (valject != null) {
				try {
					Properties props = PropertyUtil.shareInstance().initProperties(DefaultProps.BOOTFILE.getName());
					Object o = props.get(valject.value());
					if (!field.isAccessible())
						field.setAccessible(true);
					field.set(result, o);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Inject value the " + field.getName() + "failed!!");
				}
			}
		}
		return result;
	}
}