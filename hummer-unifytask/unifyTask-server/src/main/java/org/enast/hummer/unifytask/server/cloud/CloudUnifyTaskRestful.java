package org.enast.hummer.unifytask.server.cloud;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.enast.hummer.unifytask.core.vo.*;
import org.enast.hummer.unifytask.server.service.UnifyTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 框架远程接口
 *
 * @author zhujinming6
 * @create 2018-09-16 14:56
 * @update 2018-09-16 14:56
 **/
@RestController
@RequestMapping("/cloud/unifyTask/v1")
@Api(value = "统一任务调度框架内置接口",tags = "统一任务调度框架内置接口")
public class CloudUnifyTaskRestful {

    @Resource
    UnifyTaskService taskService;

    /**
     * 接收任务上报状态,并处理
     *
     * @param
     * @return
     */
    @PostMapping("/taskStatus")
    @ApiOperation(value = "服务端接收任务上报状态,并处理",tags = "统一任务调度框架内置接口")
    @ApiImplicitParams({@ApiImplicitParam(value = "taskStatus", name = "任务状态实体", required = true, paramType = "body", dataType = "UnifyTaskStatus")})
    public TaskAjaxResult<Boolean> dealTaskStatus(@RequestBody UnifyTaskStatus taskStatus) {
        return taskService.dealTaskStatus(taskStatus);
    }

    /**
     * 任务注册接口
     *
     * @param taskRegisters
     * @return
     */
    @PostMapping("/tasksRegister")
    @ApiOperation(value ="任务注册接口",tags = "统一任务调度框架内置接口")
    @ApiImplicitParams({@ApiImplicitParam(value = "taskRegisters", name = "任务注册实体", required = true, paramType = "body", dataType = "UnifyTaskRegister")})
    public TaskAjaxResult<Boolean> tasksRegister(@RequestBody List<UnifyTaskRegister> taskRegisters) {
        return taskService.tasksRegister(taskRegisters);
    }

    /**
     * 任务更新接口
     *
     * @param tasks
     * @return
     */
    @PostMapping("/tasksUpdate")
    @ApiOperation(value ="任务更新接口",tags = "统一任务调度框架内置接口")
    @ApiImplicitParams({@ApiImplicitParam(value = "tasks", name = "任务更新实体", required = true, paramType = "body", dataType = "UnifyTaskUpdate")})
    public TaskAjaxResult<Boolean> tasksUpdate(@RequestBody List<UnifyTaskUpdate> tasks) {
        return taskService.tasksUpdate(tasks);
    }

    /**
     * 根据任务编号查找任务
     *
     * @param taskNo
     * @param server
     * @return
     */
    @GetMapping("/findByNo")
    @ApiOperation(value ="根据任务编号查找任务",tags = "统一任务调度框架内置接口")
    @ApiImplicitParams({@ApiImplicitParam(value = "taskNo", name = "任务编号", required = true, paramType = "query", dataType = "string",defaultValue = ""),
            @ApiImplicitParam(value = "server", name = "所属服务", required = true, paramType = "query", dataType = "string",defaultValue = "")})
    public TaskAjaxResult<BasicTask> taskByNo(String taskNo, String server) {
        return taskService.taskByNo(taskNo, server);
    }
}
