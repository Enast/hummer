package org.enast.hummer.unifytask.client.service.impl;

import com.alibaba.fastjson.JSON;
import org.enast.hummer.unifytask.client.init.TaskClientInitService;
import org.enast.hummer.unifytask.client.service.TaskExecuteService;
import org.enast.hummer.unifytask.core.remotecall.service.ClientUnifyTaskService;
import org.enast.hummer.unifytask.core.vo.TaskAjaxResult;
import org.enast.hummer.unifytask.core.vo.UnifyTaskBean;
import org.enast.hummer.unifytask.core.vo.UnifyTaskRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户端接收到要执行任务的请求,开启新线程 执行任务
 *
 * @author zhujinming6
 * @create 2019-10-11 9:24
 * @update 2019-10-11 9:24
 **/
public class TaskExecuteServiceImpl implements TaskExecuteService {

    @Resource
    ClientUnifyTaskService clientUnifyTaskService;

    private Logger log = LoggerFactory.getLogger(TaskExecuteServiceImpl.class);

    /**
     * 启动任务
     *
     * @param taskName
     * @return
     */
    @Override
    public Boolean startTask(String taskName) {
        UnifyTaskBean taskBean = TaskClientInitService.taskBeanMap.get(taskName);
        if (taskBean == null) {
            log.info("taskBean is null");
            return false;
        }
        log.info("start task :{}", taskName);
        new Thread(() -> {
            ReflectionUtils.invokeMethod(taskBean.getMethod(), taskBean.getBean());
        }).start();
        return true;
    }

    @Override
    public void tasksRegister(List<UnifyTaskRegister> registers) {
        log.info("register task size:{}", registers == null ? "0" : registers.size());
        if (CollectionUtils.isEmpty(registers)) {
            return;
        }
        TaskAjaxResult<Boolean> result = clientUnifyTaskService.tasksRegister(registers);
        log.info("tasksRegister json:{}", JSON.toJSONString(result));
    }
}
