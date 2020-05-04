package org.hummer.demo.task;

import org.hummer.cluster.service.MasterService;
import org.hummer.task.server.remotecall.unifytask.service.UnifyTaskService;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-05-03 23:18
 * @update 2020-05-03 23:18
 **/
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
