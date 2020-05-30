package org.enast.hummer.unifytask.client.init;

import org.enast.hummer.unifytask.core.common.ServerUtils;
import org.enast.hummer.unifytask.core.aop.EnableUnifyTask;
import org.enast.hummer.unifytask.core.aop.UnifyTask;
import org.enast.hummer.unifytask.client.service.TaskExecuteService;
import org.enast.hummer.unifytask.core.vo.UnifyTaskBean;
import org.apache.commons.lang3.StringUtils;
import org.enast.hummer.unifytask.core.vo.UnifyTaskRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一任务调度模块初始化相关业务
 *
 * @author zhujinming6
 * @create 2019-10-11 11:41
 * @update 2019-10-11 11:41
 **/
public class TaskClientInitService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TaskClientInitService.class);

    public static Map<String, UnifyTaskBean> taskBeanMap = new HashMap();

    @Resource
    ApplicationContext context;
    @Resource
    TaskExecuteService taskExecuteService;
    @Resource
    ServerUtils serverUtils;

    /**
     * Callback used to run the bean.
     * 寻找所有UnifyTask注解的bean和方法,存储起来
     * // 使用CGLIB代理
     * Method[] methods = bean.getClass().getMethods();
     * UnifyTask task = AnnotationUtils.findAnnotation(m,UnifyTask.class);
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            try {
                Thread.sleep(1 * 60 * 1000);
            } catch (InterruptedException e) {
                log.error("", e);
            }
            Map<String, Object> converters = context.getBeansWithAnnotation(EnableUnifyTask.class);
            List<UnifyTaskRegister> registers = new ArrayList<>();
            String server = serverUtils.getContextPath();
            for (Map.Entry<String, Object> entry : converters.entrySet()) {
                if (entry == null) {
                    continue;
                }
                Object bean = entry.getValue();
                // 使用JDK 代理的情况下,需要获取真实类类型
                Method[] methods = AopUtils.getTargetClass(bean).getMethods();
                for (Method m : methods) {
                    UnifyTask task = m.getAnnotation(UnifyTask.class);
                    if (task == null || StringUtils.isBlank(task.taskNo())) {
                        continue;
                    }
                    UnifyTaskBean taskBean = new UnifyTaskBean();
                    taskBean.setBean(bean);
                    taskBean.setMethod(m);
                    taskBeanMap.put(task.taskNo(), taskBean);
                    // 获取cron表达式
                    String cron = getCronExpress(task.cron());
                    UnifyTaskRegister register = new UnifyTaskRegister(task.name(), task.taskNo(), server, cron);
                    registers.add(register);
                }
            }
            log.info("taskBeanMap size ", "size", taskBeanMap.size());
            new Thread(() -> {
                taskExecuteService.tasksRegister(registers);
            }).start();
        }).start();
    }

    private String getCronExpress(String cron) {
        String result = cron;
        if (StringUtils.isBlank(cron)) {
            return result;
        }
        cron = cron.trim();
        if (cron.startsWith("${") && cron.endsWith("}")) {
            log.info("use express");
            cron = cron.replace("${", "").replace("}", "");
            String[] strArr = cron.split(":");
            result = context.getEnvironment().getProperty(strArr[0]);
            if (StringUtils.isBlank(result) && strArr.length > 1) {
                result = strArr[1];
            } else {
                log.info("", "result", "strArr ", result, strArr.length);
            }
        } else {
            log.info("not use express");
        }
        return result;
    }

}
