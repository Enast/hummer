package org.enast.hummer.demo.task.service.impl;

import org.enast.hummer.demo.task.service.TestService;
import org.enast.hummer.task.client.service.TaskExecuteService;
import org.enast.hummer.task.core.aop.EnableUnifyTask;
import org.enast.hummer.task.core.aop.UnifyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 测试类
 *
 * @author zhujinming6
 * @create 2019-10-11 12:30
 * @update 2019-10-11 12:30
 **/
@EnableUnifyTask
@Service
public class TestServiceImpl implements TestService {

    private Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    TaskExecuteService taskExecuteService;

    /**
     * 执行任务
     *
     * @return
     */
    @Override
    @UnifyTask(taskNo = "demo-test", cron = "0 */1 * * * ?")
    public void task() {
        // 处理逻辑
        log.info("start task");
        return;
    }

}
