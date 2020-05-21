package org.enast.hummer.task.server.web;

import org.enast.hummer.task.core.vo.TaskAjaxResult;
import org.enast.hummer.task.server.service.UnifyTaskLogService;
import org.enast.hummer.task.server.service.UnifyTaskService;
import org.enast.hummer.task.server.web.vo.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-05-21 15:01
 * @update 2020-05-21 15:01
 **/
@RestController
@RequestMapping("/api/taskService/v1/taskLog")
public class TaskLogRestful {

    @Resource
    UnifyTaskLogService logService;

    /**
     * 分页查询任务执行日志列表
     *
     * @param queryVO
     * @return
     */
    @PostMapping("/page")
    public TaskAjaxResult<Pagination<TaskLogVO>> pageList(@RequestBody TaskLogQueryVO queryVO) {
        TaskAjaxResult<Pagination<TaskLogVO>> result = TaskAjaxResult.buildSuccess();
        Pagination<TaskLogVO> pagination = logService.pageList(queryVO);
        result.setData(pagination);
        return result;
    }
}
