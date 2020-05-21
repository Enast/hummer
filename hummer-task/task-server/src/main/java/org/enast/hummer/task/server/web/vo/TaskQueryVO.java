package org.enast.hummer.task.server.web.vo;

import lombok.Data;

/**
 * @author zhujinming6
 * @create 2020-05-21 16:43
 * @update 2020-05-21 16:43
 **/
@Data
public class TaskQueryVO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
