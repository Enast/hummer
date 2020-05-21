package org.enast.hummer.task.server.rpc;

import org.enast.hummer.task.server.service.ServerUnifyTaskService;
import org.enast.hummer.task.core.common.HttpUtils;
import org.enast.hummer.task.core.vo.TaskAjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 统一任务调度,调度类
 *
 * @author zhujinming6
 * @create 2018-09-11 19:32
 * @update 2018-09-11 19:32
 **/
@Service
public class ServerUnifyTaskServiceImpl implements ServerUnifyTaskService {

    private Logger log = LoggerFactory.getLogger(ServerUnifyTaskServiceImpl.class);

    @Autowired
    @Qualifier("taskRest")
    RestTemplate rt;

    /**
     * 服务端调用客户端接口,执行任务
     *
     * @param server   对应的微服务名称
     * @param taskName
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> dispatchTask(String server, String taskName) {
        TaskAjaxResult<Boolean> result = rt.exchange("http://" + server + "/" + server + "/cloud/unifyTask/v1/dispatch/startTask?taskName=" + taskName, HttpMethod.GET, HttpUtils.getHttpEntity(null), new ParameterizedTypeReference<TaskAjaxResult>() {
        }).getBody();
        return result;
    }
}
