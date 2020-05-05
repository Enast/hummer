package org.hummer.task.client.web;

import org.hummer.task.client.service.TaskExecuteService;
import org.hummer.task.core.vo.TaskAjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 服务端调用客户端的接口:任务调用
 */
@RestController
@RequestMapping("/cloud/unifyTask/v1/dispatch")
public class TaskDispatchRestful {

    @Resource
    TaskExecuteService dispatchService;

    @GetMapping("/startTask")
    public TaskAjaxResult<Boolean> startTask(String taskName) {
        TaskAjaxResult taskAjaxResult = new TaskAjaxResult();
        taskAjaxResult.setData(dispatchService.startTask(taskName));
        return taskAjaxResult;
    }

}
