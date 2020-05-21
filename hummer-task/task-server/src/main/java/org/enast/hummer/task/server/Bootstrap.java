package org.enast.hummer.task.server;

import org.enast.hummer.cluster.consul.service.ConsulElectService;
import org.enast.hummer.cluster.core.service.MasterService;
import org.enast.hummer.task.server.service.UnifyTaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-05-03 23:18
 * @update 2020-05-03 23:18
 **/
@Component
public class Bootstrap implements CommandLineRunner {

    @Resource
    MasterService masterService;
    @Resource
    UnifyTaskService taskService;
    @Resource
    ConsulElectService consulElectService;

    @Override
    public void run(String... args) throws Exception {
        consulElectService.work("task-server");
        if (masterService.master()) {
            taskService.scheduleStart();
        }
    }
}
