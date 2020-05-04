package org.hummer.demo.task;

import org.hummer.cluster.service.MasterService;
import org.hummer.task.server.service.UnifyTaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-05-03 23:18
 * @update 2020-05-03 23:18
 **/
@Component
public class Start implements CommandLineRunner {

    @Resource
    MasterService masterService;
    @Resource
    UnifyTaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        if(masterService.master()) {
            taskService.scheduleStart();
        }
    }
}
