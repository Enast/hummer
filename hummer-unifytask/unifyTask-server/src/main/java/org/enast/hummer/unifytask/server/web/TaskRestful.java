package org.enast.hummer.unifytask.server.web;

import org.enast.hummer.unifytask.core.vo.TaskAjaxResult;
import org.enast.hummer.unifytask.server.service.UnifyTaskService;
import org.enast.hummer.unifytask.server.web.vo.Pagination;
import org.enast.hummer.unifytask.server.web.vo.TaskQueryVO;
import org.enast.hummer.unifytask.server.web.vo.TaskVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhujinming6
 * @create 2020-05-21 15:01
 * @update 2020-05-21 15:01
 **/
@RestController
@RequestMapping("/api/taskService/v1/task")
public class TaskRestful {

    @Resource
    UnifyTaskService unifyTaskService;

    /**
     * 分页查询日志列表
     *
     * @param taskQueryVO
     * @return
     */
    @PostMapping("/page")
    @ResponseBody
    public TaskAjaxResult<Pagination<TaskVO>> pageList(@RequestBody TaskQueryVO taskQueryVO) {
        TaskAjaxResult<Pagination<TaskVO>> result = TaskAjaxResult.buildSuccess();
        Pagination<TaskVO> pagination = unifyTaskService.pageList(taskQueryVO);
        result.setData(pagination);
        return result;
    }

    /**
     * 更新任务 名称，cron , 重试次数
     *
     * @param taskVO
     * @return
     */
    @PostMapping("/update")
    public TaskAjaxResult<String> updateTask(@RequestBody TaskVO taskVO) {
        TaskAjaxResult<String> result = TaskAjaxResult.buildSuccess();
        result.setData(unifyTaskService.update(taskVO));
        return result;
    }

    /**
     * 关闭任务，下个周期不执行
     *
     * @param id
     * @return
     */
    @GetMapping("/close")
    public TaskAjaxResult<String> closeTask(String id) {
        TaskAjaxResult<String> result = TaskAjaxResult.buildSuccess();
        result.setData(unifyTaskService.close(id));
        return result;
    }


    /**
     * 立即运存任务
     *
     * @param id
     * @return
     */
    @GetMapping("/running")
    public TaskAjaxResult<String> retry(String id) {
        TaskAjaxResult<String> result = TaskAjaxResult.buildSuccess();
        result.setData(unifyTaskService.running(id));
        return result;
    }
}
