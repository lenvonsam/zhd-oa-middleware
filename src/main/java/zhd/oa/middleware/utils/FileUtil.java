package zhd.oa.middleware.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import zhd.oa.middleware.config.BeanFactory;
import zhd.oa.middleware.enums.DefaultProps;

public class FileUtil {
    private static FileUtil instance = null;

    private FileUtil() {
    }

    public static FileUtil shareInstance() {
        if (instance == null)
            instance = new FileUtil();
        return instance;
    }

    public static boolean dynamicCornExecute(String jobClass, String cornReg) {
        Boolean result = true;
        try {
        Class c = Class.forName(jobClass);
        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>)c)
                .withIdentity("job_" + c.getName(), "oa_job_group").build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + c.getName(), "oa_job_group")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cornReg)).build();

            // 3、创建Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // 4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static boolean dynamicCornRemove(String jobClass) {
        Boolean result = true;
        try {
            Class c = Class.forName(jobClass);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            TriggerKey triggerKey = new TriggerKey("job_" + c.getName(), "oa_job_group");
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            JobKey key = new JobKey("job_" + c.getName(), "oa_job_group");
            scheduler.deleteJob(key);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public void initFileConfig(String filePath) throws Exception {
        if (filePath != null) {
            String os = System.getProperty("os.name").toLowerCase();
            String folderURL = filePath.replace(".", os.startsWith("win") ? "\\" : "/");
            String path = this.getClass().getResource("/").getPath();
            String fileFullPath = path + folderURL;
            File ctrlFolder = new File(fileFullPath);
            if (ctrlFolder.isDirectory()) {
                File[] files = ctrlFolder.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    if (f.getName().endsWith(".class")) {
                        // controller file scan
                        String classname = (f.getPath().substring(f.getPath().indexOf("classes") + 8,
                                f.getPath().length() - 6)).replace(os.startsWith("win") ? "\\" : "/", ".");
                        Class<?> c = Class.forName(classname);
                        if (f.getName().indexOf("BaseController") < 0 && f.getName().indexOf("Controller") > 0) {
                            Object o = null;
                            o = BeanFactory.getBean(c);
                            // System.out.println("classname:>>" + classname);
                            // = c.newInstance();
                            for (Method m : c.getMethods()) {
                                if (m.getName().equals("router"))
                                    m.invoke(o);
                            }
                        } else if (f.getName().indexOf("Job") >= 0) {
                            Properties globalProperties = PropertyUtil.shareInstance()
                                    .initProperties(DefaultProps.BOOTFILE.getName());
                            @SuppressWarnings("unchecked")
                            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) c)
                                    .withIdentity("job_" + i, "oa_job_group").build();
                            // SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                            // .withIntervalInSeconds(5) // 设置间隔执行时间
                            // .repeatSecondlyForTotalCount(5);
                            // Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + i,
                            // "oa_job_group")
                            // .startNow().withSchedule(builder).build();
                            String cornReg = globalProperties
                                    .getProperty(f.getName().substring(0, f.getName().indexOf('.')) + ".corn");
                            if (cornReg != null) {
                                Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + i, "oa_job_group")
                                        .withSchedule(CronScheduleBuilder.cronSchedule(cornReg)).build();

                                // 3、创建Scheduler
                                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                                // 4、调度执行
                                scheduler.scheduleJob(jobDetail, trigger);
                                scheduler.start();
                            }
                        }
                        // for (Method m : c.getMethods()) {
                        // if (!GlobalVariable.COMMETHODS.contains(m.getName())) {
                        // System.out.println(
                        // m.getName() + "adjust:>>" + GlobalVariable.COMMETHODS.contains(m.getName()));
                        // m.invoke(o);
                        // }
                        // }
                    } else if (f.isDirectory()) {
                        // System.out.println("sub direction:>>" + f.getName());
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
