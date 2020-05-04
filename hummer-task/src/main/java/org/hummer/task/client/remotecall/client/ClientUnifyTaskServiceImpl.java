package org.hummer.task.client.remotecall.client;

import org.hummer.task.ServerUtils;
import org.hummer.task.client.remotecall.vo.BasicTask;
import org.hummer.task.client.remotecall.vo.UnifyTaskStatus;
import org.hummer.task.vo.TaskAjaxResult;
import org.hummer.task.HttpUtils;
import org.hummer.task.client.remotecall.service.ClientUnifyTaskService;
import org.hummer.task.client.remotecall.vo.UnifyTaskRegister;
import org.hummer.task.client.remotecall.vo.UnifyTaskUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 统一任务调度处理客户端上报状态处理类
 *
 * @author zhujinming6
 * @create 2018-09-11 19:32
 * @update 2018-09-11 19:32
 **/
public class ClientUnifyTaskServiceImpl implements ClientUnifyTaskService {

    private Logger log = LoggerFactory.getLogger(ClientUnifyTaskServiceImpl.class);

    @Resource
    ServerUtils serverUtils;

    @Resource
    @Qualifier("taskRest")
    RestTemplate rt;

    @Value("${hummer.unifyTask.server.application:}")
    private String application;

    @Value("${hummer.unifyTask.server.context:}")
    private String context;

    /**
     * 客户端上报任务执行状态
     *
     * @param taskStatus
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> reportTaskStatus(UnifyTaskStatus taskStatus) {
        TaskAjaxResult<Boolean> result = rt.exchange("http://" + application + "/" + context + "/cloud/unifyTask/v1/taskStatus", HttpMethod.POST, HttpUtils.getHttpEntity(taskStatus), new ParameterizedTypeReference<TaskAjaxResult<Boolean>>() {
        }).getBody();
        return result;
    }

    /**
     * @param registers
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> tasksRegister(List<UnifyTaskRegister> registers) {
        TaskAjaxResult<Boolean> result = rt.exchange("http://" + application + "/" + context + "/cloud/unifyTask/v1/tasksRegister", HttpMethod.POST, HttpUtils.getHttpEntity(registers), new ParameterizedTypeReference<TaskAjaxResult<Boolean>>() {
        }).getBody();
        return result;
    }


    /**
     * 任务更新执行时间
     *
     * @param tasks
     * @return
     */
    @Override
    public TaskAjaxResult<Boolean> tasksUpdate(List<UnifyTaskUpdate> tasks) {
        setServer(tasks);
        TaskAjaxResult<Boolean> result = rt.exchange("http://" + application + "/" + context + "/cloud/unifyTask/v1/tasksUpdate", HttpMethod.POST, HttpUtils.getHttpEntity(tasks), new ParameterizedTypeReference<TaskAjaxResult<Boolean>>() {
        }).getBody();
        return result;
    }

    /**
     * 任务更新执行时间
     *
     * @param taskNo
     * @return
     */
    @Override
    public TaskAjaxResult<BasicTask> taskByNo(String taskNo) {
        String server = serverUtils.getContextPath();
        TaskAjaxResult<BasicTask> result = rt.exchange("http://" + application + "/" + context + "/cloud/unifyTask/v1/findByNo?taskNo=" + taskNo + "&server=" + server, HttpMethod.GET, HttpUtils.getHttpEntity(null), new ParameterizedTypeReference<TaskAjaxResult<BasicTask>>() {
        }).getBody();
        return result;
    }


    public void setServer(List<UnifyTaskUpdate> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }
        String server = serverUtils.getContextPath();
        tasks.stream().forEach(taskUpdate -> {
            taskUpdate.setServer(server);
        });
    }
}
