package org.enast.hummer.task.server.service.impl;

import org.enast.hummer.task.server.biz.UnifyTaskLogBiz;
import org.enast.hummer.task.server.model.UnifyTaskLog;
import org.enast.hummer.task.server.service.UnifyTaskLogService;
import org.enast.hummer.task.server.web.vo.Pagination;
import org.enast.hummer.task.server.web.vo.TaskLogQueryVO;
import org.enast.hummer.task.server.web.vo.TaskLogVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sf.common.wrapper.Page;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-05-21 18:58
 * @update 2020-05-21 18:58
 **/
@Service
public class UnifyTaskLogServiceImpl implements UnifyTaskLogService {

    @Resource
    UnifyTaskLogBiz logBiz;

    /**
     * 查询任务执行日志
     *
     * @param queryVO
     * @return
     */
    @Override
    public Pagination<TaskLogVO> pageList(TaskLogQueryVO queryVO) {
        Pagination<TaskLogVO> pagination = new Pagination<>();
        Page<UnifyTaskLog> logPage = logBiz.pageList((queryVO.getPageNo() - 1) * queryVO.getPageSize(), queryVO.getPageSize());
        pagination.setPageSize(queryVO.getPageSize());
        pagination.setPageNo(queryVO.getPageNo());
        pagination.setTotal(logPage.getTotalCount());
        pagination.setPageNo(logPage.getTotalPage());
        pagination.setRows(transVO(logPage.getList()));
        return pagination;
    }

    private List<TaskLogVO> transVO(List<UnifyTaskLog> list) {
        List<TaskLogVO> taskVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(log -> {
                taskVOS.add(new TaskLogVO(log));
            });
        }
        return taskVOS;
    }
}
