package org.enast.hummer.task.server.service;

import org.enast.hummer.task.server.web.vo.Pagination;
import org.enast.hummer.task.server.web.vo.TaskLogQueryVO;
import org.enast.hummer.task.server.web.vo.TaskLogVO;

/**
 * @author zhujinming6
 * @create 2020-05-21 18:58
 * @update 2020-05-21 18:58
 **/
public interface UnifyTaskLogService {

    Pagination<TaskLogVO> pageList(TaskLogQueryVO queryVO);
}
